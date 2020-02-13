package com.unisound.iot.controller.elasticSearchUtil.condition.wrapperHandler;

import com.google.common.collect.Lists;
import com.unisound.iot.common.util.ReflectionUtils;
import com.unisound.iot.controller.ES.DateUtils;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.BaseConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.MemoryConditionRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

import static com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType.*;


public interface JavaBeanConditionWrapperHandle extends ConditionWrapperHandle {

    static Object getValue(BaseConditionRequest condition) {
        Object value = null;
        if(((MemoryConditionRequest) condition).getIsMap()!=null && ((MemoryConditionRequest) condition).getIsMap()){
            value = ((Map<String, Object>)((MemoryConditionRequest) condition).getMemoryObject()).get(condition.getFieldName());
            if(condition.getValueType().equals(NUMERIC) && value!=null && value instanceof String && !value.toString().equals("")){
                value = Integer.valueOf(value.toString());
            }
            if(condition.getValueType().equals(NUMERIC) && value==null){
                value = 0;
            }
        }else{
            value = ReflectionUtils.invokeGetter(((MemoryConditionRequest) condition).getMemoryObject(), condition.getFieldName());
            if(condition.getValueType().equals(NUMERIC)){
                if(value instanceof Double){
                    value = BigDecimal.valueOf((Double) value);
                }else if(value instanceof Float){
                    value = new BigDecimal(String.valueOf(value));
                }
            }
        }
        if(value instanceof BigDecimal){
            return ((BigDecimal) value).stripTrailingZeros();
        }
        return value;
    }

    /**
     * 获取字段组组内字段值
     * @param condition
     * @param dataGroup
     * @return
     */
    static Object getDataGroupValue(BaseConditionRequest condition, Object dataGroup) {
        Object value = null;
        if(condition.getValueType().equals(RICH_TEXT) || condition.getValueType().equals(LONG_TEXT)) {
            Object clobValue = ReflectionUtils.invokeGetter(dataGroup, condition.getFieldName());
            value = ReflectionUtils.invokeGetter(clobValue, "content");
        }else {
            value = ReflectionUtils.invokeGetter(dataGroup, condition.getFieldName());
        }
        if(value == null){
            return value;
        }else if(value instanceof BigDecimal){
            return ((BigDecimal) value).stripTrailingZeros();
        }
        return value;
    }

    /**
     * 获取现场服务任务分配条件值
     * @param condition
     * @return
     */
    static Object getFieldServiceAssignValue(BaseConditionRequest condition) {
        Object dataValue = ((MemoryConditionRequest) condition).getMemoryObject();
        if(dataValue == null) {
            return dataValue;
        }
        return ReflectionUtils.invokeGetter(dataValue, condition.getFieldName());
    }

    static List<String> getValueArray(Object value) {
        if (null == value) {
            return Collections.emptyList();
        }
        if (value instanceof List) {
            return ((List<?>) value).stream().map(String::valueOf).collect(Collectors.toList());
        }
        if (value instanceof Set) {
            return ((Set<?>) value).stream().map(String::valueOf).collect(Collectors.toList());
        }
        if (value instanceof String) {
            return Lists.newArrayList(String.valueOf(value).split(","));
        }
        throw new AssertionError();
    }

    static String getStrByValue(Object value){
        if(value instanceof BigDecimal){
            return ((BigDecimal)value).toPlainString();
        }else if(value instanceof Collection){
            return String.valueOf(((List)value).stream().map(Object::toString).collect(Collectors.joining(",")));
        }else{
            return String.valueOf(value);
        }
    }

