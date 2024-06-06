package com.techbloghub.core.post.presentation;

import com.techbloghub.core.post.application.PostService;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPosts(
            @PageableDefault(sort = "publishAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

}
