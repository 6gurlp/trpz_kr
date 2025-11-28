package org.example.jenkinstool.job.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/commands")
public class CommandDemoController {

    private final CommandDemoService service;

    public CommandDemoController(CommandDemoService service) {
        this.service = service;
    }

    @GetMapping("/run")
    public ResponseEntity<JobResultDto> runJob(
            @RequestParam(defaultValue = "false") boolean failTests
    ) {
        JobResultDto result = service.runDemoJob(failTests);
        return ResponseEntity.ok(result);
    }
}