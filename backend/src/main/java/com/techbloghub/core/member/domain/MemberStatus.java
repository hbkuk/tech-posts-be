package com.techbloghub.core.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    WITHDRAWN
}
