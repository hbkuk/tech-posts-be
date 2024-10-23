package com.techbloghub.core.authentication.fixture;

import com.techbloghub.common.exception.BadRequestException;
import com.techbloghub.common.exception.common.ErrorCode;
import java.util.Arrays;
import java.util.List;

public enum NaverMemberFixture {
    
    나나("Authorization Code for Nana", "200", "Access Token for Nana"),
    줄리엣("Authorization Code for Juliet", "201", "Access Token for Juliet"),
    도레미("Authorization Code for Doremi", "202", "Access Token for Doremi"),
    파라솔("Authorization Code for Parasol", "203", "Access Token for Parasol");
    
    public final String 인가_코드;
    public final String 네이버_회원_번호;
    public final String 토큰;
    
    NaverMemberFixture(String 인가_코드, String 네이버_회원_번호, String 토큰) {
        this.인가_코드 = 인가_코드;
        this.네이버_회원_번호 = 네이버_회원_번호;
        this.토큰 = 토큰;
    }
    
    public static List<NaverMemberFixture> 모든_네이버_회원_가져오기() {
        return List.of(values());
    }
    
    public static String findTokenByCode(String 인가_코드) {
        return Arrays.stream(values())
            .filter(memberFixture -> memberFixture.인가_코드.equals(인가_코드))
            .findFirst()
            .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_DATA))
            .get토큰();
    }
    
    public static String findIdByToken(String 토큰) {
        return Arrays.stream(values())
            .filter(memberFixture -> memberFixture.토큰.equals(토큰))
            .findFirst()
            .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_FOUND_DATA))
            .get네이버_회원_번호();
    }
    
    public String get인가_코드() {
        return 인가_코드;
    }
    
    public String get네이버_회원_번호() {
        return 네이버_회원_번호;
    }
    
    public String get토큰() {
        return 토큰;
    }
}

