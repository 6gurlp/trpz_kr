package org.example.jenkinstool.pipeline.execution.command;

public interface PipelineCommand {
    void accept(PipelineCommandVisitor visitor);

    int execute(PipelineCommandContext context) throws Exception;
}
