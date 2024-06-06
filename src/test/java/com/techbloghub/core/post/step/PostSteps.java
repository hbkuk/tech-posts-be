package com.techbloghub.core.post.step;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostSteps {

    public static final int 페이지_기본_사이즈 = 10;

    public static ExtractableResponse<Response> 게시글_목록_요청() {
        return given().log().all()
                .when().log().all()
                .get("/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 게시글_목록_요청(int size, int pageNumber, String sort) {
        Map<String, String> 파라미터_목록 = new HashMap<>();
        파라미터_목록.put("size", String.valueOf(size));
        파라미터_목록.put("page", String.valueOf(pageNumber));
        파라미터_목록.put("sort", sort);

        return given().log().all()
                .params(파라미터_목록)
                .when().log().all()
                .get("/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static List<String> 게시글_제목만_분류하기(ExtractableResponse<Response> 게시글_목록_응답) {
        return 게시글_목록_응답.jsonPath().getList("title", String.class);
    }
}
