package com.techbloghub.common.util;

import com.techbloghub.common.util.converter.DateConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateConverterTest {

    @ParameterizedTest
    @CsvSource(value = {
            "Thu, 18 Jul 2019 18:00:00 +0900$2019-07-18T18:00:00",
            "Mon, 01 Jan 2020 00:00:00 +0900$2020-01-01T00:00:00",
            "Wed, 25 Dec 2019 12:30:00 +0900$2019-12-25T12:30:00",
            "Sun, 15 Mar 2020 23:45:00 +0900$2020-03-15T23:45:00",
            "Fri, 10 Apr 2020 09:15:00 +0900$2020-04-10T09:15:00"
    }, delimiter = '$')
    void RFC_822_타입을_ISO_8601_타입으로_변환(String RFC_822_타입의_날짜_문자열, String ISO_8601_타입의_날짜_문자열) throws ParseException {
        // when
        String ISO_8601_타입으로_변환된_날짜 = DateConverter.convertRfc822ToIso8601(RFC_822_타입의_날짜_문자열);

        // then
        assertEquals(ISO_8601_타입의_날짜_문자열, ISO_8601_타입으로_변환된_날짜);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Invalid Date", "Error", "null"})
    @DisplayName("날짜 데이터가 아닌 경우 파싱 예외가 발생한다.")
    void 날짜_데이터가_아닌_경우_파싱_예외_발생(String 날짜_데이터가_아닌_문자열) {

        // when, then
        assertThatExceptionOfType(ParseException.class)
                .isThrownBy(() -> {
                    DateConverter.convertRfc822ToIso8601(날짜_데이터가_아닌_문자열);
                });
    }
}
