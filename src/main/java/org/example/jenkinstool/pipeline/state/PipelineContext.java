package org.example.jenkinstool.pipeline.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PipelineContext {

    private final Long id;
    private final String name;
    private PipelineState state;
    private final List<String> history;

    public PipelineContext(Long id, String name) {
        this(id, name, new CreatedState(), List.of("CREATED"));
    }

    public PipelineContext(Long id, String name, PipelineState state, List<String> history) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.history = new ArrayList<>(history);
        if (this.history.isEmpty()) {
            this.history.add(state.getName());
        }
    }

    void setState(PipelineState state) {
        this.state = state;
        this.history.add(state.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrentState() {
        return state.getName();
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void queue() {
        state.queue(this);
    }

    public void start() {
        state.start(this);
    }

    public void succeed() {
        state.succeed(this);
    }

    public void fail() {
        state.fail(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public static PipelineState stateFromName(String name) {
        return switch (name) {
            case "CREATED" -> new CreatedState();
            case "QUEUED" -> new QueuedState();
            case "RUNNING" -> new RunningState();
            case "SUCCEEDED" -> new SucceededState();
            case "FAILED" -> new FailedState();
            case "CANCELLED" -> new CancelledState();
            default -> throw new IllegalArgumentException("Unknown pipeline state: " + name);
        };
    }
}