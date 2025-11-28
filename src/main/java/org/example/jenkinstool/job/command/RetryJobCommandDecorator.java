package org.example.jenkinstool.job.command;

public class RetryJobCommandDecorator extends JobCommandDecorator {

    private final int maxAttempts;

    public RetryJobCommandDecorator(JobCommand delegate, int maxAttempts) {
        super(delegate);
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be >= 1");
        }
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void execute(JobContext context) {
        int attempt = 1;
        while (true) {
            try {
                context.log("[RETRY] Attempt " + attempt + " of " + maxAttempts
                        + " for " + delegate.getClass().getSimpleName());
                delegate.execute(context);
                return;
            } catch (JobExecutionException ex) {
                context.log("[RETRY] Attempt " + attempt + " failed: " + ex.getMessage());
                if (attempt >= maxAttempts) {
                    context.log("[RETRY] All attempts failed for "
                            + delegate.getClass().getSimpleName());
                    throw ex;
                }
                attempt++;
            }
        }
    }
}