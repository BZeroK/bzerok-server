package com.bzerok.server.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.bzerok.server.config.security.oauth2.dto.SessionUser;
import com.bzerok.server.service.liquor.LiquorPostService;
import com.bzerok.server.web.dto.LiquorResponseDto;
import com.bzerok.server.web.dto.LiquorSaveRequestDto;
import com.bzerok.server.web.dto.LiquorUpdateRequestDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LiquorPostController {

    @Value("${app.session.attributes.user}")
    private String SESSION_USER;

    private final LiquorPostService liquorPostService;
    private final HttpSession httpSession;
    private final static Logger logger = LoggerFactory.getLogger(LiquorPostController.class);

    @PostMapping("/api/v1/liquor")
    public String save(@RequestBody LiquorSaveRequestDto requestDto) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute(SESSION_USER);
        Long userId = sessionUser.getUserId();
        Long result = liquorPostService.save(userId, requestDto);
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

    @GetMapping("/api/v1/liquor")
    public String findById() {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute(SESSION_USER);
        Long userId = sessionUser.getUserId();
        List<LiquorResponseDto> results = liquorPostService.findByUserId(userId);
        JsonObject response = new JsonObject();
        JsonArray data = new JsonArray();

        if (results != null) {
            for (LiquorResponseDto result : results) {
                JsonObject temp = new JsonObject();
                temp.addProperty("liquorPostId", result.getLiquorPostId());
                temp.addProperty("userId", result.getUserId());
                temp.addProperty("name", result.getName());
                temp.addProperty("category", result.getCategory());
                temp.addProperty("volume", result.getVolume());
                temp.addProperty("price", result.getPrice());
                temp.addProperty("rate", result.getRate());
                temp.addProperty("picture", result.getPicture());
                temp.addProperty("etc", result.getEtc());

                data.add(temp);
            }

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
