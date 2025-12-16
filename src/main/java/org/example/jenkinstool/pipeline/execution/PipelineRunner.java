package org.example.jenkinstool.pipeline.execution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.example.jenkinstool.pipeline.execution.command.CommandPlanVisitor;
import org.example.jenkinstool.pipeline.execution.command.GitCloneCommand;
import org.example.jenkinstool.pipeline.execution.command.PipelineCommand;
import org.example.jenkinstool.pipeline.execution.command.PipelineCommandContext;
import org.example.jenkinstool.pipeline.execution.command.StageCommand;
import org.example.jenkinstool.pipeline.execution.definition.PipelineDefinition;
import org.example.jenkinstool.pipeline.execution.definition.PipelineDefinitionParser;
import org.example.jenkinstool.pipeline.execution.definition.StageDefinition;
import org.example.jenkinstool.pipeline.execution.state.PipelineRunStateMachine;
import org.example.jenkinstool.pipeline.model.Pipeline;
import org.example.jenkinstool.pipeline.model.PipelineRun;
import org.example.jenkinstool.pipeline.model.PipelineRunRepository;
import org.example.jenkinstool.pipeline.model.StageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class PipelineRunner {

    private static final Logger log = LoggerFactory.getLogger(PipelineRunner.class);

    private final PipelineRunRepository pipelineRunRepository;
    private final PipelineDefinitionParser pipelineDefinitionParser = new PipelineDefinitionParser();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public PipelineRunner(PipelineRunRepository pipelineRunRepository) {
        this.pipelineRunRepository = pipelineRunRepository;
    }

    public PipelineRun queueRun(Pipeline pipeline) {
        PipelineRun run = new PipelineRun(pipeline);
        pipelineRunRepository.save(run);
        Long runId = run.getId();
        Runnable startExecution = () -> executor.submit(() -> executeRun(runId));

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    startExecution.run();
                }
            });
        } else {
            startExecution.run();
        }
        return run;
    }

    @Transactional
    protected void executeRun(Long runId) {
        PipelineRun run = pipelineRunRepository.findById(runId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown run id " + runId));
        PipelineRunStateMachine stateMachine = new PipelineRunStateMachine(run);
        stateMachine.start();
        pipelineRunRepository.save(run);

        StringBuilder logBuilder = new StringBuilder();
        List<StringBuilder> stageLogBuilders = new ArrayList<>();
        List<StageResult> stageResults = new ArrayList<>();
        Path workingDir = null;
        int exitCode = 0;
        try {
            workingDir = Files.createTempDirectory("pipeline-" + runId);
            Path repoDir = workingDir.resolve("repo");
            stageResults = createStageResults(run.getPipeline());
            stageResults.forEach(result -> stageLogBuilders.add(new StringBuilder()));
            persistProgress(run, stageResults, stageLogBuilders, logBuilder);

            List<PipelineCommand> commands = buildCommands(run.getPipeline());
            appendPlan(logBuilder, commands);

            for (int i = 0; i < commands.size(); i++) {
                PipelineCommand command = commands.get(i);
                StageResult stageResult = stageResults.get(i);
                StringBuilder stageLog = stageLogBuilders.get(i);
                markStageRunning(stageResult, stageLog, stageResults, stageLogBuilders, logBuilder, run);
                PipelineCommandContext context = new PipelineCommandContext(
                        workingDir,
                        repoDir,
                        line -> appendLine(logBuilder, line),
                        line -> appendLine(stageLog, line)
                );
                exitCode = command.execute(context);
                if (exitCode == 0) {
                    markStageFinished(stageResult, StageResult.Status.SUCCESS, stageResults, stageLogBuilders, logBuilder, run);
                } else {
                    markStageFinished(stageResult, StageResult.Status.FAILED, stageResults, stageLogBuilders, logBuilder, run);
                }
                if (exitCode != 0) {
                    break;
                }
            }
        } catch (Exception e) {
            appendLine(logBuilder, "Execution failed: " + e.getMessage());
            log.error("Failed to execute pipeline {}", runId, e);
            exitCode = 1;
        } finally {
            cleanWorkingDirectory(workingDir);
        }

        if (exitCode == 0) {
            stateMachine.succeed();
        } else {
            stateMachine.fail();
        }
        persistProgress(run, stageResults, stageLogBuilders, logBuilder);
    }

    private void appendPlan(StringBuilder logBuilder, List<PipelineCommand> commands) {
        CommandPlanVisitor planVisitor = new CommandPlanVisitor();
        commands.forEach(command -> command.accept(planVisitor));
        appendLine(logBuilder, "Execution plan:\n" + planVisitor.render());
    }

    private List<PipelineCommand> buildCommands(Pipeline pipeline) {
        PipelineDefinition definition = pipelineDefinitionParser.parse(pipeline.getScript());
        List<PipelineCommand> commands = new ArrayList<>();
        commands.add(new GitCloneCommand(pipeline.getRepositoryUrl(), pipeline.getBranch()));
        for (StageDefinition stage : definition.getStages()) {
            commands.add(new StageCommand(stage.getName(), stage.getRun()));
        }
        return commands;
    }

    private List<StageResult> createStageResults(Pipeline pipeline) {
        PipelineDefinition definition = pipelineDefinitionParser.parse(pipeline.getScript());
        List<StageResult> stageResults = new ArrayList<>();
        stageResults.add(new StageResult("Clone", StageResult.Status.PENDING));
        for (StageDefinition stage : definition.getStages()) {
            stageResults.add(new StageResult(stage.getName(), StageResult.Status.PENDING));
        }
        return stageResults;
    }

    private void markStageRunning(StageResult stage,
                                  StringBuilder stageLog,
                                  List<StageResult> stageResults,
                                  List<StringBuilder> stageLogBuilders,
                                  StringBuilder logBuilder,
                                  PipelineRun run) {
        appendLine(logBuilder, "\n== Stage: " + stage.getName() + " ==");
        appendLine(stageLog, "== " + stage.getName() + " ==");
        stage.setStatus(StageResult.Status.RUNNING);
        persistProgress(run, stageResults, stageLogBuilders, logBuilder);
    }

    private void markStageFinished(StageResult stage,
                                   StageResult.Status status,
                                   List<StageResult> stageResults,
                                   List<StringBuilder> stageLogBuilders,
                                   StringBuilder logBuilder,
                                   PipelineRun run) {
        stage.setStatus(status);
        persistProgress(run, stageResults, stageLogBuilders, logBuilder);
    }

    private void persistProgress(PipelineRun run,
                                 List<StageResult> stageResults,
                                 List<StringBuilder> stageLogBuilders,
                                 StringBuilder logBuilder) {
        for (int i = 0; i < stageResults.size(); i++) {
            StageResult result = stageResults.get(i);
            if (i < stageLogBuilders.size()) {
                result.setLogOutput(stageLogBuilders.get(i).toString());
            }
        }
        run.setLogOutput(logBuilder.toString());
        run.setStageResults(stageResults);
        pipelineRunRepository.save(run);
    }

    private void cleanWorkingDirectory(Path workingDir) {
        if (workingDir == null) {
            return;
        }
        try {
            Files.walk(workingDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            log.warn("Failed to clean {}", path, e);
                        }
                    });
        } catch (IOException e) {
            log.warn("Failed to clean working dir {}", workingDir, e);
        }
    }

    private void appendLine(StringBuilder logBuilder, String message) {
        logBuilder.append(message).append('\n');
    }
}
