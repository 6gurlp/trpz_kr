package org.example.jenkinstool.pipeline.state;

public abstract class AbstractPipelineState implements PipelineState {

    protected void unsupported(String action) {
        throw new IllegalStateException(
                "Cannot " + action + " when pipeline is in state " + getName()
        );
    }

    @Override
    public void queue(PipelineContext ctx) {
        unsupported("queue");
    }

    @Override
    public void start(PipelineContext ctx) {
        unsupported("start");
    }

    @Override
    public void succeed(PipelineContext ctx) {
        unsupported("succeed");
    }

    @Override
    public void fail(PipelineContext ctx) {
        unsupported("fail");
    }

    @Override
    public void cancel(PipelineContext ctx) {
        unsupported("cancel");
    }
}