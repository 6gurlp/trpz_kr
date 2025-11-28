package org.example.jenkinstool.pipeline.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StageNode implements PipelineElement {

    private final String name;
    private final List<JobNode> jobs = new ArrayList<>();

    public StageNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<JobNode> getJobs() {
        return Collections.unmodifiableList(jobs);
    }

    public StageNode addJob(JobNode job) {
        this.jobs.add(job);
        return this;
    }

    @Override
    public void accept(PipelineVisitor visitor) {
        visitor.visit(this);
        for (JobNode job : jobs) {
            job.accept(visitor);
        }
    }
}