package org.example.jenkinstool.demo;

import java.util.List;

public class PipelineDto {

    private Long id;
    private String name;
    private String state;
    private List<String> history;

    public PipelineDto(Long id, String name, String state, List<String> history) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.history = history;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public List<String> getHistory() {
        return history;
    }
}