package org.example.jenkinstool.pipeline.web.dto;

import java.time.Instant;
import java.util.List;

public class PipelineResponse {

    private Long id;
    private String name;
    private String repositoryUrl;
    private String branch;
    private String script;
    private Instant createdAt;
    private List<PipelineRunResponse> runs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<PipelineRunResponse> getRuns() {
        return runs;
    }

    public void setRuns(List<PipelineRunResponse> runs) {
        this.runs = runs;
    }
}
