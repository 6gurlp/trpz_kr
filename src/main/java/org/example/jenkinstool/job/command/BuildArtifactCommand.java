package org.example.jenkinstool.job.command;

public class BuildArtifactCommand implements JobCommand {

    @Override
    public void execute(JobContext context) {
        context.log("Building artifact (e.g., mvn package)...");
        context.log("Artifact built successfully");
    }
}