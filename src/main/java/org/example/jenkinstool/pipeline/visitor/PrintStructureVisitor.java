package org.example.jenkinstool.pipeline.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintStructureVisitor implements PipelineVisitor {

    private final List<String> lines = new ArrayList<>();

    @Override
    public void visit(PipelineDefinitionNode pipeline) {
        lines.add("Pipeline: " + pipeline.getName());
    }

    @Override
    public void visit(StageNode stage) {
        lines.add("  Stage: " + stage.getName());
    }

    @Override
    public void visit(JobNode job) {
        lines.add("    Job: " + job.getName() + " (command: " + job.getCommand() + ")");
    }

    public List<String> getLines() {
        return Collections.unmodifiableList(lines);
    }
}