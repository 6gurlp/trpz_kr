package org.example.jenkinstool.job.command;

public class CheckoutCodeCommand implements JobCommand {

    @Override
    public void execute(JobContext context) {
        context.log("Checkout code from " + context.getRepoUrl()
                + " (branch " + context.getBranch() + ")");
    }
}
