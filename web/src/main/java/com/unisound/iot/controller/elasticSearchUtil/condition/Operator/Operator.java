package com.unisound.iot.controller.elasticSearchUtil.condition.Operator;

import com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType.*;


public enum Operator {
    /**
     * 等于
     */
    IS("is", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY, TIME_DATE),


    /**
     * 不等于
     */
    NOT("not", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY, TIME_DATE),

    /**
     * 开头等于
     */
    PREFIX_CONTAINS("prefix_contains", TEXT, LONG_TEXT),

    /**
     * 开头不等于
     */
    PREFIX_NOT_CONTAINS("prefix_not_contains", TEXT, LONG_TEXT),

    /**
     * 结尾等于
     */
    SUFFIX_CONTAINS("suffix_contains", TEXT, LONG_TEXT),

    /**
     * 结尾不等于
     */
    SUFFIX_NOT_CONTAINS("suffix_not_contains", TEXT, LONG_TEXT),

    /**
     * 包含
     */
    CONTAINS("contains", TEXT, LONG_TEXT),

    /**
     * 不包含
     */
    NOT_CONTAINS("not_contains", TEXT, LONG_TEXT),

    /**
     * 包含任意
     */
    CONTAINS_ANY("contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 不包含任意
     */
    NOT_CONTAINS_ANY("not_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 为空
     */
    IS_NULL("is_null", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY, TIME_DATE),

    /**
     * 不为空
     */
    IS_NOT_NULL("is_not_null", TEXT, LONG_TEXT, TIME, NUMERIC, COMMA_SPLIT, ARRAY, TIME_DATE),

    /**
     * 大于
     */
    GREATER_THAN("greater_than", TIME, NUMERIC,TEXT, TIME_DATE),

    /**
     * 大于等于
     */
    GREATER_THAN_EQ("greater_than_eq", TIME, NUMERIC,TEXT, TIME_DATE),

    /**
     * 小于
     */
    LESS_THAN("less_than", TIME, NUMERIC,TEXT, TIME_DATE),

    /**
     * 小于等于
     */
    LESS_THAN_EQ("less_than_eq", TIME, NUMERIC,TEXT, TIME_DATE),

    /**
     * 今天
     */
    TODAY("today", TIME, TIME_DATE),

    /**
     * 昨天
     */
    YESTERDAY("yesterday", TIME, TIME_DATE),

    /**
     * 明天
     */
    TOMORROW("tomorrow", TIME, TIME_DATE),

    /**
     * 今后7天
     */
    NEXT_SEVEN_DAY("next_seven_day", TIME, TIME_DATE),

    /**
     * 最近7天
     */
    LAST_SEVEN_DAY("last_seven_day", TIME, TIME_DATE),

    /**
     * 最近30天
     */
    LAST_THIRTY_DAY("last_thirty_day", TIME, TIME_DATE),

    /**
     * 早于（包含当天）
     */
    BEFORE("before", TIME, TIME_DATE),

    /**
     * 晚于（包含当天）
     */
    AFTER("after", TIME, TIME_DATE),

    /**
     * 本周
     */
    THIS_WEEK("this_week", TIME, TIME_DATE),

    /**
     * 上周
     */
    LAST_WEEK("last_week", TIME, TIME_DATE),

    /**
     * 下周
     */
    NEXT_WEEK("next_week", TIME, TIME_DATE),

    /**
     * 本月
     */
    THIS_MONTH("this_month", TIME, TIME_DATE),

    /**
     * 上月
     */
    LAST_MONTH("last_month", TIME, TIME_DATE),

    /**
     * 下月
     */
    NEXT_MONTH("next_month", TIME, TIME_DATE),

    /**
     * 今年
     */
    THIS_YEAR("this_year", TIME, TIME_DATE),

    /**
     * 去年
     */
    LAST_YEAR("last_year", TIME, TIME_DATE),

    /**
     * 明年
     */
    NEXT_YEAR("next_year", TIME, TIME_DATE),

    /**
     * 任意等于
     */
    IS_ANY("is_any", TEXT, COMMA_SPLIT, ARRAY, NUMERIC),

    /**
     * 任意不等于
     */
    NOT_ANY("not_any", COMMA_SPLIT, ARRAY, NUMERIC),

    /**
     * 任意开头等于
     */
    PREFIX_CONTAINS_ANY("prefix_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 任意开头不等于
     */
    PREFIX_NOT_CONTAINS_ANY("prefix_not_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 任意结尾等于
     */
    SUFFIX_CONTAINS_ANY("suffix_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 任意结尾不等于
     */
    SUFFIX_NOT_CONTAINS_ANY("suffix_not_contains_any", COMMA_SPLIT, ARRAY),

    /**
     * 介于
     */
    BETWEEN("between",TIME, TIME_DATE),

    /**
     * 不介于
     */
    NOT_BETWEEN("not_between",TIME, TIME_DATE),

    /**
     * 不在(日期时间）
     */
    NOT_IN_DATE("not_in_date",TIME),

    /**
     * 包含所有
     */
    CONTAINS_ALL("contains_all"),

    /**
     * 不包含所有
     */
    NOT_CONTAINS_ALL("not_contains_all"),

    /**
     * 任意包含，仅支持字段组组内字段
     */
    GROUP_INNER_FIELD_CONTAINS_ANY("group_inner_field_contains_any", TEXT, LONG_TEXT, RICH_TEXT, NUMERIC, TIME, TIME_DATE, COMMA_SPLIT, ARRAY),

    /**
     * 任意不包含，仅支持字段组组内字段
     */
    GROUP_INNER_FIELD_NOT_CONTAINS_ANY("group_inner_field_not_contains_any", TEXT, LONG_TEXT, RICH_TEXT, NUMERIC, TIME, TIME_DATE, COMMA_SPLIT, ARRAY),

    /**
     * 包含任意，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_CONTAINS_ANY("field_service_assign_contains_any"),

    /**
     * 不包含任意，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_NOT_CONTAINS_ANY("field_service_assign_not_contains_any"),

    /**
     * 包含全部，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_CONTAINS_ALL("field_service_assign_contains_all"),

    /**
     * 不包含全部，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_NOT_CONTAINS_ALL("field_service_assign_not_contains_all"),

    /**
     * 等于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_IS("field_service_assign_is"),

    /**
     * 不等于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_NOT("field_service_assign_not"),

    /**
     * 大于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_GREATER_THAN("field_service_assign_greater_than"),

    /**
     * 大于等于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_GREATER_THAN_EQ("field_service_assign_greater_than_eq"),

    /**
     * 小于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_LESS_THAN("field_service_assign_less_than"),

    /**
     * 小于等于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_LESS_THAN_EQ("field_service_assign_less_than_eq"),

    /**
     * 任意等于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_IS_ANY("field_service_assign_is_any"),
    /**
     * 任意不等于，仅支持现场服务任务分配
     */
    FIELD_SERVICE_ASSIGN_NOT_ANY("field_service_assign_not_any"),

    /**
     * 属于工作时间
     */
    BELONG_TO_WORK_TIME("belong_to_work_time", TIME),

    /**
     * 不属于工作时间
     */
    NOT_BELONG_TO_WORK_TIME("not_belong_to_work_time", TIME),

    /**
     * 早于时分
     */
    BEFORE_HOUR_MINUTE("before_hour_minute", TIME),

    /**
     * 晚于时分
     */
    AFTER_HOUR_MINUTE("after_hour_minute", TIME),

    /**
     * 等于(字段值)
     */
    IS_FIELD("is_field", NUMERIC, TIME, TIME_DATE),

    /**
     * 不等于(字段值)
     */
    NOT_FIELD("not_field", NUMERIC, TIME, TIME_DATE),

    /**
     * 大于(字段值)
     */
    GREATER_THAN_FIELD("greater_than_field", NUMERIC, TIME, TIME_DATE),

    /**
     * 大于等于(字段值)
     */
    GREATER_THAN_EQ_FIELD("greater_than_eq_field", NUMERIC, TIME, TIME_DATE),

    /**
     * 小于(字段值)
     */
    LESS_THAN_FIELD("less_than_field", NUMERIC, TIME, TIME_DATE),

    /**
     * 小于等于(字段值)
     */
    LESS_THAN_EQ_FIELD("less_than_eq_field", NUMERIC, TIME, TIME_DATE),

    /**
     * 最近n年/月/日/时/分/秒
     */
    LAST("last", TIME, TIME_DATE),

    /**
     * 今后n年/月/日/时/分/秒
     */
    NEXT("next", TIME, TIME_DATE),

    /**
     * 大于当前
     */
    GREATER_THAN_CURRENT("greater_than_current",TIME, TIME_DATE),

    /**
     * 小于当前
     */
    LESS_THAN_CURRENT("less_than_current",TIME, TIME_DATE),

    /**
     * 等于当前
     */
    EQ_CURRENT("eq_current",TIME, TIME_DATE),

    /**
     * 大于等于当前
     */
    GREATER_THAN_EQ_CURRENT("greater_than_eq_current",TIME, TIME_DATE),

    /**
     * 小于等于当前
     */
    LESS_THAN_EQ_CURRENT("less_than_eq_current",TIME, TIME_DATE);


    @Getter
    private final String value;

    @Getter
    private final ValueType[] valueTypes;

    Operator(@NonNull final String value, final ValueType... valueTypes) {
        this.value = value;
        this.valueTypes = valueTypes;
    }

    public static Operator getByValue(@NonNull final String value) {
        return Arrays.stream(Operator.values()).filter(i -> i.value.equals(value)).findFirst().orElseThrow(() -> new AssertionError("not support operator! "));
    }

    public static boolean isSupport(@NonNull ValueType valueType, @NonNull Operator operator) {
        return Stream.of(Operator.values()).anyMatch(i -> i.equals(operator) && Stream.of(i.getValueTypes()).anyMatch(j -> j.equals(valueType)));
    }
}
