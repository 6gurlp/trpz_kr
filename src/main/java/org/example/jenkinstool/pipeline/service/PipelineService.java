package org.example.jenkinstool.pipeline.service;

import java.util.List;
import java.util.stream.Collectors;
import org.example.jenkinstool.pipeline.execution.PipelineRunner;
import org.example.jenkinstool.pipeline.model.Pipeline;
import org.example.jenkinstool.pipeline.model.PipelineRepository;
import org.example.jenkinstool.pipeline.model.PipelineRun;
import org.example.jenkinstool.pipeline.model.PipelineRunRepository;
import org.example.jenkinstool.pipeline.model.StageResult;
import org.example.jenkinstool.pipeline.web.dto.PipelineRequest;
import org.example.jenkinstool.pipeline.web.dto.PipelineResponse;
import org.example.jenkinstool.pipeline.web.dto.PipelineRunResponse;
import org.example.jenkinstool.pipeline.web.dto.StageResultResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PipelineService {

    private final PipelineRepository pipelineRepository;
    private final PipelineRunRepository pipelineRunRepository;
    private final PipelineRunner pipelineRunner;

    public PipelineService(PipelineRepository pipelineRepository,
                           PipelineRunRepository pipelineRunRepository,
                           PipelineRunner pipelineRunner) {
        this.pipelineRepository = pipelineRepository;
        this.pipelineRunRepository = pipelineRunRepository;
        this.pipelineRunner = pipelineRunner;
    }

    public PipelineResponse create(PipelineRequest request) {
        Pipeline pipeline = new Pipeline();
        pipeline.setName(request.getName());
        pipeline.setRepositoryUrl(request.getRepositoryUrl());
        pipeline.setBranch(request.getBranch());
        pipeline.setScript(request.getScript());
        pipelineRepository.save(pipeline);
        return toResponse(pipeline, List.of());
    }

    public List<PipelineResponse> listPipelines() {
        return pipelineRepository.findAll().stream()
                .map(pipeline -> toResponse(pipeline, List.of()))
                .collect(Collectors.toList());
    }

    public PipelineResponse get(Long id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pipeline not found: " + id));
        List<PipelineRun> runs = pipelineRunRepository.findByPipelineOrderByQueuedAtDesc(pipeline);
        return toResponse(pipeline, runs);
    }

    @Transactional
    public PipelineRunResponse runPipeline(Long id) {
        Pipeline pipeline = pipelineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pipeline not found: " + id));
        PipelineRun run = pipelineRunner.queueRun(pipeline);
        return toRunResponse(run);
    }

    private PipelineResponse toResponse(Pipeline pipeline, List<PipelineRun> runs) {
        PipelineResponse response = new PipelineResponse();
        response.setId(pipeline.getId());
        response.setName(pipeline.getName());
        response.setRepositoryUrl(pipeline.getRepositoryUrl());
        response.setBranch(pipeline.getBranch());
        response.setScript(pipeline.getScript());
        response.setCreatedAt(pipeline.getCreatedAt());
        response.setRuns(runs.stream().map(this::toRunResponse).collect(Collectors.toList()));
        return response;
    }

    private PipelineRunResponse toRunResponse(PipelineRun run) {
        PipelineRunResponse response = new PipelineRunResponse();
        response.setId(run.getId());
        response.setStatus(run.getStatus());
        response.setQueuedAt(run.getQueuedAt());
        response.setStartedAt(run.getStartedAt());
        response.setFinishedAt(run.getFinishedAt());
        response.setLogOutput(run.getLogOutput());
        response.setStageResults(run.getStageResults().stream()
                .map(this::toStageResult)
                .collect(Collectors.toList()));
        return response;
    }

    private StageResultResponse toStageResult(StageResult stageResult) {
        StageResultResponse response = new StageResultResponse();
        response.setName(stageResult.getName());
        response.setStatus(stageResult.getStatus());
        response.setLogOutput(stageResult.getLogOutput());
        return response;
    }
}
