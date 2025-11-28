package org.example.jenkinstool.pipeline.state;

public class QueuedState extends AbstractPipelineState {

    @Override
    public String getName() {
        return "QUEUED";
    }

    @Override
    public void start(PipelineContext ctx) {
        ctx.setState(new RunningState());
    }

    @Override
    public void cancel(PipelineContext ctx) {
        ctx.setState(new CancelledState());
    }
}