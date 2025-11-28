package org.example.jenkinstool.pipeline.state;

public class FailedState extends AbstractPipelineState {

    @Override
    public String getName() {
        return "FAILED";
    }
}