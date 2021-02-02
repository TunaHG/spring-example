package com.naver.example.dto;

import com.naver.example.domain.Memo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoRequestDto {
    private String name;
    private String content;

    public MemoRequestDto(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Memo toEntity() {
        return new Memo(
                name,
                LocalDateTime.now(),
                content
        );
    }
}
