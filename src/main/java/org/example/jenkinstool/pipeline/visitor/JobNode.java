package org.example.jenkinstool.pipeline.visitor;

public class JobNode implements PipelineElement {

    private final String name;
    private final String command;

    public JobNode(String name, String command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public void accept(PipelineVisitor visitor) {
        visitor.visit(this);
    }
}