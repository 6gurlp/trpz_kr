package org.example.jenkinstool.pipeline.execution.command;

import java.util.List;

public class ScriptCommand implements PipelineCommand {

    private final String script;

    public ScriptCommand(String script) {
        this.script = script;
    }

    @Override
    public void accept(PipelineCommandVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int execute(PipelineCommandContext context) throws Exception {
        String[] steps = script.split("\\r?\\n");
        int exitCode = 0;
        for (String step : steps) {
            if (step.isBlank()) {
                continue;
            }
            exitCode = context.execute(List.of("/bin/sh", "-c", step), context.getRepositoryDirectory());
            if (exitCode != 0) {
                return exitCode;
            }
        }
        return exitCode;
    }

    public String getScript() {
        return script;
    }
}
