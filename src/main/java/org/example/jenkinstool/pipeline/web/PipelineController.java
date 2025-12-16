package org.example.jenkinstool.pipeline.web;

import java.util.List;
import org.example.jenkinstool.pipeline.service.PipelineService;
import org.example.jenkinstool.pipeline.web.dto.PipelineRequest;
import org.example.jenkinstool.pipeline.web.dto.PipelineResponse;
import org.example.jenkinstool.pipeline.web.dto.PipelineRunResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pipelines")
public class PipelineController {

    private final PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @GetMapping
    public List<PipelineResponse> listPipelines() {
        return pipelineService.listPipelines();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PipelineResponse create(@RequestBody PipelineRequest request) {
        return pipelineService.create(request);
    }

    @GetMapping("/{id}")
    public PipelineResponse get(@PathVariable Long id) {
        return pipelineService.get(id);
    }

    @PostMapping("/{id}/runs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PipelineRunResponse run(@PathVariable Long id) {
        return pipelineService.runPipeline(id);
    }
}
