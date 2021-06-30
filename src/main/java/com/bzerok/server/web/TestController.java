package com.bzerok.server.web;

import com.bzerok.server.service.test.TestService;
import com.bzerok.server.web.dto.TestSaveRequestDto;
import com.bzerok.server.web.dto.TestUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/api/v1/test")
    public Long save(@RequestBody TestSaveRequestDto requestDto) {
        return testService.save(requestDto);
    }

    @PutMapping("/api/v1/test/{id}")
    public Long update(@PathVariable Long id, @RequestBody TestUpdateRequestDto requestDto) {
        return testService.update(id, requestDto);
    }

}
