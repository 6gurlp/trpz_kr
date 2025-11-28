package org.example.jenkinstool.demo;

import org.example.jenkinstool.pipeline.state.PipelineContext;
import org.example.jenkinstool.pipeline.state.PipelineState;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.function.Consumer;


@Service
public class PipelineDemoService {

    private final PipelineRepository repository;

    public PipelineDemoService(PipelineRepository repository) {
        this.repository = repository;
    }

    public List<PipelineContext> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toContext)
                .toList();
    }

    public PipelineContext createPipeline(String name) {
        PipelineRecord record = new PipelineRecord(name, "CREATED", List.of("CREATED"));
        PipelineRecord saved = repository.save(record);
        return toContext(saved);
    }

    public PipelineContext getPipeline(Long id) {
        return toContext(findRecord(id));
    }

    public PipelineContext queue(Long id) {
        return updateState(id, PipelineContext::queue);
    }

    public PipelineContext start(Long id) {
        return updateState(id, PipelineContext::start);
    }

    public PipelineContext succeed(Long id) {
        return updateState(id, PipelineContext::succeed);
    }

    public PipelineContext fail(Long id) {
        return updateState(id, PipelineContext::fail);
    }

    public PipelineContext cancel(Long id) {
        return updateState(id, PipelineContext::cancel);
    }

    private PipelineContext updateState(Long id, Consumer<PipelineContext> action) {
        PipelineRecord record = findRecord(id);
        PipelineContext ctx = toContext(record);
        action.accept(ctx);
        persist(record, ctx);
        return ctx;
    }

    private PipelineRecord findRecord(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pipeline not found: " + id));
    }

    private PipelineContext toContext(PipelineRecord record) {
        PipelineState state = PipelineContext.stateFromName(record.getCurrentState());
        return new PipelineContext(record.getId(), record.getName(), state, record.getHistory());
    }

    private void persist(PipelineRecord record, PipelineContext ctx) {
        record.setCurrentState(ctx.getCurrentState());
        record.setHistory(ctx.getHistory());
        repository.save(record);
    }
}