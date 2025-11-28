package org.example.jenkinstool.soa.agent;

import org.example.jenkinstool.job.demo.CommandDemoService;
import org.example.jenkinstool.job.demo.JobResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/soa/agent")
public class AgentSoaController {

    private final CommandDemoService commandDemoService;

    public AgentSoaController(CommandDemoService commandDemoService) {
        this.commandDemoService = commandDemoService;
    }

    @PostMapping("/jobs/run")
    public ResponseEntity<JobResultDto> runJob(
            @RequestParam(defaultValue = "false") boolean failTests
    ) {
        JobResultDto result = commandDemoService.runDemoJob(failTests);
        return ResponseEntity.ok(result);
    }
}