package org.example.jenkinstool.pipeline.state;

public class CancelledState extends AbstractPipelineState {

    @Override
    public String getName() {
        return "CANCELLED";
    }
}