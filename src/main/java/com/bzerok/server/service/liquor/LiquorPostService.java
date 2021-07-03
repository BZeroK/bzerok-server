package com.bzerok.server.service.liquor;

import javax.transaction.Transactional;

import com.bzerok.server.domain.liquor.Liquor;
import com.bzerok.server.domain.liquor.LiquorRepository;
//import com.bzerok.server.domain.liquorCategories.LiquorCategoriesRepository;
import com.bzerok.server.web.dto.LiquorResponseDto;
import com.bzerok.server.web.dto.LiquorSaveRequestDto;
import com.bzerok.server.web.dto.LiquorUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LiquorPostService {

    private final LiquorRepository liquorRepository;
//    private final LiquorCategoriesRepository liquorCategoriesRepository;

    @Transactional
    public Long save(LiquorSaveRequestDto requestDto) {
        return liquorRepository.save(requestDto.toEntity()).getLiquorPostId();
    }

    @Transactional
    public Long update(Long liquorPostId, LiquorUpdateRequestDto requestDto) {
        Liquor liquor = liquorRepository.findById(liquorPostId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        liquor.update(requestDto.getName(), requestDto.getCategory(), requestDto.getVolume(), requestDto.getPrice(), requestDto.getRate(), requestDto.getPicture(), requestDto.getEtc());

        return liquorPostId;
    }

    @Transactional
    public LiquorResponseDto findByUserId(Long userId) {
        Liquor liquor = liquorRepository.findByUserId(userId);

        if (liquor == null) return null;
        else return new LiquorResponseDto(liquor);
    }

//    @Transactional
//    public Long delete(Long liquorPostId) {
//
//    }

}
