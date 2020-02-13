package com.unisound.iot.controller.ES.enummodle;

import lombok.Getter;

import java.util.stream.Stream;

public enum DateUnit {
    YEAR("year"), MONTH("month"), DAY("day"), HOUR("hour"), MINUTE("minute"), SECOND("second");

    private @Getter
    final String value;

    DateUnit(final String value) {
        this.value = value;
    }

    public static DateUnit getByValue(String value) {
        return Stream.of(DateUnit.values()).filter(e -> e.value.equals(value)).findAny().orElse(null);
    }
}
