package com.techbloghub.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateConverter {
    
    private static final String[] DATE_FORMATS = {
        "EEE, dd MMM yyyy HH:mm:ss Z",     // RFC 822
        "yyyy-MM-dd'T'HH:mm:ss",           // ISO 8601
    };
    
    public static String convertToIso8601(String dateStr) throws ParseException {
        for (String format : DATE_FORMATS) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            try {
                Date date = dateFormat.parse(dateStr);
                SimpleDateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                
                return iso8601DateFormat.format(date);
            } catch (ParseException e) {
                // 현재 포맷으로 파싱 실패 시, 다음 포맷으로 시도
                log.debug("주어진 날짜 데이터인 `{}`가 변환하려는 날짜 데이터 형식인 `{}`과 다릅니다.", dateStr, format);
            }
        }
        throw new ParseException("변환할 수 없는 데이터 형식입니다. : \"" + dateStr + "\"", 0);
    }
}
