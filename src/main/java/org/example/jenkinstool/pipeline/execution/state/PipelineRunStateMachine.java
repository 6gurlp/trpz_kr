package org.example.jenkinstool.pipeline.execution.state;

import java.time.Instant;
import org.example.jenkinstool.pipeline.model.PipelineRun;

public class PipelineRunStateMachine {

    private PipelineRunState state;

    public PipelineRunStateMachine(PipelineRun run) {
        this.state = new QueuedState(run);
    }

    public void start() {
        state.start();
        if (!(state instanceof RunningState)) {
            state = new RunningState(((RunStateSupport) state).getRun());
        }
    }

    public void succeed() {
        state.succeed();
    }

    public void fail() {
        state.fail();
    }

    private interface RunStateSupport extends PipelineRunState {
        PipelineRun getRun();
    }

    private static abstract class BaseState implements RunStateSupport {
        private final PipelineRun run;

        protected BaseState(PipelineRun run) {
            this.run = run;
        }

        @Override
        public PipelineRun getRun() {
            return run;
        }
    }

    private static final class QueuedState extends BaseState {
        private QueuedState(PipelineRun run) {
            super(run);
        }

        @Override
        public void start() {
            getRun().setStatus(PipelineRun.Status.RUNNING);
            getRun().setStartedAt(Instant.now());
        }

        @Override
        public void succeed() {
            // ignore
        }

        @Override
        public void fail() {
            getRun().setStatus(PipelineRun.Status.FAILED);
            getRun().setFinishedAt(Instant.now());
        }
    }

    private static final class RunningState extends BaseState {
        private RunningState(PipelineRun run) {
            super(run);
        }

        @Override
        public void start() {
            // already started
        }

        @Override
        public void succeed() {
            getRun().setStatus(PipelineRun.Status.SUCCESS);
            getRun().setFinishedAt(Instant.now());
        }

        @Override
        public void fail() {
            getRun().setStatus(PipelineRun.Status.FAILED);
            getRun().setFinishedAt(Instant.now());
        }
    }
}
