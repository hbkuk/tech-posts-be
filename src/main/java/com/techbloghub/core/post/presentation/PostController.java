package com.techbloghub.core.post.presentation;

import com.techbloghub.common.domain.pagination.PagedResponse;
import com.techbloghub.core.post.application.PostService;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글", description = "게시글 관련 엔드포인트")
@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 목록", description = "게시글 목록 조회;")
    @GetMapping("/api/posts")
    public ResponseEntity<PagedResponse<PostResponse>> findAllPosts(
        @PageableDefault(sort = "publishAt", direction = Sort.Direction.DESC) Pageable pageable) {
        PagedResponse<PostResponse> pagedResponse = postService.findAll(pageable);
        return ResponseEntity.ok(pagedResponse);
    }
}
