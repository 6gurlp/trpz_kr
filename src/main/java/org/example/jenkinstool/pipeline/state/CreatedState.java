package org.example.jenkinstool.pipeline.state;

public class CreatedState extends AbstractPipelineState {

    @Override
    public String getName() {
        return "CREATED";
    }

    @Override
    public void queue(PipelineContext ctx) {
        ctx.setState(new QueuedState());
    }
}