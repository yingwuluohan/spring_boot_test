package com.unisound.iot.controller.elasticSearchUtil.condition.type;

import lombok.NonNull;

/**
 * Created by fanfever on 2017/6/19.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
public enum ValueType {
    TEXT, LONG_TEXT, RICH_TEXT, NUMERIC, TIME, TIME_DATE, COMMA_SPLIT, ARRAY;

    public static boolean isText(@NonNull ValueType valueType){
        return valueType.equals(TEXT) || valueType.equals(LONG_TEXT) || valueType.equals(RICH_TEXT);
    }

    public static boolean isMultiValue(@NonNull ValueType valueType){
        return valueType.equals(COMMA_SPLIT) || valueType.equals(ARRAY) || valueType.equals(TEXT);
    }

    public static boolean isTimeDate(@NonNull ValueType valueType){
        return valueType.equals(TIME_DATE);
    }

    public static boolean isComma(@NonNull ValueType valueType){
        return valueType.equals(COMMA_SPLIT);
    }
}

