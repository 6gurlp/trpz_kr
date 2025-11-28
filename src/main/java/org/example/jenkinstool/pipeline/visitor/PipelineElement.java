package org.example.jenkinstool.pipeline.visitor;

public interface PipelineElement {
    void accept(PipelineVisitor visitor);
}