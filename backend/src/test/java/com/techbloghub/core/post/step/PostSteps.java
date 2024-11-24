package com.techbloghub.core.post.step;

import static io.restassured.RestAssured.given;

import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.domain.Sort;
import com.techbloghub.core.post.presentation.dto.PostSearchConditionRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class PostSteps {
    
    public static ExtractableResponse<Response> 실패하는_게시글_목록_요청() {
        return given().log().all()
            .when().log().all()
            .get("/api/posts")
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract();
    }
    
    public static ExtractableResponse<Response> 게시글_목록_요청(PostSearchConditionRequest request) {
        Map<String, String> 파라미터_목록 = new HashMap<>();
        
        if (request.getSort() != null && !request.getSort().isEmpty()) {
            파라미터_목록.put("sort", request.getSort());
        }
        
        if (request.getCursor() != null) {
            파라미터_목록.put("lastId", request.getCursor());
        }
        
        if (request.getItemsPerPage() > 0) {
            파라미터_목록.put("itemsPerPage", String.valueOf(request.getItemsPerPage()));
        }
        
        if (request.getBlog() != null) {
            파라미터_목록.put("blog", request.getBlog());
        }
        
        return given().log().all()
            .params(파라미터_목록)
            .when().log().all()
            .get("/api/posts")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract();
    }
    
    public static PostSearchConditionRequest 게시글_목록_요청_조건_생성(int itemsPerPage) {
        return PostSearchConditionRequest.builder()
            .itemsPerPage(itemsPerPage)
            .build();
    }
    
    public static PostSearchConditionRequest 게시글_목록_요청_조건_생성(Sort sort, int itemPerPage) {
        return PostSearchConditionRequest.builder()
            .sort(sort.name())
            .itemsPerPage(itemPerPage)
            .build();
    }
    
    public static PostSearchConditionRequest 게시글_목록_요청_조건_생성(Sort sort, int itemPerPage, Blog blog) {
        return PostSearchConditionRequest.builder()
            .sort(sort.name())
            .itemsPerPage(itemPerPage)
            .blog(blog.getEnglishName())
            .build();
    }
}
