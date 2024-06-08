package com.techbloghub.core.post.step;

import com.techbloghub.core.post.domain.Post;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PostSteps {

    public static final int 페이지_기본_사이즈 = 10;

    public static ExtractableResponse<Response> 게시글_목록_요청하기() {
        return given().log().all()
                .when().log().all()
                .get("/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static ExtractableResponse<Response> 게시글_목록_요청하기(PageRequest 페이지_요청_정보) {
        Map<String, String> 파라미터_목록 = new HashMap<>();
        파라미터_목록.put("size", String.valueOf(페이지_요청_정보.getPageSize()));
        파라미터_목록.put("page", String.valueOf(페이지_요청_정보.getPageNumber()));

        Sort sort = 페이지_요청_정보.getSort();
        Sort.Order order = sort.iterator().next();
        String sortParam = order.getProperty() + "," + order.getDirection().name().toLowerCase();
        파라미터_목록.put("sort", sortParam);

        return given().log().all()
                .params(파라미터_목록)
                .when().log().all()
                .get("/posts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    public static void 게시글_목록_검증하기(List<Post> 저장된_게시글_목록, ExtractableResponse<Response> 응답된_게시글_목록) {
        List<String> 예상하는_응답된_게시글_제목_목록 = 저장된_게시글_목록.stream()
                .sorted(Comparator.comparing(Post::getPublishAt).reversed())
                .limit(페이지_기본_사이즈)
                .map(Post::getTitle)
                .collect(Collectors.toList());

        List<String> 응답된_게시글_제목_목록 = 게시글_제목만_분류하기(응답된_게시글_목록);
        assertThat(응답된_게시글_제목_목록).isEqualTo(예상하는_응답된_게시글_제목_목록);
    }

    public static List<String> 게시글_제목만_분류하기(ExtractableResponse<Response> 게시글_목록_응답) {
        return 게시글_목록_응답.jsonPath().getList("title", String.class);
    }

    public static void 게시글_목록_검증하기(PageRequest 페이지_요청_정보, List<Post> 저장된_게시글_목록, ExtractableResponse<Response> 응답된_게시글_목록) {
        var 예상하는_응답된_게시글_제목_목록 = 저장된_게시글_목록.stream()
                .sorted(Comparator.comparing(Post::getPublishAt).reversed())
                .skip(5) // TODO: 페이지 계산 필요
                .limit(페이지_요청_정보.getPageSize())
                .map(Post::getTitle)
                .collect(Collectors.toList());
        var 응답된_게시글_제목_목록 = 게시글_제목만_분류하기(응답된_게시글_목록);
        assertThat(응답된_게시글_제목_목록).isEqualTo(예상하는_응답된_게시글_제목_목록);
    }
}
