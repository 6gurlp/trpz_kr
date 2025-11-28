package org.example.jenkinstool.demo;

import org.example.jenkinstool.pipeline.state.PipelineContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demo/pipelines")
public class PipelineDemoController {

    private final PipelineDemoService service;

    public PipelineDemoController(PipelineDemoService service) {
        this.service = service;
    }

    private PipelineDto toDto(PipelineContext ctx) {
        return new PipelineDto(
                ctx.getId(),
                ctx.getName(),
                ctx.getCurrentState(),
                ctx.getHistory()
        );
    }

    @GetMapping
    public ResponseEntity<List<PipelineDto>> list() {
        List<PipelineDto> pipelines = service.getAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pipelines);
    }

    @PostMapping
    public ResponseEntity<PipelineDto> create(@RequestParam String name) {
        PipelineContext ctx = service.createPipeline(name);
        return ResponseEntity.ok(toDto(ctx));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PipelineDto> get(@PathVariable Long id) {
        PipelineContext ctx = service.getPipeline(id);
        return ResponseEntity.ok(toDto(ctx));
    }

    @PostMapping("/{id}/queue")
    public ResponseEntity<PipelineDto> queue(@PathVariable Long id) {
        PipelineContext ctx = service.queue(id);
        return ResponseEntity.ok(toDto(ctx));
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<PipelineDto> start(@PathVariable Long id) {
        PipelineContext ctx = service.start(id);
        return ResponseEntity.ok(toDto(ctx));
    }

    @PostMapping("/{id}/succeed")
    public ResponseEntity<PipelineDto> succeed(@PathVariable Long id) {
        PipelineContext ctx = service.succeed(id);
        return ResponseEntity.ok(toDto(ctx));
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<PipelineDto> fail(@PathVariable Long id) {
        PipelineContext ctx = service.fail(id);
        return ResponseEntity.ok(toDto(ctx));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<PipelineDto> cancel(@PathVariable Long id) {
        PipelineContext ctx = service.cancel(id);
        return ResponseEntity.ok(toDto(ctx));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleNotFound(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}