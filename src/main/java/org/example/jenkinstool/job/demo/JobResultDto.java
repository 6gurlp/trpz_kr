package org.example.jenkinstool.job.demo;

import java.util.List;

public class JobResultDto {

    private String repoUrl;
    private String branch;
    private boolean success;
    private List<String> logs;

    public JobResultDto(String repoUrl, String branch, boolean success, List<String> logs) {
        this.repoUrl = repoUrl;
        this.branch = branch;
        this.success = success;
        this.logs = logs;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getBranch() {
        return branch;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getLogs() {
        return logs;
    }
}