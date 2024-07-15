package com.techbloghub.core.post.presentation;

import com.techbloghub.common.domain.pagination.PagedResponse;
import com.techbloghub.common.domain.pagination.PaginationDefault;
import com.techbloghub.common.domain.pagination.PaginationRequest;
import com.techbloghub.core.post.application.PostService;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "게시글", description = "게시글 관련 엔드포인트")
@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    // TODO: 게시글 반응..

    @Operation(summary = "게시글 목록", description = "게시글 목록 조회")
    @GetMapping("/api/posts")
    public ResponseEntity<PagedResponse<PostResponse>> findAllPosts(
        @ParameterObject @PaginationDefault(sort = "publishAt", direction = "DESC") PaginationRequest request) {
        PagedResponse<PostResponse> pagedResponse = postService.findAll(request.toEntity());
        return ResponseEntity.ok(pagedResponse);
    }
}
