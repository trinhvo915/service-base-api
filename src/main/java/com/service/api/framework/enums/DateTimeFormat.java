package com.service.api.framework.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DateTimeFormat implements CodeEnum {
    YYYYMMDD("yyyyMMdd", ""),
    MM_DD_YYYY("MM/dd/yyyy", ""),
    SLASH_YYYYMMDD("yyyy/MM/dd", ""),
    YYYY_MM_DD("yyyy-MM-dd", ""),
    YYYYMMDDHHMMSS("yyyyMMddHHmmss", ""),
    SLASH_YYYY_MM_DD_HH_MM_SS("yyyy/MM/dd HH:mm:ss", ""),
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss.SSS", ""),
    YYYY_MM_DD_HH_MM_SS_FF("yyyy-MM-dd HH:mm:ss.SSS", ""),
    ISO_8601_BASIC_DATE_PATTERN("yyyy-MM-dd'T'HH:mm:ss'Z'", ""),
    ISO_8601_BASIC_DATE_PATTERN_WITH_MILLISECOND("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", ""),
    HHMM("HHmm", ""),
    HH_MM("HH:mm", ""),
    HH_MM_AM_PM("hh:mm a", ""),
    H_MM("H:mm", ""),
    YYYYMM("yyyyMM", ""),
    YYYYMMDDHHMM("yyyyMMddHHmm", "");

    private final String value;
    private final String name;
}
