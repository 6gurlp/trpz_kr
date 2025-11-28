package org.example.jenkinstool.job.command;

import java.util.List;

public class JobExecutor {

    public void executeAll(JobContext context, List<JobCommand> commands) {
        for (JobCommand command : commands) {
            try {
                command.execute(context);
            } catch (JobExecutionException ex) {
                context.log("Command failed: " + ex.getMessage());
                context.markFailed("Command failed: " + ex.getMessage());
                break;
            }
        }
        context.log("Job finished. Success = " + context.isSuccess());
    }
}