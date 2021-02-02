package com.naver.example.controller;

import com.naver.example.domain.Memo;
import com.naver.example.dto.MemoRequestDto;
import com.naver.example.service.MemoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemoControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemoService memoService;

    @Test
    @DisplayName("메모 저장 잘 됨! 👍")
    void test() throws URISyntaxException {
        String name = "이름";
        String content = "내용";

        MemoRequestDto requestDto = new MemoRequestDto(name, content);

        String url = "http://localhost:" + port + "/api/memos";

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestDto, Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isEqualTo(new URI("/api/memos/1"));

        Memo entity = memoService.findById(1L);

        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getContent()).isEqualTo(content);
        assertThat(entity.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("없는 ID로 조회 안됨! 😭")
    void test_failed() {
        assertThatThrownBy(() -> {
            memoService.findById(100L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("메모를 찾을 수 없습니다.");
    }
}