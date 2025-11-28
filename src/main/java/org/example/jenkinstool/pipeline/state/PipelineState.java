package org.example.jenkinstool.pipeline.state;

public interface PipelineState {

    String getName();

    void queue(PipelineContext ctx);

    void start(PipelineContext ctx);

    void succeed(PipelineContext ctx);

    void fail(PipelineContext ctx);

    void cancel(PipelineContext ctx);
}