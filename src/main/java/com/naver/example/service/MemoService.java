package com.naver.example.service;

import com.naver.example.domain.Memo;
import com.naver.example.domain.MemoRepository;
import com.naver.example.dto.MemoRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {
    private MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public Long save(MemoRequestDto requestDto) {
        Memo entity = requestDto.toEntity();
        memoRepository.save(entity);

        return entity.getId();
    }

    public List<Memo> findAll() {
        return memoRepository.findAll();
    }

    public Memo findById(Long id) {
        return memoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));
    }
}
