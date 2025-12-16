package org.example.jenkinstool.pipeline.execution.command;

import java.nio.file.Path;
import java.util.List;

public class GitCloneCommand implements PipelineCommand {

    private final String repositoryUrl;
    private final String branch;

    public GitCloneCommand(String repositoryUrl, String branch) {
        this.repositoryUrl = repositoryUrl;
        this.branch = branch;
    }

    @Override
    public void accept(PipelineCommandVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int execute(PipelineCommandContext context) throws Exception {
        context.log("Cloning " + repositoryUrl + " (branch " + branch + ")");
        Path destination = context.getRepositoryDirectory();
        return context.execute(List.of(
                "git", "clone", "--depth", "1", "--branch", branch,
                repositoryUrl, destination.toString()
        ), context.getWorkingDirectory());
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public String getBranch() {
        return branch;
    }
}
