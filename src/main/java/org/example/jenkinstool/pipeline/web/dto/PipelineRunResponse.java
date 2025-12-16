package org.example.jenkinstool.pipeline.web.dto;

import java.time.Instant;
import java.util.List;
import org.example.jenkinstool.pipeline.model.PipelineRun;

public class PipelineRunResponse {

    private Long id;
    private PipelineRun.Status status;
    private Instant queuedAt;
    private Instant startedAt;
    private Instant finishedAt;
    private String logOutput;
    private List<StageResultResponse> stageResults;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PipelineRun.Status getStatus() {
        return status;
    }

    public void setStatus(PipelineRun.Status status) {
        this.status = status;
    }

    public Instant getQueuedAt() {
        return queuedAt;
    }

    public void setQueuedAt(Instant queuedAt) {
        this.queuedAt = queuedAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getLogOutput() {
        return logOutput;
    }

    public void setLogOutput(String logOutput) {
        this.logOutput = logOutput;
    }

    public List<StageResultResponse> getStageResults() {
        return stageResults;
    }

    public void setStageResults(List<StageResultResponse> stageResults) {
        this.stageResults = stageResults;
    }
}
