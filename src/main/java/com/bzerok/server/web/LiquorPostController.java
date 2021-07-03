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
            response.addProperty("data", "");
            response.addProperty("message", "등록 성공");
        }
        else {
            response.addProperty("code", 500);
            response.addProperty("data", "");
            response.addProperty("message", "등록 실패");
        }

        return response.toString();
    }

    @PutMapping("/api/v1/liquor/{liquorPostId}")
    public String update(@PathVariable Long liquorPostId, @RequestBody LiquorUpdateRequestDto requestDto) {
        Long result = liquorPostService.update(liquorPostId, requestDto);
        JsonObject response = new JsonObject();

        if (result != null) {
            response.addProperty("code", 200);
            response.addProperty("data", "");
            response.addProperty("message", "수정 성공");
        }
        else {
            response.addProperty("code", 500);
            response.addProperty("data", "");
            response.addProperty("message", "수정 실패");
        }

        return response.toString();
    }

    @GetMapping("/api/v1/liquor/{userId}")
    public String findById(@PathVariable Long userId) {
        LiquorResponseDto result = liquorPostService.findByUserId(userId);
        JsonObject response = new JsonObject();

        if (result != null) {
            response.addProperty("code", 200);
            response.addProperty("data", result.toString());
            response.addProperty("message", "조회 성공");
        }
        else {
            response.addProperty("code", 500);
            response.addProperty("data", "");
            response.addProperty("message", "게시물이 존재하지 않습니다. liquor_post_id = " + userId);
        }

        return response.toString();
    }

//    @DeleteMapping("/api/v1/liquor/{liquorPostId}")
//    public String delete(@PathVariable Long liquorPostId) {
//        Long result = liquorPostService.delete(liquorPostId);
//
//        if (result != null) return responseBuilder.build(200, "", "삭제 성공");
//        else return responseBuilder.build(500, "", "게시물이 존재하지 않습니다. liquor_post_id = " + liquorPostId);
//    }


}
