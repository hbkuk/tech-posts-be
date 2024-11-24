package com.techbloghub.core.post.presentation;

import static com.techbloghub.core.post.converter.PostConverter.convertResponse;

import com.techbloghub.common.domain.pagination.CursorPaged;
import com.techbloghub.common.domain.pagination.dto.CursorPagedResponse;
import com.techbloghub.core.post.application.PostService;
import com.techbloghub.core.post.domain.Post;
import com.techbloghub.core.post.presentation.dto.PostResponse;
import com.techbloghub.core.post.presentation.dto.PostSearchConditionRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    
    @GetMapping("/api/posts")
    public ResponseEntity<CursorPagedResponse<PostResponse>> findAllPosts(@Valid @ModelAttribute PostSearchConditionRequest request) {
        CursorPaged<Post> cursorPaged = postService.findAllByCondition(request.toEntity());
        return ResponseEntity.ok(convertResponse(cursorPaged));
    }
}
