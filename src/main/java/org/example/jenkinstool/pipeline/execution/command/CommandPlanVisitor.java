package org.example.jenkinstool.pipeline.execution.command;

public class CommandPlanVisitor implements PipelineCommandVisitor {

    private final StringBuilder plan = new StringBuilder();
    private final StringBuilder flow = new StringBuilder();
    private boolean firstStep = true;

    @Override
    public void visit(GitCloneCommand command) {
        plan.append("- Clone repo: ")
                .append(command.getRepositoryUrl())
                .append(" [branch ")
                .append(command.getBranch())
                .append("]\n");
        appendFlow("Clone repo");
    }

    @Override
    public void visit(ScriptCommand command) {
        plan.append("- Run script steps:\n");
        String[] steps = command.getScript().split("\\r?\\n");
        for (String step : steps) {
            if (!step.isBlank()) {
                plan.append("  * ").append(step.trim()).append('\n');
            }
        }
        appendFlow("Script");
    }

    @Override
    public void visit(StageCommand command) {
        plan.append("- Stage '")
                .append(command.getName())
                .append("': ")
                .append(command.getRun())
                .append('\n');
        appendFlow(command.getName());
    }

    public String render() {
        if (flow.length() > 0) {
            plan.append("\nPipeline flow: ")
                    .append(flow)
                    .append('\n');
        }
        return plan.toString();
    }

    private void appendFlow(String label) {
        if (!firstStep) {
            flow.append(" -> ");
        }
        flow.append(label);
        firstStep = false;
    }
}
