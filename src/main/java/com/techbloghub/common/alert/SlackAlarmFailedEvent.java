package com.techbloghub.common.alert;

import java.util.List;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@ToString
public class SlackAlarmFailedEvent {

    private final String title;
    private final String titleLink;
    private final String email;
    private final List<String> contents;
}