    static boolean getIsHandle(BaseConditionRequest condition, Object value){
        if(value==null){
            return false;
        }
        if (value instanceof BigDecimal) {
            return 0 == ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString()));
        } else if (value instanceof Integer) {
            return ((Integer) value).intValue() == Integer.valueOf(condition.getValue().toString()).intValue();
        } else if (value instanceof LocalDateTime) {
            if(condition.getValueType().equals(TIME_DATE)){
                if(condition.getValue() instanceof String){
                    return ((LocalDateTime) value).toLocalDate().isEqual(DateUtils.parseDateStr(condition.getValue().toString()));
                }
            }
            return ((LocalDateTime) value).isEqual(((LocalDateTime) condition.getValue()));
        } else if (value instanceof LocalDate) {
            if(condition.getValue() instanceof String){
                return ((LocalDate) value).isEqual(DateUtils.parseDateStr(condition.getValue().toString()));
            }
            return ((LocalDate) value).isEqual((LocalDate) condition.getValue());
        } else {
            return condition.getValue().equals(value);
        }
    }

    static boolean getNotHandle(BaseConditionRequest condition, Object value){
        if(value==null){
            return true;
        }
        if (value instanceof BigDecimal) {
            return 0 != ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString()));
        } else if (value instanceof Integer) {
            return ((Integer) value).intValue() != Integer.valueOf(condition.getValue().toString()).intValue();
        } else if (value instanceof LocalDateTime) {
            if(condition.getValueType().equals(TIME_DATE)){
                if(condition.getValue() instanceof String){

                    return !((LocalDateTime) value).toLocalDate().isEqual(DateUtils.parseDateStr(condition.getValue().toString()));
                }
            }
            return !((LocalDateTime) value).isEqual(((LocalDateTime) condition.getValue()));
        } else if (value instanceof LocalDate) {
            if(condition.getValue() instanceof String){
                return !((LocalDate) value).isEqual(DateUtils.parseDateStr(condition.getValue().toString()));
            }
            return !((LocalDate) value).isEqual((LocalDate) condition.getValue());
        } else {
            return !condition.getValue().equals(value);
        }
    }

    static ConditionWrapperHandle iSHandle() {
        return condition -> {
            Object value = getValue(condition);
            return getIsHandle(condition, value);
        };
    }

    static ConditionWrapperHandle notHandle() {
        return condition -> {
            Object value = getValue(condition);
            return getNotHandle(condition, value);
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
            if (isText(condition.getValueType()) && value == null) {
                return false;
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
            if (isText(condition.getValueType()) && value == null) {
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return condition -> {
            List value = Lists.newArrayList((getStrByValue(getValue(condition))).split(","));
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
            List value = Lists.newArrayList((getStrByValue(getValue(condition))).split(","));
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
                if (getStrByValue(value).contains(condition.getValueArray().get(i))) {
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
                if (getStrByValue(value).contains(condition.getValueArray().get(i))) {
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
                if(value != null && condition.getValueType().equals(NUMERIC) && value.equals(0) && ((MemoryConditionRequest) condition).getIsMap()!=null && ((MemoryConditionRequest) condition).getIsMap()){
                    return true;
                }
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
            if(value==null){
                return false;
            }
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 < ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (value instanceof Integer) {
                return (Integer) value > Integer.valueOf(condition.getValue().toString());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 < ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDate) {
                if(condition.getValue() instanceof String){
                    return 0 < ((LocalDate) value).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
                }else {
                    return 0 < ((LocalDate) value).compareTo((LocalDate) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME) && value instanceof String) {
                return 0 < (DateUtils.parseDateStr(value.toString())).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof LocalDateTime) {
                if(condition.getValue() instanceof String){
                    return 0 < ((LocalDateTime) value).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString()));
                }else {
                    return 0 < ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof String) {
                return 0 < (DateUtils.parseDateTimeStr((value.toString())).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString())));
            } else if (condition.getValueType().equals(TEXT)){
                return 0 < String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return condition -> {
            Object value = getValue(condition);
            if(value==null){
                return false;
            }
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 <= ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (value instanceof Integer) {
                return (Integer) value >= Integer.valueOf(condition.getValue().toString());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 <= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDate) {
                if(condition.getValue() instanceof String){
                    return 0 <= ((LocalDate) value).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
                }else {
                    return 0 <= ((LocalDate) value).compareTo((LocalDate) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME) && value instanceof String) {
                return 0 <= (DateUtils.parseDateStr(value.toString())).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof LocalDateTime) {
                if(condition.getValue() instanceof String){
                    return 0 <= ((LocalDateTime) value).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString()));
                }else {
                    return 0 <= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof String) {
                return 0 <= (DateUtils.parseDateTimeStr((value.toString())).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString())));
            } else if (condition.getValueType().equals(TEXT)){
                return 0 <= String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return condition -> {
            Object value = getValue(condition);
            if(value==null){
                return false;
            }
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 > ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (value instanceof Integer) {
                return (Integer) value < Integer.valueOf(condition.getValue().toString());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 > ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDate) {
                if(condition.getValue() instanceof String){
                    return 0 > ((LocalDate) value).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
                }else {
                    return 0 > ((LocalDate) value).compareTo((LocalDate) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME) && value instanceof String) {
                return 0 > (DateUtils.parseDateStr(value.toString())).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof LocalDateTime) {
                if(condition.getValue() instanceof String){
                    return 0 > ((LocalDateTime) value).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString()));
                }else {
                    return 0 > ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof String) {
                return 0 > (DateUtils.parseDateTimeStr((value.toString())).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString())));
            } else if (condition.getValueType().equals(TEXT)){
                return 0 > String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return condition -> {
            Object value = getValue(condition);
            if(value==null){
                return false;
            }
            if (condition.getValueType().equals(NUMERIC) && value instanceof BigDecimal) {
                return 0 >= ((BigDecimal) value).compareTo(new BigDecimal(condition.getValueStr()));
            } else if (value instanceof Integer) {
                return (Integer) value <= Integer.valueOf(condition.getValue().toString());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                return 0 >= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
            } else if (condition.getValueType().equals(TIME) && value instanceof LocalDate) {
                if(condition.getValue() instanceof String){
                    return 0 >= ((LocalDate) value).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
                }else {
                    return 0 >= ((LocalDate) value).compareTo((LocalDate) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME) && value instanceof String) {
                return 0 >= (DateUtils.parseDateStr(value.toString())).compareTo(DateUtils.parseDateStr(condition.getValue().toString()));
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof LocalDateTime) {
                if(condition.getValue() instanceof String){
                    return 0 >= ((LocalDateTime) value).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString()));
                }else {
                    return 0 >= ((LocalDateTime) value).compareTo((LocalDateTime) condition.getValue());
                }
            } else if (condition.getValueType().equals(TIME_DATE) && value instanceof String) {
                return 0 >= (DateUtils.parseDateTimeStr((value.toString())).compareTo(DateUtils.parseDateTimeStr(condition.getValue().toString())));
            } else if (condition.getValueType().equals(TEXT)){
                return 0 >= String.valueOf(value).compareTo(String.valueOf(condition.getValue()));
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    return ((LocalDate) obj).isEqual(LocalDate.now());
                }else {
                    return ((LocalDateTime) obj).toLocalDate().isEqual(LocalDate.now());
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    return ((LocalDate) obj).plusDays(1L).isEqual(LocalDate.now());
                }else {
                    return ((LocalDateTime) obj).toLocalDate().plusDays(1L).isEqual(LocalDate.now());
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    return ((LocalDate) obj).minusDays(1L).isEqual(LocalDate.now());
                }else {
                    return ((LocalDateTime) obj).toLocalDate().minusDays(1L).isEqual(LocalDate.now());
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME)) {
                if(obj instanceof LocalDate){
                    LocalDate date = (LocalDate) obj;
                    return date.isAfter(LocalDate.now().minusDays(1)) && date.isBefore(LocalDate.now().plusDays(8L));
                }else {
                    LocalDate date = ((LocalDateTime) obj).toLocalDate();
                    return date.isAfter(LocalDate.now().minusDays(1)) && date.isBefore(LocalDate.now().plusDays(8L));
                }
            }
            if (condition.getValueType().equals(TIME_DATE)) {
                LocalDate date = ((LocalDateTime) obj).toLocalDate();
                return date.isAfter(LocalDate.now().minusDays(1)) && date.isBefore(LocalDate.now().plusDays(8L));
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate date = (LocalDate) obj;
                    return date.isAfter(LocalDate.now().minusDays(8L)) && date.isBefore(LocalDate.now());
                }else {
                    LocalDate date = ((LocalDateTime) obj).toLocalDate();
                    return date.isAfter(LocalDate.now().minusDays(8L)) && date.isBefore(LocalDate.now());
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    return ((LocalDate) obj).isBefore(LocalDate.now()) || ((LocalDate) obj).isEqual(LocalDate.now());
                }else {
                    return ((LocalDateTime) obj).toLocalDate().isBefore(LocalDate.now()) || ((LocalDateTime) obj).toLocalDate().isEqual(LocalDate.now());
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    return ((LocalDate) obj).isAfter(LocalDate.now()) || ((LocalDate) obj).isEqual(LocalDate.now());
                }else {
                    return ((LocalDateTime) obj).toLocalDate().isAfter(LocalDate.now()) || ((LocalDateTime) obj).toLocalDate().isEqual(LocalDate.now());
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 2).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 2).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 2).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 2).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().minusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().minusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().minusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().minusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusWeeks(2L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusWeeks(2L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusWeeks(2L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().plusWeeks(1L).with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 2).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusWeeks(2L).with(WeekFields.of(Locale.CHINA).dayOfWeek(), 1).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().withDayOfMonth(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().withDayOfMonth(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().minusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusMonths(1L).withDayOfMonth(LocalDate.now().minusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().minusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().minusMonths(1L).withDayOfMonth(LocalDate.now().minusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().minusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusMonths(1L).withDayOfMonth(LocalDate.now().minusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().minusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().minusMonths(1L).withDayOfMonth(LocalDate.now().minusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().plusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusMonths(1L).withDayOfMonth(LocalDate.now().plusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().plusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusMonths(1L).withDayOfMonth(LocalDate.now().plusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().plusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusMonths(1L).withDayOfMonth(LocalDate.now().plusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().plusMonths(1L).withDayOfMonth(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusMonths(1L).withDayOfMonth(LocalDate.now().plusMonths(1L).lengthOfMonth()).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().withDayOfYear(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().withDayOfYear(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().withDayOfYear(LocalDate.now().lengthOfYear()).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().minusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusYears(1L).withDayOfYear(LocalDate.now().minusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().minusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().minusYears(1L).withDayOfYear(LocalDate.now().minusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().minusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().minusYears(1L).withDayOfYear(LocalDate.now().minusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().minusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().minusYears(1L).withDayOfYear(LocalDate.now().minusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null){
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    return (LocalDate.now().plusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusYears(1L).withDayOfYear(LocalDate.now().plusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().plusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusYears(1L).withDayOfYear(LocalDate.now().plusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isEqual(value);
                }else {
                    LocalDate value = ((LocalDateTime) obj).toLocalDate();
                    return (LocalDate.now().plusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isBefore(value) && LocalDate.now().plusYears(1L).withDayOfYear(LocalDate.now().plusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isAfter(value))
                            || LocalDate.now().plusYears(1L).withDayOfYear(1).atStartOfDay().toLocalDate().isEqual(value) || LocalDate.now().plusYears(1L).withDayOfYear(LocalDate.now().plusYears(1L).lengthOfYear()).atStartOfDay().toLocalDate().isEqual(value);
                }
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle betweenHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null) {
                return false;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if ((condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) && condition.getValueArray().size() == 2) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    LocalDate beforeTime = DateUtils.parseDateStr(condition.getValueArray().get(0));
                    LocalDate afterTime = DateUtils.parseDateStr(condition.getValueArray().get(1));

                    return beforeTime.compareTo(value) <= 0 && afterTime.compareTo(value) >= 0;
                }else {
                    LocalDateTime value = (LocalDateTime) obj;
                    LocalDateTime beforeTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(0));
                    LocalDateTime afterTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(1));

                    return beforeTime.compareTo(value) <= 0 && afterTime.compareTo(value) >= 0;
                }
            }
            if (condition.getValueType().equals(NUMERIC)) {
                Integer value = ((Integer) getValue(condition));
                Integer min = Integer.valueOf(condition.getValueArray().get(0));
                Integer max = Integer.valueOf(condition.getValueArray().get(1));

                return min < value && value < max;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notBetweenHandle() {
        return condition -> {
            Object obj = getValue(condition);
            if(obj==null) {
                return true;
            }
            if (condition.getValueType().equals(TIME) && obj instanceof String) {
                obj = DateUtils.parseDateStr(obj.toString());
            }
            if (condition.getValueType().equals(TIME_DATE) && obj instanceof String) {
                obj = DateUtils.parseDateTimeStr(obj.toString());
            }
            if ((condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) && condition.getValueArray().size() == 2) {
                if(obj instanceof LocalDate){
                    LocalDate value = (LocalDate) obj;
                    LocalDate beforeTime = DateUtils.parseDateStr(condition.getValueArray().get(0));
                    LocalDate afterTime = DateUtils.parseDateStr(condition.getValueArray().get(1));

                    return beforeTime.compareTo(value) > 0 || afterTime.compareTo(value) < 0;
                }else {
                    LocalDateTime value = (LocalDateTime) obj;
                    LocalDateTime beforeTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(0));
                    LocalDateTime afterTime = DateUtils.parseDateTimeStr(condition.getValueArray().get(1));

                    return beforeTime.compareTo(value) > 0 || afterTime.compareTo(value) < 0;
                }
            }
            if (condition.getValueType().equals(NUMERIC)) {
                Integer value = ((Integer) getValue(condition));
                Integer min = Integer.valueOf(condition.getValueArray().get(0));
                Integer max = Integer.valueOf(condition.getValueArray().get(1));

                return min > value || value > max;
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
                List<String> dataValue = Lists.newArrayList((getStrByValue(getValue(condition))).split(","));
                List<String> valueList = condition.getValueArray();
                for (String aValue : valueList) {
                    if(!dataValue.contains(aValue)){
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
                List<String> dataValue = Lists.newArrayList((getStrByValue(getValue(condition))).split(","));
                List<String> valueList = condition.getValueArray();
                for (String aValue : valueList) {
                    if(dataValue.contains(aValue)){
                        return false;
                    }
                }
                return true;
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle groupInnerFieldContainsAnyHandle() {
        return condition -> {
            MemoryConditionRequest memoryConditionRequest = (MemoryConditionRequest) condition;
            List<Object> dataGroupList = null;
            if(memoryConditionRequest.getMemoryObject() == null)
                return false;
            if(memoryConditionRequest.getMemoryObject() instanceof List)
                dataGroupList = (List<Object>) memoryConditionRequest.getMemoryObject();
            if(CollectionUtils.isEmpty(dataGroupList))
                return false;

            for (Object dataGroup : dataGroupList) {
                Object value = getDataGroupValue(condition, dataGroup);
                if(value == null){
                    continue;
                }
                if (isMultiValue(condition.getValueType())) {
                    List<String> dataValue = Lists.newArrayList((getStrByValue(value)).split(","));
                    List<String> valueList = condition.getValueArray();
                    for (String aValue : valueList) {
                        if(dataValue.contains(aValue)){
                            return true;
                        }
                    }
                }else {
                    if (value instanceof BigDecimal) {
                        if(0 == ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString())))
                            return true;
                    } else if (value instanceof Integer) {
                        if(((Integer) value).intValue() == Integer.valueOf(condition.getValue().toString()).intValue())
                            return true;
                    } else if (value instanceof LocalDateTime) {
                        if(condition.getValueType().equals(TIME_DATE)){
                            if(condition.getValue() instanceof String){
                                if(((LocalDateTime) value).toLocalDate().isEqual(DateUtils.parseDateStr(condition.getValue().toString())))
                                    return true;
                            }
                        }
                        if(((LocalDateTime) value).isEqual(((LocalDateTime) condition.getValue())))
                            return true;
                    } else if (value instanceof LocalDate) {
                        if(condition.getValue() instanceof String){
                            if(((LocalDate) value).isEqual(DateUtils.parseDateStr(condition.getValue().toString())))
                                return true;
                        }
                        if(((LocalDate) value).isEqual((LocalDate) condition.getValue()))
                            return true;
                    } else {
                        if(condition.getValue().equals(value))
                            return true;
                    }
                }
            }

            return false;
        };
    }

    static ConditionWrapperHandle groupInnerFieldNotContainsAnyHandle() {
        return condition -> {
            MemoryConditionRequest memoryConditionRequest = (MemoryConditionRequest) condition;
            List<Object> dataGroupList = null;
            if(memoryConditionRequest.getMemoryObject() == null)
                return true;
            if(memoryConditionRequest.getMemoryObject() instanceof List)
                dataGroupList = (List<Object>) memoryConditionRequest.getMemoryObject();
            if(CollectionUtils.isEmpty(dataGroupList))
                return true;

            for (Object dataGroup : dataGroupList) {
                Object value = getDataGroupValue(condition, dataGroup);
                if(value == null){
                    return true;
                }
                if (isMultiValue(condition.getValueType())) {
                    List<String> dataValue = Lists.newArrayList((getStrByValue(value)).split(","));
                    List<String> valueList = condition.getValueArray();
                    for (String aValue : valueList) {
                        if(dataValue.contains(aValue)){
                            return false;
                        }
                    }
                }else {
                    if (value instanceof BigDecimal) {
                        if(0 == ((BigDecimal) value).compareTo(new BigDecimal(condition.getValue().toString())))
                            return false;
                    } else if (value instanceof Integer) {
                        if(((Integer) value).intValue() == Integer.valueOf(condition.getValue().toString()).intValue())
                            return false;
                    } else if (value instanceof LocalDateTime) {
                        if(condition.getValueType().equals(TIME_DATE)){
                            if(condition.getValue() instanceof String){
                                if(((LocalDateTime) value).toLocalDate().isEqual(DateUtils.parseDateStr(condition.getValue().toString())))
                                    return false;
                            }
                        }
                        if(((LocalDateTime) value).isEqual(((LocalDateTime) condition.getValue())))
                            return false;
                    } else if (value instanceof LocalDate) {
                        if(condition.getValue() instanceof String){
                            if(((LocalDate) value).isEqual(DateUtils.parseDateStr(condition.getValue().toString())))
                                return false;
                        }
                        if(((LocalDate) value).isEqual((LocalDate) condition.getValue()))
                            return false;
                    } else {
                        if(condition.getValue().equals(value))
                            return false;
                    }
                }
            }

            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignContainsAnyHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            List<String> dataValue = getValueArray(value);
            List<String> valueList = condition.getValueArray();
            if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isNotEmpty(valueList)) {
                return false;
            }else if(CollectionUtils.isNotEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }
            for (String aValue : valueList) {
                if (dataValue.contains(aValue)) {
                    return true;
                }
            }
            return false;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignNotContainsAnyHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            List<String> dataValue = getValueArray(value);
            List<String> valueList = condition.getValueArray();
            if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isNotEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isNotEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }
            for (String aValue : valueList) {
                if (dataValue.contains(aValue)) {
                    return false;
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignContainsAllHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            List<String> dataValue = getValueArray(value);
            List<String> valueList = condition.getValueArray();
            if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isNotEmpty(valueList)) {
                return false;
            }else if(CollectionUtils.isNotEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }
            for (String aValue : valueList) {
                if (!dataValue.contains(aValue)) {
                    return false;
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignNotContainsAllHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            List<String> dataValue = getValueArray(value);
            List<String> valueList = condition.getValueArray();
            if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isNotEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isNotEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }
            for (String aValue : valueList) {
                if (dataValue.contains(aValue)) {
                    return false;
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignIsHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            if(value == null || condition.getValue() == null) {
                return false;
            }
            return getIsHandle(condition, value);
        };
    }

    static ConditionWrapperHandle fieldServiceAssignNotHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            if(value == null || condition.getValue() == null) {
                return false;
            }else if(value == null || condition.getValue() != null) {
                return true;
            }else if(value != null || condition.getValue() == null) {
                return true;
            }
            return getNotHandle(condition, value);
        };
    }

    static ConditionWrapperHandle fieldServiceAssignGreaterThanHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            if(value instanceof Map) {
                Map<Object, Object> dataMap = (Map<Object, Object>)value;
                Map<Object, Object> valueMap = (Map<Object, Object>)condition.getValue();
                if(MapUtils.isEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }else if(MapUtils.isEmpty(dataMap) && MapUtils.isNotEmpty(valueMap)) {
                    return false;
                }else if(MapUtils.isNotEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }
                for(Map.Entry<Object, Object> entry : valueMap.entrySet()) {
                    Object valueKey = entry.getKey();
                    Object valueValue = entry.getValue();
                    if(!dataMap.containsKey(valueKey))
                        return false;
                    Object dataValue = dataMap.get(valueKey);
                    if(valueValue instanceof Integer && dataValue instanceof Integer) {
                        if((Integer)dataValue <= (Integer)valueValue)
                            return false;
                    }else if(valueValue instanceof BigDecimal && dataValue instanceof BigDecimal) {
                        if(0 >= ((BigDecimal) dataValue).compareTo((BigDecimal)valueValue))
                            return false;
                    }else {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignGreaterThanEqHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            if(value instanceof Map) {
                Map<Object, Object> dataMap = (Map<Object, Object>)value;
                Map<Object, Object> valueMap = (Map<Object, Object>)condition.getValue();
                if(MapUtils.isEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }else if(MapUtils.isEmpty(dataMap) && MapUtils.isNotEmpty(valueMap)) {
                    return false;
                }else if(MapUtils.isNotEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }
                for(Map.Entry<Object, Object> entry : valueMap.entrySet()) {
                    Object valueKey = entry.getKey();
                    Object valueValue = entry.getValue();
                    if(!dataMap.containsKey(valueKey))
                        return false;
                    Object dataValue = dataMap.get(valueKey);
                    if(valueValue instanceof Integer && dataValue instanceof Integer) {
                        if((Integer)dataValue < (Integer)valueValue)
                            return false;
                    }else if(valueValue instanceof BigDecimal && dataValue instanceof BigDecimal) {
                        if(0 > ((BigDecimal) dataValue).compareTo((BigDecimal)valueValue))
                            return false;
                    }else {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignLessThanHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            if(value instanceof Map) {
                Map<Object, Object> dataMap = (Map<Object, Object>)value;
                Map<Object, Object> valueMap = (Map<Object, Object>)condition.getValue();
                if(MapUtils.isEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }else if(MapUtils.isEmpty(dataMap) && MapUtils.isNotEmpty(valueMap)) {
                    return false;
                }else if(MapUtils.isNotEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }
                for(Map.Entry<Object, Object> entry : valueMap.entrySet()) {
                    Object valueKey = entry.getKey();
                    Object valueValue = entry.getValue();
                    if(!dataMap.containsKey(valueKey))
                        return false;
                    Object dataValue = dataMap.get(valueKey);
                    if(valueValue instanceof Integer && dataValue instanceof Integer) {
                        if((Integer)dataValue >= (Integer)valueValue)
                            return false;
                    }else if(valueValue instanceof BigDecimal && dataValue instanceof BigDecimal) {
                        if(0 <= ((BigDecimal) dataValue).compareTo((BigDecimal)valueValue))
                            return false;
                    }else {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignLessThanEqHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            if(value instanceof Map) {
                Map<Object, Object> dataMap = (Map<Object, Object>)value;
                Map<Object, Object> valueMap = (Map<Object, Object>)condition.getValue();
                if(MapUtils.isEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }else if(MapUtils.isEmpty(dataMap) && MapUtils.isNotEmpty(valueMap)) {
                    return false;
                }else if(MapUtils.isNotEmpty(dataMap) && MapUtils.isEmpty(valueMap)) {
                    return true;
                }
                for(Map.Entry<Object, Object> entry : valueMap.entrySet()) {
                    Object valueKey = entry.getKey();
                    Object valueValue = entry.getValue();
                    if(!dataMap.containsKey(valueKey))
                        return false;
                    Object dataValue = dataMap.get(valueKey);
                    if(valueValue instanceof Integer && dataValue instanceof Integer) {
                        if((Integer)dataValue > (Integer)valueValue)
                            return false;
                    }else if(valueValue instanceof BigDecimal && dataValue instanceof BigDecimal) {
                        if(0 < ((BigDecimal) dataValue).compareTo((BigDecimal)valueValue))
                            return false;
                    }else {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignIsAnyHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            List<String> dataValue = getValueArray(value);
            List<String> valueList = condition.getValueArray();
            if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isNotEmpty(valueList)) {
                return false;
            }else if(CollectionUtils.isNotEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }
            for (String aValue : valueList) {
                if (dataValue.contains(aValue)) {
                    return true;
                }
            }
            return false;
        };
    }

    static ConditionWrapperHandle fieldServiceAssignNotAnyHandle() {
        return condition -> {
            Object value = getFieldServiceAssignValue(condition);
            List<String> dataValue = getValueArray(value);
            List<String> valueList = condition.getValueArray();
            if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isEmpty(dataValue) && CollectionUtils.isNotEmpty(valueList)) {
                return true;
            }else if(CollectionUtils.isNotEmpty(dataValue) && CollectionUtils.isEmpty(valueList)) {
                return true;
            }
            for (String aValue : valueList) {
                if (dataValue.contains(aValue)) {
                    return false;
                }
            }
            return true;
        };
    }

    static ConditionWrapperHandle beforeHourMinuteHandle() {
        return condition -> {
            Object value = getValue(condition);
            Object inputValue = condition.getValueStr();
            if (value == null || inputValue == null) {
                return false;
            }
            if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                int[] time = DateUtils.split(inputValue);
                LocalDateTime valueDateTime = (LocalDateTime) value;
                LocalDateTime inputDateTime = LocalDateTime.of(valueDateTime.getYear(),
                        valueDateTime.getMonth(), valueDateTime.getDayOfMonth(), time[0], time[1]);
                return valueDateTime.isBefore(inputDateTime);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHourMinuteHandle() {
        return condition -> {
            Object value = getValue(condition);
            Object inputValue = condition.getValueStr();
            if (value == null || inputValue == null) {
                return false;
            }
            if (condition.getValueType().equals(TIME) && value instanceof LocalDateTime) {
                int[] time = DateUtils.split(inputValue);
                LocalDateTime valueDateTime = (LocalDateTime) value;
                LocalDateTime inputDateTime = LocalDateTime.of(valueDateTime.getYear(),
                        valueDateTime.getMonth(), valueDateTime.getDayOfMonth(), time[0], time[1]);
                return valueDateTime.isAfter(inputDateTime);
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isFieldHandle() {
        return iSHandle();
    }

    static ConditionWrapperHandle notFieldHandle() {
        return notHandle();
    }

    static ConditionWrapperHandle greaterThanFieldHandle() {
        return greaterThanHandle();
    }

    static ConditionWrapperHandle greaterThanEqFieldHandle() {
        return greaterThanEqHandle();
    }

    static ConditionWrapperHandle lessThanFieldHandle() {
        return lessThanHandle();
    }

    static ConditionWrapperHandle lessThanEqFieldHandle() {
        return lessThanEqHandle();
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }
}
