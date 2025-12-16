package org.example.jenkinstool.pipeline.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.example.jenkinstool.pipeline.model.StageResult;

@Entity
public class PipelineRun {

    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineRun.class);

    public enum Status {
        QUEUED,
        RUNNING,
        SUCCESS,
        FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Pipeline pipeline;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Instant queuedAt;

    private Instant startedAt;

    private Instant finishedAt;

    @Column(columnDefinition = "TEXT")
    private String logOutput;

    @Column(columnDefinition = "TEXT")
    private String stageResultsJson;

    @Transient
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PipelineRun() {
    }

    public PipelineRun(Pipeline pipeline) {
        this.pipeline = pipeline;
        this.status = Status.QUEUED;
        this.queuedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getQueuedAt() {
        return queuedAt;
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

    public List<StageResult> getStageResults() {
        if (stageResultsJson == null || stageResultsJson.isBlank()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(stageResultsJson, new TypeReference<>() { });
        } catch (Exception e) {
            LOGGER.warn("Failed to deserialize stage results", e);
            return new ArrayList<>();
        }
    }

    public void setStageResults(List<StageResult> stageResults) {
        try {
            this.stageResultsJson = objectMapper.writeValueAsString(stageResults);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to serialize stage results", e);
        }
    }
}
