package com.techbloghub.common.alert.rss;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RssMessageGenerator {

    private static final String SUCCESS_TITLE = "[ SUCCESS ]\n";
    private static final String ERROR_TITLE = "[ ERROR ]\n";

    private static final String ERROR_TEMPLATE = "%s %s %s (line : %d)";

    public static final String TOTAL_TIMING_TEMPLATE = "Sync of All Blogs Duration: %d ms";
    public static final String TIMING_TEMPLATE = "Blog: %s | Duration: %d ms";

    /**
     * RSS 피드 읽기 성공 정보를 기반으로 메시지 생성
     * @param blogName 블로그 이름
     * @param duration 소요 시간
     * @return 생성된 성공 메시지
     */
    public static String generateSuccessMessage(String blogName, long duration) {
        return SUCCESS_TITLE + String.format(TIMING_TEMPLATE, blogName, duration);
    }

    /**
     * RSS 피드 읽기 실패 정보를 기반으로 메시지 생성
     * @param e 예외 정보
     * @return 생성된 실패 메시지
     */
    public static String generateErrorMessage(Exception e) {
        return ERROR_TITLE + String.format(ERROR_TEMPLATE, e.getClass().getName(),
            e.getStackTrace()[0].getMethodName(),
            e.getMessage(), e.getStackTrace()[0].getLineNumber());
    }

}
