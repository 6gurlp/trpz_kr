package org.example.jenkinstool.mediator;

import org.example.jenkinstool.demo.PipelineDemoService;
import org.example.jenkinstool.job.demo.CommandDemoService;
import org.springframework.stereotype.Service;

@Service
public class MediatorDemoService {

    private final CiMediator mediator;

    public MediatorDemoService(PipelineDemoService pipelineDemoService,
                               CommandDemoService commandDemoService) {

        PipelineColleague pipelineColleague = new PipelineColleague(pipelineDemoService);
        AgentColleague agentColleague = new AgentColleague(commandDemoService);
        NotificationColleague notificationColleague = new NotificationColleague();

        CiMediatorImpl mediatorImpl = new CiMediatorImpl(
                pipelineColleague,
                agentColleague,
                notificationColleague
        );

        pipelineColleague.setMediator(mediatorImpl);
        agentColleague.setMediator(mediatorImpl);
        notificationColleague.setMediator(mediatorImpl);

        this.mediator = mediatorImpl;
    }

    public MediatorDemoResultDto runDemo(String pipelineName, boolean failTests) {
        return mediator.runPipelineWithAgent(pipelineName, failTests);
    }
}