package org.example.jenkinstool.pipeline.execution.definition;

import java.util.ArrayList;
import java.util.List;

public class PipelineDefinition {

    private List<StageDefinition> stages = new ArrayList<>();

    public List<StageDefinition> getStages() {
        return stages;
    }

    public void setStages(List<StageDefinition> stages) {
        this.stages = stages == null ? new ArrayList<>() : new ArrayList<>(stages);
    }
}
