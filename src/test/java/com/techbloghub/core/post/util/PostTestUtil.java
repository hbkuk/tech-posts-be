package com.techbloghub.core.post.util;

import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PostTestUtil {

    public static List<Post> 게시글_더미_데이터_생성(Blog 블로그, int 개수) {
        return IntStream.rangeClosed(1, 개수)
                .mapToObj(postIndex -> new Post(
                        블로그,
                        "https://techblog.woowahan.com/" + postIndex,
                        "더미 타이틀 " + postIndex,
                        LocalDateTime.now().plusDays(postIndex).withSecond(0).withNano(0),
                        "더미 설명 " + postIndex
                ))
                .collect(Collectors.toList());
    }
}
