package org.example.jenkinstool.pipeline.visitor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo/visitor")
public class VisitorDemoController {

    private final VisitorDemoService service;

    public VisitorDemoController(VisitorDemoService service) {
        this.service = service;
    }

    @GetMapping("/analyze")
    public ResponseEntity<VisitorDemoResultDto> analyze(
            @RequestParam(defaultValue = "false") boolean broken
    ) {
        VisitorDemoResultDto result = service.analyzeDemoPipeline(broken);
        return ResponseEntity.ok(result);
    }
}