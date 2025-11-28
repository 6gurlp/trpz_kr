package org.example.jenkinstool.pipeline.visitor;

import java.util.List;

public class VisitorDemoResultDto {

    private String pipelineName;
    private List<String> structure;
    private boolean valid;
    private List<String> errors;
    private int stageCount;
    private int jobCount;

    public VisitorDemoResultDto(String pipelineName,
                                List<String> structure,
                                boolean valid,
                                List<String> errors,
                                int stageCount,
                                int jobCount) {
        this.pipelineName = pipelineName;
        this.structure = structure;
        this.valid = valid;
        this.errors = errors;
        this.stageCount = stageCount;
        this.jobCount = jobCount;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public List<String> getStructure() {
        return structure;
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getErrors() {
        return errors;
    }

    public int getStageCount() {
        return stageCount;
    }

    public int getJobCount() {
        return jobCount;
    }
}