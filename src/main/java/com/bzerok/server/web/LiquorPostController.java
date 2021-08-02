package com.bzerok.server.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.bzerok.server.service.liquor.LiquorPostService;
import com.bzerok.server.web.dto.LiquorResponseDto;
import com.bzerok.server.web.dto.LiquorSaveRequestDto;
import com.bzerok.server.web.dto.LiquorUpdateRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LiquorPostController {

    private final LiquorPostService liquorPostService;
    private final static Logger logger = LoggerFactory.getLogger(LiquorPostController.class);

    @PostMapping("/api/v1/liquor")
    public String save(HttpServletResponse response, @RequestBody LiquorSaveRequestDto requestDto) throws JsonProcessingException {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        Map<String, Object> jsonData = new HashMap<>();

        logger.debug(">> User ID :: {}", userId);
        logger.debug(">> {}", SecurityContextHolder.getContext().getAuthentication());

        Long result = liquorPostService.save(userId, requestDto);

        if (result != null) {
            jsonData.put("code", 200);
            jsonData.put("message", "등록 성공");
        }
        else {
            jsonData.put("code", 500);
            jsonData.put("message", "등록 실패");
        }

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
    }

    @PutMapping("/api/v1/liquor/{liquorPostId}")
    public String update(@PathVariable Long liquorPostId, @RequestBody LiquorUpdateRequestDto requestDto) throws JsonProcessingException {
        Long result = liquorPostService.update(liquorPostId, requestDto);
        Map<String, Object> jsonData = new HashMap<String, Object>();

        if (result != null) {
            jsonData.put("code", 200);
            jsonData.put("message", "수정 성공");
        }
        else {
            jsonData.put("code", 500);
            jsonData.put("message", "수정 실패");
        }

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
    }

    @DeleteMapping("/api/v1/liquor/{liquorPostId}")
    public String delete(@PathVariable Long liquorPostId) throws JsonProcessingException {
        Map<String, Object> jsonData = new HashMap<String, Object>();

        liquorPostService.delete(liquorPostId);

        jsonData.put("code", 200);
        jsonData.put("message", "삭제 성공");

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
    }

    @GetMapping("/api/v1/liquor")
    public String findById() throws JsonProcessingException {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        List<LiquorResponseDto> results = liquorPostService.findByUserId(userId);
        Map<String, Object> jsonData = new HashMap<String, Object>();

        if (results != null) {
            jsonData.put("code", 200);
            jsonData.put("message", "조회 성공");
            jsonData.put("data", results);
        }
        else {
            jsonData.put("code", 500);
            jsonData.put("message", "해당 사용자의 게시물이 존재하지 않습니다. userId=" + userId);
            jsonData.put("data", "");
        }

        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
    }

}
