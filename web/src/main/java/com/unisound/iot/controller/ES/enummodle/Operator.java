package com.unisound.iot.controller.ES.enummodle;

import com.unisound.iot.controller.ES.ValueType;
import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Operator {
    IS("is", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.COMMA_SPLIT, ValueType.ARRAY, ValueType.TIME_DATE}),
    NOT("not", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.COMMA_SPLIT, ValueType.ARRAY, ValueType.TIME_DATE}),
    PREFIX_CONTAINS("prefix_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),
    PREFIX_NOT_CONTAINS("prefix_not_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),
    SUFFIX_CONTAINS("suffix_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),
    SUFFIX_NOT_CONTAINS("suffix_not_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),
    CONTAINS("contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),
    NOT_CONTAINS("not_contains", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT}),
    CONTAINS_ANY("contains_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    NOT_CONTAINS_ANY("not_contains_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    IS_NULL("is_null", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.COMMA_SPLIT, ValueType.ARRAY, ValueType.TIME_DATE}),
    IS_NOT_NULL("is_not_null", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.TIME, ValueType.NUMERIC, ValueType.COMMA_SPLIT, ValueType.ARRAY, ValueType.TIME_DATE}),
    GREATER_THAN("greater_than", new ValueType[]{ValueType.TIME, ValueType.NUMERIC, ValueType.TEXT, ValueType.TIME_DATE}),
    GREATER_THAN_EQ("greater_than_eq", new ValueType[]{ValueType.TIME, ValueType.NUMERIC, ValueType.TEXT, ValueType.TIME_DATE}),
    LESS_THAN("less_than", new ValueType[]{ValueType.TIME, ValueType.NUMERIC, ValueType.TEXT, ValueType.TIME_DATE}),
    LESS_THAN_EQ("less_than_eq", new ValueType[]{ValueType.TIME, ValueType.NUMERIC, ValueType.TEXT, ValueType.TIME_DATE}),
    TODAY("today", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    YESTERDAY("yesterday", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    TOMORROW("tomorrow", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NEXT_SEVEN_DAY("next_seven_day", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LAST_SEVEN_DAY("last_seven_day", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LAST_THIRTY_DAY("last_thirty_day", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    BEFORE("before", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    AFTER("after", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    THIS_WEEK("this_week", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LAST_WEEK("last_week", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NEXT_WEEK("next_week", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    THIS_MONTH("this_month", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LAST_MONTH("last_month", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NEXT_MONTH("next_month", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    THIS_YEAR("this_year", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LAST_YEAR("last_year", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NEXT_YEAR("next_year", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    IS_ANY("is_any", new ValueType[]{ValueType.TEXT, ValueType.COMMA_SPLIT, ValueType.ARRAY, ValueType.NUMERIC}),
    NOT_ANY("not_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY, ValueType.NUMERIC}),
    PREFIX_CONTAINS_ANY("prefix_contains_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    PREFIX_NOT_CONTAINS_ANY("prefix_not_contains_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    SUFFIX_CONTAINS_ANY("suffix_contains_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    SUFFIX_NOT_CONTAINS_ANY("suffix_not_contains_any", new ValueType[]{ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    BETWEEN("between", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NOT_BETWEEN("not_between", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NOT_IN_DATE("not_in_date", new ValueType[]{ValueType.TIME}),
    CONTAINS_ALL("contains_all", new ValueType[0]),
    NOT_CONTAINS_ALL("not_contains_all", new ValueType[0]),
    GROUP_INNER_FIELD_CONTAINS_ANY("group_inner_field_contains_any", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.RICH_TEXT, ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE, ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    GROUP_INNER_FIELD_NOT_CONTAINS_ANY("group_inner_field_not_contains_any", new ValueType[]{ValueType.TEXT, ValueType.LONG_TEXT, ValueType.RICH_TEXT, ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE, ValueType.COMMA_SPLIT, ValueType.ARRAY}),
    FIELD_SERVICE_ASSIGN_CONTAINS_ANY("field_service_assign_contains_any", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_NOT_CONTAINS_ANY("field_service_assign_not_contains_any", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_CONTAINS_ALL("field_service_assign_contains_all", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_NOT_CONTAINS_ALL("field_service_assign_not_contains_all", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_IS("field_service_assign_is", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_NOT("field_service_assign_not", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_GREATER_THAN("field_service_assign_greater_than", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_GREATER_THAN_EQ("field_service_assign_greater_than_eq", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_LESS_THAN("field_service_assign_less_than", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_LESS_THAN_EQ("field_service_assign_less_than_eq", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_IS_ANY("field_service_assign_is_any", new ValueType[0]),
    FIELD_SERVICE_ASSIGN_NOT_ANY("field_service_assign_not_any", new ValueType[0]),
    BELONG_TO_WORK_TIME("belong_to_work_time", new ValueType[]{ValueType.TIME}),
    NOT_BELONG_TO_WORK_TIME("not_belong_to_work_time", new ValueType[]{ValueType.TIME}),
    BEFORE_HOUR_MINUTE("before_hour_minute", new ValueType[]{ValueType.TIME}),
    AFTER_HOUR_MINUTE("after_hour_minute", new ValueType[]{ValueType.TIME}),
    IS_FIELD("is_field", new ValueType[]{ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE}),
    NOT_FIELD("not_field", new ValueType[]{ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE}),
    GREATER_THAN_FIELD("greater_than_field", new ValueType[]{ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE}),
    GREATER_THAN_EQ_FIELD("greater_than_eq_field", new ValueType[]{ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE}),
    LESS_THAN_FIELD("less_than_field", new ValueType[]{ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE}),
    LESS_THAN_EQ_FIELD("less_than_eq_field", new ValueType[]{ValueType.NUMERIC, ValueType.TIME, ValueType.TIME_DATE}),
    LAST("last", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    NEXT("next", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    GREATER_THAN_CURRENT("greater_than_current", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LESS_THAN_CURRENT("less_than_current", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    EQ_CURRENT("eq_current", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    GREATER_THAN_EQ_CURRENT("greater_than_eq_current", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE}),
    LESS_THAN_EQ_CURRENT("less_than_eq_current", new ValueType[]{ValueType.TIME, ValueType.TIME_DATE});

    private final String value;
    private final ValueType[] valueTypes;

    private Operator(@NonNull String value, ValueType... valueTypes) {
        if (value == null) {
            throw new NullPointerException("value");
        } else {
            this.value = value;
            this.valueTypes = valueTypes;
        }
    }

    public static Operator getByValue(@NonNull String value) {
        if (value == null) {
            throw new NullPointerException("value");
        } else {
            return (Operator) Arrays.stream(values()).filter((i) -> {
                return i.value.equals(value);
            }).findFirst().orElseThrow(() -> {
                return new AssertionError("not support operator! ");
            });
        }
    }

    public static boolean isSupport(@NonNull ValueType valueType, @NonNull Operator operator) {
        if (valueType == null) {
            throw new NullPointerException("valueType");
        } else if (operator == null) {
            throw new NullPointerException("operator");
        } else {
            return Stream.of(values()).anyMatch((i) -> {
                return i.equals(operator) && Stream.of(i.getValueTypes()).anyMatch((j) -> {
                    return j.equals(valueType);
                });
            });
        }
    }

    public String getValue() {
        return this.value;
    }

    public ValueType[] getValueTypes() {
        return this.valueTypes;
    }
//    public String getSymbol() {
//        return this.symbol;
//    }
}
