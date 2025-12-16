package org.example.jenkinstool.pipeline.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PipelineRunRepository extends JpaRepository<PipelineRun, Long> {

    List<PipelineRun> findByPipelineOrderByQueuedAtDesc(Pipeline pipeline);
}
