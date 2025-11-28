package org.example.jenkinstool.mediator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/mediator")
public class MediatorDemoController {

    private final MediatorDemoService service;

    public MediatorDemoController(MediatorDemoService service) {
        this.service = service;
    }

    @GetMapping("/run")
    public ResponseEntity<MediatorDemoResultDto> run(
            @RequestParam(defaultValue = "DemoPipeline") String name,
            @RequestParam(defaultValue = "false") boolean failTests
    ) {
        MediatorDemoResultDto result = service.runDemo(name, failTests);
        return ResponseEntity.ok(result);
    }
}