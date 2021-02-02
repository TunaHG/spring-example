package com.naver.example.controller;

import com.naver.example.domain.Memo;
import com.naver.example.dto.MemoRequestDto;
import com.naver.example.service.MemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class MemoController {
    private MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/api/memos")
    public List<Memo> findAll() {
        return memoService.findAll();
    }

    @GetMapping("/api/memos/{id}")
    public ResponseEntity<Memo> findById(@PathVariable Long id) {
        Memo memo = memoService.findById(id);
        return ResponseEntity.ok(memo);
    }

    @PostMapping("/api/memos")
    public ResponseEntity<Void> save(@RequestBody MemoRequestDto requestDto) throws URISyntaxException {
        Long id = memoService.save(requestDto);

        return ResponseEntity.created(new URI("/api/memos/" + id)).build();
    }
}
