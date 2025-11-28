package org.example.jenkinstool.job.command;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobContext {

    private final String repoUrl;
    private final String branch;
    private final LocalDateTime startedAt;
    private boolean success = true;
    private final List<String> logs = new ArrayList<>();

    public JobContext(String repoUrl, String branch) {
        this.repoUrl = repoUrl;
        this.branch = branch;
        this.startedAt = LocalDateTime.now();
        log("Job started at " + startedAt);
        log("Repository: " + repoUrl + " (branch: " + branch + ")");
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getBranch() {
        return branch;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void markFailed(String reason) {
        this.success = false;
        log("MARK FAILED: " + reason);
    }

    public void log(String message) {
        logs.add(LocalDateTime.now() + " - " + message);
    }

    public List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}