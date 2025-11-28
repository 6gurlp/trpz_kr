package org.example.jenkinstool.soa.core;

import org.example.jenkinstool.mediator.MediatorDemoResultDto;
import org.example.jenkinstool.mediator.MediatorDemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/soa/core")
public class CoreSoaController {

    private final MediatorDemoService mediatorDemoService;

    public CoreSoaController(MediatorDemoService mediatorDemoService) {
        this.mediatorDemoService = mediatorDemoService;
    }

    @PostMapping("/pipelines/run")
    public ResponseEntity<MediatorDemoResultDto> runPipeline(
            @RequestParam(defaultValue = "DemoProject") String name,
            @RequestParam(defaultValue = "false") boolean failTests
    ) {
        MediatorDemoResultDto result = mediatorDemoService.runDemo(name, failTests);
        return ResponseEntity.ok(result);
    }
}