package org.example.jenkinstool.pipeline.visitor;

public interface PipelineVisitor {

    void visit(PipelineDefinitionNode pipeline);

    void visit(StageNode stage);

    void visit(JobNode job);
}