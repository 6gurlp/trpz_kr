package org.example.jenkinstool.job.demo;

import org.example.jenkinstool.job.command.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CommandDemoService {

    private final JobExecutor executor = new JobExecutor();


    public JobResultDto runDemoJob(boolean failTests) {
        JobContext context = new JobContext(
                "https://github.com/example/demo-repo.git",
                "main"
        );

        List<JobCommand> commands = getJobCommands(failTests);

        executor.executeAll(context, commands);

        return new JobResultDto(
                context.getRepoUrl(),
                context.getBranch(),
                context.isSuccess(),
                context.getLogs()
        );
    }

    private static List<JobCommand> getJobCommands(boolean failTests) {
        JobCommand checkout = new CheckoutCodeCommand();
        JobCommand tests = new RunTestsCommand(failTests);
        JobCommand build = new BuildArtifactCommand();

        checkout = new LoggingJobCommandDecorator(checkout);
        tests = new LoggingJobCommandDecorator(tests);
        build = new LoggingJobCommandDecorator(build);

        tests = new RetryJobCommandDecorator(tests, 3);

        return Arrays.asList(
                checkout,
                tests,
                build
        );
    }
}