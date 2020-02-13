package com.unisound.iot.controller.elasticSearchUtil.condition.wrapperHandler;

import com.google.common.collect.Lists;
import com.unisound.iot.controller.ES.DateUtils;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.BaseConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.SimpleConditionRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType.*;


public interface SimpleConditionWrapperHandle extends ConditionWrapperHandle {

    static Object getValue(BaseConditionRequest condition) {
        Object value = ((SimpleConditionRequest) condition).getWaitCompareValue();
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).stripTrailingZeros();
        }
        return value;
    }

    static ConditionWrapperHandle iSHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof BigDecimal) {
                return 0 == ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString()));
            } else if (value instanceof Integer) {
                return ((Integer)value).intValue() == ((Integer)condition.getValue()).intValue();
            } else if (value instanceof Long) {
                return ((Long)value).intValue() == ((Long)condition.getValue()).intValue();
            } else if (value instanceof LocalDateTime) {
                return ((LocalDateTime) value).isEqual((LocalDateTime) condition.getValue());
            } else {
                return condition.getValue().equals(value);
            }
        };
    }

    static ConditionWrapperHandle notHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof BigDecimal) {
                return 0 != ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString()));
            } else if (value instanceof LocalDateTime) {
                return !((LocalDateTime) value).isEqual((LocalDateTime) condition.getValue());
            } else {
                return !condition.getValue().equals(value);
            }
        };
    }


    static ConditionWrapperHandle prefixContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return String.valueOf(value).startsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return !String.valueOf(value).startsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return String.valueOf(value).endsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                Object value = getValue(condition);
                if (value instanceof String) {
                    return !String.valueOf(value).endsWith(condition.getValueStr());
                } else {
                    return notFoundOperation();
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (isText(condition.getValueType()) && value instanceof String) {
                return String.valueOf(value).contains(condition.getValueStr());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (isText(condition.getValueType()) && value instanceof String) {
                return !String.valueOf(value).contains(condition.getValueStr());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return condition -> {
            List value = Lists.newArrayList((String.valueOf(getValue(condition))).split(","));
            for (int i = 0; i < condition.getValueArray().size(); i++) {
                if (value.contains(condition.getValueArray().get(i))) {
                    return true;
                }
            }
            return false;
        };
    }

    static ConditionWrapperHandle notAnyHandle() {
        return condition -> {
            List value = Lists.newArrayList((String.valueOf(getValue(condition))).split(","));
            for (int i = 0; i < condition.getValueArray().size(); i++) {
                if (value.contains(condition.getValueArray().get(i))) {
                    return false;
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle containsAnyHandle() {
        return condition -> {
            Object value = getValue(condition);
            for (int i = 0; i < condition.getValueArray().size(); i++) {
                if (String.valueOf(value).contains(condition.getValueArray().get(i))) {
                    return true;
                }
            }
            return false;
        };
    }


    static ConditionWrapperHandle notContainsAnyHandle() {
        return condition -> {
            Object value = getValue(condition);
            for (int i = 0; i < condition.getValueArray().size(); i++) {
                if (String.valueOf(value).contains(condition.getValueArray().get(i))) {
                    return false;
                }
            }
            return true;
        };
    }


    static ConditionWrapperHandle prefixContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (Object aValue : value) {
                    if (String.valueOf(aValue).startsWith(condition.getValueStr())) {
                        return true;
                    }
                }
                return false;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (Object aValue : value) {
                    if (String.valueOf(aValue).startsWith(condition.getValueStr())) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (Object aValue : value) {
                    if (String.valueOf(aValue).endsWith(condition.getValueStr())) {
                        return true;
                    }
                }
                return false;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                List value = Lists.newArrayList(((String) getValue(condition)).split(","));
                for (Object aValue : value) {
                    if (String.valueOf(aValue).endsWith(condition.getValueStr())) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isNullHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof String) {
                return StringUtils.isBlank(String.valueOf(value));
            } else if (value instanceof List) {
                return CollectionUtils.isEmpty((List) value);
            } else {
                return null == value;
            }
        };
    }

    static ConditionWrapperHandle isNotNullHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (value instanceof String) {
                return !StringUtils.isBlank(String.valueOf(value));
            } else if (value instanceof List) {
                return CollectionUtils.isNotEmpty((List) value);
            } else {
                return null != value;
            }
        };
    }

    static ConditionWrapperHandle greaterThanHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 < ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 < ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TEXT)) {
                return 0 < String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 <= ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 <= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TEXT)) {
                return 0 <= String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 > ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 > ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TEXT)) {
                return 0 > String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return condition -> {
            Object value = getValue(condition);
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 >= ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 >= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TEXT)) {
                return 0 >= String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().plusDays(1L).isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().minusDays(1L).isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate date = ((LocalDateTime) getValue(condition)).toLocalDate();
                return date.isAfter(LocalDate.now()) && date.isBefore(LocalDate.now().plusDays(8L));
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate date = ((LocalDateTime) getValue(condition)).toLocalDate();
                return date.isAfter(LocalDate.now().minusDays(8L)) && date.isBefore(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isBefore(LocalDate.now()) || ((LocalDateTime) getValue(condition)).toLocalDate().isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return ((LocalDateTime) getValue(condition)).toLocalDate().isAfter(LocalDate.now()) || ((LocalDateTime) getValue(condition)).toLocalDate().isEqual(LocalDate.now());
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 7).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().minusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusWeeks(1L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 7).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 7).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().minusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusMonths(1L).withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().plusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusMonths(1L).withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().minusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusYears(1L).withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDate value = ((LocalDateTime) getValue(condition)).toLocalDate();
                return LocalDate.now().plusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusYears(1L).withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle betweenHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME) && condition.getValueArray().size() == 2) {
                LocalDateTime value = ((LocalDateTime) getValue(condition));
                LocalDateTime beforeTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(0));
                LocalDateTime afterTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(1));

                return beforeTime.compareTo(value) <= 0 && afterTime.compareTo(value) >= 0;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notBetweenHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME) && condition.getValueArray().size() == 2) {
                LocalDateTime value = ((LocalDateTime) getValue(condition));
                LocalDateTime beforeTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(0));
                LocalDateTime afterTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(1));

                return beforeTime.compareTo(value) > 0 || afterTime.compareTo(value) < 0;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notInDateHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                LocalDateTime value = ((LocalDateTime) getValue(condition));

                return !value.toLocalDate().equals(DateUtils.parseDateStr(condition.getValueStr()));
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAllHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                List<String> dataValue = Lists.newArrayList(((String) getValue(condition)).split(","));
                List<String> valueList = condition.getValueArray();
                for (String aValue : valueList) {
                    if (!dataValue.contains(aValue)) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsAllHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                List<String> dataValue = Lists.newArrayList(((String) getValue(condition)).split(","));
                List<String> valueList = condition.getValueArray();
                for (String aValue : valueList) {
                    if (dataValue.contains(aValue)) {
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }
}
