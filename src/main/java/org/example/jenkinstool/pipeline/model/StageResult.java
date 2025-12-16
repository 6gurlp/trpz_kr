package org.example.jenkinstool.pipeline.model;

public class StageResult {

    public enum Status {
        PENDING,
        RUNNING,
        SUCCESS,
        FAILED
    }

    private String name;
    private Status status;
    private String logOutput;

    public StageResult() {
    }

    public StageResult(String name, Status status) {
        this.name = name;
        this.status = status;
        this.logOutput = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getLogOutput() {
        return logOutput;
    }

    public void setLogOutput(String logOutput) {
        this.logOutput = logOutput;
    }
}
