package com.bzerok.server.service.test;

import com.bzerok.server.domain.test.Test;
import com.bzerok.server.domain.test.TestRepository;
import com.bzerok.server.web.dto.TestSaveRequestDto;
import com.bzerok.server.web.dto.TestUpdateRequestDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    @Transactional
    public Long save(TestSaveRequestDto requestDto) {
        return testRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, TestUpdateRequestDto requestDto) {
        Test test = testRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다 id = " + id));
        test.update(requestDto.getName(), requestDto.getAge());

        return id;
    }

}
