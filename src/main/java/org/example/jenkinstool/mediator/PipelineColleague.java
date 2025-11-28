package org.example.jenkinstool.mediator;

import org.example.jenkinstool.demo.PipelineDemoService;
import org.example.jenkinstool.demo.PipelineDto;
import org.example.jenkinstool.pipeline.state.PipelineContext;

public class PipelineColleague {

    private CiMediator mediator;
    private final PipelineDemoService pipelineDemoService;

    public PipelineColleague(PipelineDemoService pipelineDemoService) {
        this.pipelineDemoService = pipelineDemoService;
    }

    public void setMediator(CiMediator mediator) {
        this.mediator = mediator;
    }

    public PipelineDto createAndStart(String name) {
        PipelineContext ctx = pipelineDemoService.createPipeline(name);
        pipelineDemoService.queue(ctx.getId());
        pipelineDemoService.start(ctx.getId());
        PipelineContext updated = pipelineDemoService.getPipeline(ctx.getId());
        return toDto(updated);
    }

    public void markSucceeded(Long id) {
        pipelineDemoService.succeed(id);
    }

    public void markFailed(Long id) {
        pipelineDemoService.fail(id);
    }

    public PipelineDto getPipeline(Long id) {
        PipelineContext ctx = pipelineDemoService.getPipeline(id);
        return toDto(ctx);
    }

    public String getPipelineName(Long id) {
        return pipelineDemoService.getPipeline(id).getName();
    }

    private PipelineDto toDto(PipelineContext ctx) {
        return new PipelineDto(
                ctx.getId(),
                ctx.getName(),
                ctx.getCurrentState(),
                ctx.getHistory()
        );
    }
}