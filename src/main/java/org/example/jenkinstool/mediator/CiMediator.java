package org.example.jenkinstool.mediator;

import org.example.jenkinstool.job.demo.JobResultDto;

public interface CiMediator {

    MediatorDemoResultDto runPipelineWithAgent(String pipelineName, boolean failTests);
    void onJobCompleted(Long pipelineId, JobResultDto jobResult);
}