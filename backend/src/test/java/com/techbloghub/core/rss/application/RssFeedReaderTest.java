package com.techbloghub.core.rss.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.techbloghub.common.ApplicationTest;
import com.techbloghub.core.blog.domain.Blog;
import com.techbloghub.core.rss.domain.RssFeed;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("Rss Feed Reader 컴포넌트 테스트")
public class RssFeedReaderTest extends ApplicationTest {
    
    @Autowired
    RssFeedReader rssFeedReader;
    
    @Nested
    class 피드_읽기 {
        
        @Test
        void 모든_피드_읽기() {
            Arrays.asList(Blog.values()).forEach(블로그 -> {
                // given
                String 피드_URL = 블로그.getRssFeedUrl();
                
                // when
                List<RssFeed> 읽은_피드_목록 = rssFeedReader.read(피드_URL);
                
                // then
                System.out.println("읽은_피드_목록" + 읽은_피드_목록);
                assertThat(읽은_피드_목록).isNotEmpty();
            });
        }
    }
    
}
