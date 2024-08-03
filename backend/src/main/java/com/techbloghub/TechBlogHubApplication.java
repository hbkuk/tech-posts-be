package com.techbloghub;

import com.techbloghub.common.exception.BadRequestException;
import com.techbloghub.common.exception.ForbiddenException;
import com.techbloghub.common.exception.InternalServerErrorException;
import com.techbloghub.common.exception.UnsupportedOAuthTypeException;
import com.techbloghub.common.exception.common.ErrorCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class TechBlogHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechBlogHubApplication.class, args);
    }

}

@RestController
@RequestMapping("/api/health")
class Health {

    @GetMapping
    String health() {
        return "UP";
    }

}

@RestController
@RequestMapping("/tests")
@Profile("local")
class Test {

    @GetMapping("/throw-not-found-exception")
    public void test1() {
        throw new BadRequestException(ErrorCode.NOT_FOUND_DATA);
    }


    @GetMapping("/throw-oauth-type-exception")
    public void test2() {
        throw new UnsupportedOAuthTypeException(ErrorCode.NOT_SUPPORTED_OAUTH_SERVICE);
    }


    @GetMapping("/throw-invalid-refresh-token-exception")
    public void test3() {
        throw new ForbiddenException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

    @GetMapping("/throw-internal-server-error-exception")
    public void test4() {
        throw new InternalServerErrorException(ErrorCode.UNHANDLED_EXCEPTION);
    }
}


