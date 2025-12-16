package org.example.jenkinstool.pipeline.execution.definition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.util.ArrayList;
import java.util.List;

public class PipelineDefinitionParser {

    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public PipelineDefinition parse(String rawDefinition) {
        if (rawDefinition == null || rawDefinition.isBlank()) {
            throw new IllegalArgumentException("Pipeline definition cannot be empty");
        }

        PipelineDefinition definition = tryParseYaml(rawDefinition);
        if (definition == null || definition.getStages().isEmpty()) {
            definition = parseLegacyScript(rawDefinition);
        }

        normalizeStages(definition);
        validate(definition);
        return definition;
    }

    private PipelineDefinition tryParseYaml(String rawDefinition) {
        try {
            return yamlMapper.readValue(rawDefinition, PipelineDefinition.class);
        } catch (Exception ignored) {
            return null;
        }
    }

    private PipelineDefinition parseLegacyScript(String rawDefinition) {
        PipelineDefinition definition = new PipelineDefinition();
        List<StageDefinition> stages = new ArrayList<>();
        String[] lines = rawDefinition.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isBlank()) {
                continue;
            }
            StageDefinition stage = new StageDefinition();
            stage.setName("stage-" + (i + 1));
            stage.setRun(line);
            stages.add(stage);
        }
        definition.setStages(stages);
        return definition;
    }

    private void normalizeStages(PipelineDefinition definition) {
        List<StageDefinition> normalized = new ArrayList<>();
        for (StageDefinition stage : definition.getStages()) {
            if (stage == null) {
                continue;
            }
            StageDefinition copy = new StageDefinition();
            copy.setName(stage.getName() == null ? null : stage.getName().trim());
            copy.setRun(stage.getRun() == null ? null : stage.getRun().trim());
            normalized.add(copy);
        }
        definition.setStages(normalized);
    }

    private void validate(PipelineDefinition definition) {
        List<StageDefinition> stages = definition.getStages();
        if (stages.isEmpty()) {
            throw new IllegalArgumentException("Pipeline must contain at least one stage");
        }

        for (int i = 0; i < stages.size(); i++) {
            StageDefinition stage = stages.get(i);
            if (stage.getRun() == null || stage.getRun().isBlank()) {
                throw new IllegalArgumentException("Stage run command cannot be empty (stage index " + (i + 1) + ")");
            }
            if (stage.getName() == null || stage.getName().isBlank()) {
                stage.setName("stage-" + (i + 1));
            }
        }
    }
}
