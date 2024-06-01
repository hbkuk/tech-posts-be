package com.techbloghub.common.util.converter;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateConverter {

    public static String convertRfc822ToIso8601(String rfc822Date) throws ParseException {
        SimpleDateFormat rfc822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        SimpleDateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date parseDate = rfc822DateFormat.parse(rfc822Date);
        return iso8601DateFormat.format(parseDate);
    }
}
