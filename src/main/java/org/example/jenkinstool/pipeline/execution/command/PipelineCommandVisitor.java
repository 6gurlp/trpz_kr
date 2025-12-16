package org.example.jenkinstool.pipeline.execution.command;

public interface PipelineCommandVisitor {
    void visit(GitCloneCommand command);

    void visit(ScriptCommand command);

    void visit(StageCommand command);
}
