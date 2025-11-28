package org.example.jenkinstool.job.command;

public class LoggingJobCommandDecorator extends JobCommandDecorator {

    public LoggingJobCommandDecorator(JobCommand delegate) {
        super(delegate);
    }

    @Override
    public void execute(JobContext context) {
        context.log("[LOG] Starting command: " + delegate.getClass().getSimpleName());
        try {
            super.execute(context);
            context.log("[LOG] Finished command: " + delegate.getClass().getSimpleName());
        } catch (JobExecutionException ex) {
            context.log("[LOG] Command failed: " + delegate.getClass().getSimpleName()
                    + " - " + ex.getMessage());
            throw ex;
        }
    }
}