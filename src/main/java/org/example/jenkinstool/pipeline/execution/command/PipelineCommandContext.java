package org.example.jenkinstool.pipeline.execution.command;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PipelineCommandContext {

    private final Path workingDirectory;
    private final Path repositoryDirectory;
    private final Consumer<String> logConsumer;

    public PipelineCommandContext(Path workingDirectory,
                                  Path repositoryDirectory,
                                  Consumer<String> logConsumer) {
        this(workingDirectory, repositoryDirectory, logConsumer, logConsumer);
    }

    public PipelineCommandContext(Path workingDirectory,
                                  Path repositoryDirectory,
                                  Consumer<String> logConsumer,
                                  Consumer<String> stageLogConsumer) {
        this.workingDirectory = workingDirectory;
        this.repositoryDirectory = repositoryDirectory;
        this.logConsumer = combine(logConsumer, stageLogConsumer);
    }

    public Path getWorkingDirectory() {
        return workingDirectory;
    }

    public Path getRepositoryDirectory() {
        return repositoryDirectory;
    }

    public void log(String message) {
        logConsumer.accept(message);
    }

    private Consumer<String> combine(Consumer<String> primary, Consumer<String> secondary) {
        if (primary == null && secondary == null) {
            return line -> { };
        }
        if (primary == null) {
            return secondary;
        }
        if (secondary == null) {
            return primary;
        }
        return line -> {
            primary.accept(line);
            secondary.accept(line);
        };
    }

    public int execute(List<String> command, Path directory) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(directory.toFile());
        builder.redirectErrorStream(true);
        log("\n$ " + command.stream().collect(Collectors.joining(" ")));
        Process process = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            reader.lines().forEach(logConsumer);
        }
        int exitCode = process.waitFor();
        log("Exit code: " + exitCode);
        return exitCode;
    }
}
