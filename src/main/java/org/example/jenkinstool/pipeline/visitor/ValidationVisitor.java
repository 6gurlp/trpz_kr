package org.example.jenkinstool.pipeline.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationVisitor implements PipelineVisitor {

    private boolean hasPipeline = false;
    private int stageCount = 0;
    private int jobCount = 0;
    private final List<String> errors = new ArrayList<>();

    @Override
    public void visit(PipelineDefinitionNode pipeline) {
        hasPipeline = true;
        if (pipeline.getStages().isEmpty()) {
            errors.add("Pipeline '" + pipeline.getName() + "' must have at least one stage");
        }
    }

    @Override
    public void visit(StageNode stage) {
        stageCount++;
        if (stage.getJobs().isEmpty()) {
            errors.add("Stage '" + stage.getName() + "' must have at least one job");
        }
    }

    @Override
    public void visit(JobNode job) {
        jobCount++;
        if (job.getName() == null || job.getName().isBlank()) {
            errors.add("Job has empty name (command: " + job.getCommand() + ")");
        }
        if (job.getCommand() == null || job.getCommand().isBlank()) {
            errors.add("Job '" + job.getName() + "' has empty command");
        }
    }

    public boolean isValid() {
        return hasPipeline && errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public int getStageCount() {
        return stageCount;
    }

    public int getJobCount() {
        return jobCount;
    }
}