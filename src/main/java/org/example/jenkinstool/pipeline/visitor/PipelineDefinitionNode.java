package org.example.jenkinstool.pipeline.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PipelineDefinitionNode implements PipelineElement {

    private final String name;
    private final List<StageNode> stages = new ArrayList<>();

    public PipelineDefinitionNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<StageNode> getStages() {
        return Collections.unmodifiableList(stages);
    }

    public PipelineDefinitionNode addStage(StageNode stage) {
        this.stages.add(stage);
        return this;
    }

    @Override
    public void accept(PipelineVisitor visitor) {
        visitor.visit(this);
        for (StageNode stage : stages) {
            stage.accept(visitor);
        }
    }
}