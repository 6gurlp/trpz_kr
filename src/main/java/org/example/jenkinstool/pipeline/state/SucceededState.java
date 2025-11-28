package org.example.jenkinstool.pipeline.state;

public class SucceededState extends AbstractPipelineState {

    @Override
    public String getName() {
        return "SUCCEEDED";
    }
}