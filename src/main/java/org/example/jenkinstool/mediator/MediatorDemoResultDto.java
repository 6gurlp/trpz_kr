package org.example.jenkinstool.mediator;

import org.example.jenkinstool.demo.PipelineDto;
import org.example.jenkinstool.job.demo.JobResultDto;

import java.util.List;

public class MediatorDemoResultDto {

    private PipelineDto pipeline;
    private JobResultDto job;
    private List<String> notifications;

    public MediatorDemoResultDto(PipelineDto pipeline,
                                 JobResultDto job,
                                 List<String> notifications) {
        this.pipeline = pipeline;
        this.job = job;
        this.notifications = notifications;
    }

    public PipelineDto getPipeline() {
        return pipeline;
    }

    public JobResultDto getJob() {
        return job;
    }

    public List<String> getNotifications() {
        return notifications;
    }
}