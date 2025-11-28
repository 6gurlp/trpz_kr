package org.example.jenkinstool.pipeline.state;

public class RunningState extends AbstractPipelineState {

    @Override
    public String getName() {
        return "RUNNING";
    }

    @Override
    public void succeed(PipelineContext ctx) {
        ctx.setState(new SucceededState());
    }

    @Override
    public void fail(PipelineContext ctx) {
        ctx.setState(new FailedState());
    }

    @Override
    public void cancel(PipelineContext ctx) {
        ctx.setState(new CancelledState());
    }
}