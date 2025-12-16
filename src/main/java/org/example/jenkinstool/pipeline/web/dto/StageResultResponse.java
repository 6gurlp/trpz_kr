package org.example.jenkinstool.pipeline.web.dto;

import org.example.jenkinstool.pipeline.model.StageResult;

public class StageResultResponse {

    private String name;
    private StageResult.Status status;
    private String logOutput;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StageResult.Status getStatus() {
        return status;
    }

    public void setStatus(StageResult.Status status) {
        this.status = status;
    }

    public String getLogOutput() {
        return logOutput;
    }

    public void setLogOutput(String logOutput) {
        this.logOutput = logOutput;
    }
}
