package org.example.jenkinstool.pipeline.execution.state;

public interface PipelineRunState {
    void start();

    void succeed();

    void fail();
}
