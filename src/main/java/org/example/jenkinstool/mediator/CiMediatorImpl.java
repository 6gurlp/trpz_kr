package org.example.jenkinstool.mediator;

import org.example.jenkinstool.demo.PipelineDto;
import org.example.jenkinstool.job.demo.JobResultDto;

public class CiMediatorImpl implements CiMediator {

    private final PipelineColleague pipelineColleague;
    private final AgentColleague agentColleague;
    private final NotificationColleague notificationColleague;

    public CiMediatorImpl(PipelineColleague pipelineColleague,
                          AgentColleague agentColleague,
                          NotificationColleague notificationColleague) {
        this.pipelineColleague = pipelineColleague;
        this.agentColleague = agentColleague;
        this.notificationColleague = notificationColleague;
    }

    @Override
    public MediatorDemoResultDto runPipelineWithAgent(String pipelineName, boolean failTests) {
        notificationColleague.clear();

        PipelineDto pipeline = pipelineColleague.createAndStart(pipelineName);

        JobResultDto jobResult = agentColleague.runJobForPipeline(pipeline.getId(), failTests);

        PipelineDto updatedPipeline = pipelineColleague.getPipeline(pipeline.getId());

        return new MediatorDemoResultDto(
                updatedPipeline,
                jobResult,
                notificationColleague.getNotifications()
        );
    }

    @Override
    public void onJobCompleted(Long pipelineId, JobResultDto jobResult) {
        boolean success = jobResult.isSuccess();
        if (success) {
            pipelineColleague.markSucceeded(pipelineId);
        } else {
            pipelineColleague.markFailed(pipelineId);
        }

        String pipelineName = pipelineColleague.getPipelineName(pipelineId);
        notificationColleague.notifyPipelineFinished(pipelineName, success);
    }
}