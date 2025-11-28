package org.example.jenkinstool.demo;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pipelines")
public class PipelineRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "current_state")
    private String currentState;

    @ElementCollection
    @CollectionTable(name = "pipeline_history", joinColumns = @JoinColumn(name = "pipeline_id"))
    @OrderColumn(name = "order_index")
    @Column(name = "state")
    private List<String> history = new ArrayList<>();

    protected PipelineRecord() {
    }

    public PipelineRecord(String name, String currentState, List<String> history) {
        this.name = name;
        this.currentState = currentState;
        this.history = new ArrayList<>(history);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history.clear();
        this.history.addAll(history);
    }
}
