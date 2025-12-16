package org.example.jenkinstool.pipeline.execution.command;

import java.util.List;

public class StageCommand implements PipelineCommand {

    private final String name;
    private final String run;

    public StageCommand(String name, String run) {
        this.name = name;
        this.run = run;
    }

    @Override
    public void accept(PipelineCommandVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int execute(PipelineCommandContext context) throws Exception {
        context.log("\n== Stage: " + name + " ==");
        return context.execute(List.of("/bin/sh", "-c", run), context.getRepositoryDirectory());
    }

    public String getName() {
        return name;
    }

    public String getRun() {
        return run;
    }
}
