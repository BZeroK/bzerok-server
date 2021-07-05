package com.bzerok.server.web;

import com.bzerok.server.service.liquor.LiquorPostService;
import com.bzerok.server.web.dto.LiquorResponseDto;
import com.bzerok.server.web.dto.LiquorSaveRequestDto;
import com.bzerok.server.web.dto.LiquorUpdateRequestDto;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LiquorPostController {

    private final LiquorPostService liquorPostService;

    @PostMapping("/api/v1/liquor")
    public String save(@RequestBody LiquorSaveRequestDto requestDto) {
        Long result = liquorPostService.save(requestDto);
        JsonObject response = new JsonObject();

        if (result != null) {
            response.addProperty("code", 200);
            response.addProperty("message", "등록 성공");
            response.addProperty("data", "");
        }
        else {
            response.addProperty("code", 500);
            response.addProperty("message", "등록 실패");
            response.addProperty("data", "");
        }

        return response.toString();
    }

    @PutMapping("/api/v1/liquor/{liquorPostId}")
    public String update(@PathVariable Long liquorPostId, @RequestBody LiquorUpdateRequestDto requestDto) {
        Long result = liquorPostService.update(liquorPostId, requestDto);
        JsonObject response = new JsonObject();

        if (result != null) {
            response.addProperty("code", 200);
            response.addProperty("message", "수정 성공");
            response.addProperty("data", "");
        }
        else {
            response.addProperty("code", 500);
            response.addProperty("message", "수정 실패");
            response.addProperty("data", "");
        }

        return response.toString();
    }

    @DeleteMapping("/api/v1/liquor/{liquorPostId}")
    public String delete(@PathVariable Long liquorPostId) {
        liquorPostService.delete(liquorPostId);
        JsonObject response = new JsonObject();

        response.addProperty("code", 200);
        response.addProperty("message", "삭제 성공");
        response.addProperty("data", "");

        return response.toString();
    }

    @GetMapping("/api/v1/liquor/{userId}")
    public String findById(@PathVariable Long userId) {
        LiquorResponseDto result = liquorPostService.findByUserId(userId);
        JsonObject response = new JsonObject();
        JsonObject data = new JsonObject();

        if (result != null) {
            data.addProperty("liquorPostId", result.getLiquorPostId());
            data.addProperty("userId", result.getUserId());
            data.addProperty("name", result.getName());
            data.addProperty("category", result.getCategory());
            data.addProperty("volume", result.getVolume());
            data.addProperty("price", result.getPrice());
            data.addProperty("rate", result.getRate());
            data.addProperty("picture", result.getPicture());
            data.addProperty("etc", result.getEtc());

            response.addProperty("code", 200);
            response.addProperty("message", "조회 성공");
            response.add("data", data);
        }
        else {
            response.addProperty("code", 500);
            response.addProperty("message", "해당 사용자의 게시물이 존재하지 않습니다. userId=" + userId);
            response.addProperty("data", "");
        }

        return response.toString();
    }


}
