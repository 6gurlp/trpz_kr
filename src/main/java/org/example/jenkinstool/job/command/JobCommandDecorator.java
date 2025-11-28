package org.example.jenkinstool.job.command;


public abstract class JobCommandDecorator implements JobCommand {

    protected final JobCommand delegate;

    protected JobCommandDecorator(JobCommand delegate) {
        this.delegate = delegate;
    }

    @Override
    public void execute(JobContext context) {
        delegate.execute(context);
    }
}