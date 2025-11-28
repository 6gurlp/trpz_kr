package org.example.jenkinstool.job.command;

public class RunTestsCommand implements JobCommand {

    private final boolean shouldFail;

    public RunTestsCommand(boolean shouldFail) {
        this.shouldFail = shouldFail;
    }

    @Override
    public void execute(JobContext context) {
        context.log("Running tests...");

        if (shouldFail) {
            context.log("Tests FAILED");
            context.markFailed("Tests failed");
            throw new JobExecutionException("Tests failed");
        } else {
            context.log("All tests PASSED");
        }
    }
}