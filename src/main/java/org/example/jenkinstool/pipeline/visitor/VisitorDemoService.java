package org.example.jenkinstool.pipeline.visitor;

import org.springframework.stereotype.Service;

@Service
public class VisitorDemoService {

    public VisitorDemoResultDto analyzeDemoPipeline(boolean broken) {
        PipelineDefinitionNode pipeline = broken
                ? buildBrokenPipeline()
                : buildValidPipeline();

        PrintStructureVisitor printVisitor = new PrintStructureVisitor();
        ValidationVisitor validationVisitor = new ValidationVisitor();

        pipeline.accept(printVisitor);
        pipeline.accept(validationVisitor);

        return new VisitorDemoResultDto(
                pipeline.getName(),
                printVisitor.getLines(),
                validationVisitor.isValid(),
                validationVisitor.getErrors(),
                validationVisitor.getStageCount(),
                validationVisitor.getJobCount()
        );
    }

    private PipelineDefinitionNode buildValidPipeline() {
        return new PipelineDefinitionNode("Demo CI Pipeline")
                .addStage(
                        new StageNode("Build")
                                .addJob(new JobNode("Compile", "mvn compile"))
                                .addJob(new JobNode("Unit tests", "mvn test"))
                )
                .addStage(
                        new StageNode("Deploy")
                                .addJob(new JobNode("Deploy to dev", "./deploy-dev.sh"))
                );
    }

    private PipelineDefinitionNode buildBrokenPipeline() {
        return new PipelineDefinitionNode("Broken Pipeline")
                .addStage(
                        new StageNode("EmptyStage")
                )
                .addStage(
                        new StageNode("WeirdStage")
                                .addJob(new JobNode("", ""))
                );
    }
}