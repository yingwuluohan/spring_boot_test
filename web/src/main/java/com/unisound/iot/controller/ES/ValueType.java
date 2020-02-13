package com.unisound.iot.controller.ES;

import lombok.NonNull;

public enum ValueType {
    TEXT,
    LONG_TEXT,
    RICH_TEXT,
    NUMERIC,
    TIME,
    TIME_DATE,
    COMMA_SPLIT,
    ARRAY;

    private ValueType() {
    }

    public static boolean isText(@NonNull ValueType valueType) {
        if (valueType == null) {
            throw new NullPointerException("valueType");
        } else {
            return valueType.equals(TEXT) || valueType.equals(LONG_TEXT) || valueType.equals(RICH_TEXT);
        }
    }

    public static boolean isMultiValue(@NonNull ValueType valueType) {
        if (valueType == null) {
            throw new NullPointerException("valueType");
        } else {
            return valueType.equals(COMMA_SPLIT) || valueType.equals(ARRAY) || valueType.equals(TEXT);
        }
    }

    public static boolean isTimeDate(@NonNull ValueType valueType) {
        if (valueType == null) {
            throw new NullPointerException("valueType");
        } else {
            return valueType.equals(TIME_DATE);
        }
    }

    public static boolean isComma(@NonNull ValueType valueType) {
        if (valueType == null) {
            throw new NullPointerException("valueType");
        } else {
            return valueType.equals(COMMA_SPLIT);
        }
    }
}
