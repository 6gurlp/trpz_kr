package org.example.jenkinstool.mediator;

import org.example.jenkinstool.job.demo.CommandDemoService;
import org.example.jenkinstool.job.demo.JobResultDto;

public class AgentColleague {

    private CiMediator mediator;
    private final CommandDemoService commandDemoService;

    public AgentColleague(CommandDemoService commandDemoService) {
        this.commandDemoService = commandDemoService;
    }

    public void setMediator(CiMediator mediator) {
        this.mediator = mediator;
    }

    public JobResultDto runJobForPipeline(Long pipelineId, boolean failTests) {
        JobResultDto result = commandDemoService.runDemoJob(failTests);
        mediator.onJobCompleted(pipelineId, result);
        return result;
    }
}