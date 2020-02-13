package com.unisound.iot.controller.elasticSearchUtil.condition.wrapperHandler;


import com.unisound.iot.controller.ES.DateUtils;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.BaseConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.DataBaseConditionRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.StringJoiner;

import static com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType.*;


public interface MySqlConditionWrapperHandle extends ConditionWrapperHandle {

    static StringJoiner getJoiner() {
        return new StringJoiner(" ", "(", ")");
    }

    static ConditionWrapperHandle iSHandle() {
        return condition ->
                getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("=").add("'" + condition.getValue() + "'").toString();
    }

    static ConditionWrapperHandle notHandle() {
        return condition ->
                getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("<>").add("'" + condition.getValue() + "'").toString();
    }

    static ConditionWrapperHandle prefixContainsHandle() {
        return condition -> {
            if (condition.getValueType().equals(LONG_TEXT) || condition.getValueType().equals(RICH_TEXT)) {
                return "";
            } else if (condition.getValueType().equals(TEXT)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("LIKE").add("'" + condition.getValueStr() + "%" + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsHandle() {
        return condition -> {
            if (condition.getValueType().equals(LONG_TEXT) || condition.getValueType().equals(RICH_TEXT)) {
                return "";
            } else if (condition.getValueType().equals(TEXT)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("NOT").add("LIKE").add("'" + condition.getValueStr() + "%" + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsHandle() {
        return condition -> {
            if (condition.getValueType().equals(LONG_TEXT) || condition.getValueType().equals(RICH_TEXT)) {
                return "";
            } else if (condition.getValueType().equals(TEXT)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("LIKE").add("'" + "%" + condition.getValueStr() + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsHandle() {
        return condition -> {
            if (condition.getValueType().equals(LONG_TEXT) || condition.getValueType().equals(RICH_TEXT)) {
                return "";
            } else if (condition.getValueType().equals(TEXT)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("NOT").add("LIKE").add("'" + "%" + condition.getValueStr() + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsHandle() {
        return condition -> {
            if (condition.getValueType().equals(LONG_TEXT) || condition.getValueType().equals(RICH_TEXT)) {
                return "";
            }else if (condition.getValueType().equals(TEXT)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("LIKE").add("'" + "%" + condition.getValueStr() + "%" + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsHandle() {
        return condition -> {
            if (condition.getValueType().equals(LONG_TEXT) || condition.getValueType().equals(RICH_TEXT)) {
                return "";
            }else if (condition.getValueType().equals(TEXT)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("NOT").add("LIKE").add("'" + "%" + condition.getValueStr() + "%" + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return condition -> {
            if(condition.getValueArray().size() == 0){
                StringJoiner sj = new StringJoiner(" OR ", "(", ")");
                sj.add("1=2");
                return sj.toString();
            }
            if (isMultiValue(condition.getValueType())) {
                StringJoiner sj = new StringJoiner(" OR ", "(", ")");
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    sj.add("FIND_IN_SET('" + condition.getValueArray().get(i) + "', " + tableAlias(condition) + condition.getFieldName() + ")");
                }
                return sj.toString();
            }
            if (condition.getValueType().equals(NUMERIC)) {
                StringJoiner sj = new StringJoiner(" OR ", "(", ")");
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    sj.add(tableAlias(condition) + condition.getFieldName() + "=" + condition.getValueArray().get(i));
                }
                return sj.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                StringJoiner sj = new StringJoiner(" AND ", "(", ")");
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    sj.add("NOT FIND_IN_SET('" + condition.getValueArray().get(i) + "', " + tableAlias(condition) + condition.getFieldName() + ")");
                }
                return sj.toString();
            }
            if (condition.getValueType().equals(NUMERIC)) {
                StringJoiner sj = new StringJoiner(" AND ", "(", ")");
                for (int i = 0; i < condition.getValueArray().size(); i++) {
                    sj.add(tableAlias(condition) + condition.getFieldName() + "<>" + condition.getValueArray().get(i));
                }
                return sj.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                return "";
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                return "";
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                return "";
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                return "";
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                return "";
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                return "";
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isNullHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("IS NULL OR").add(condition.getFieldName()).add("= ''").toString();
            }else{
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("IS NULL").toString();
            }
        };
    }

    static ConditionWrapperHandle isNotNullHandle() {
        return condition -> {
            if (isText(condition.getValueType())) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("IS NOT NULL OR").add(condition.getFieldName()).add("<> ''").toString();
            }else{
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("IS NOT NULL").toString();
            }
        };
    }

    static ConditionWrapperHandle greaterThanHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add(">").add("'" + condition.getValue() + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add(">=").add("'" + condition.getValue() + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("<").add("'" + condition.getValue() + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return condition -> {
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME)) {
                return getJoiner().add(tableAlias(condition) + condition.getFieldName()).add("<=").add("'" + condition.getValue() + "'").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("DATE(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("DATE(NOW() - INTERVAL 1 DAY)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("DATE(NOW() + INTERVAL 1 DAY)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add(">=").add("DATE(NOW())").add("AND").add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add("<=").add("DATE(NOW() + INTERVAL 7 DAY)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add("<=").add("DATE(NOW())").add("AND").add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add(">=").add("DATE(NOW() - INTERVAL 7 DAY)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add("<=").add("DATE(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("DATE(" + tableAlias(condition) + condition.getFieldName() + ")").add(">=").add("DATE(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEARWEEK(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEARWEEK(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEARWEEK(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEARWEEK(NOW() - INTERVAL 1 WEEK)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEARWEEK(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEARWEEK(NOW() + INTERVAL 1 WEEK)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEAR(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").add("AND").add("MONTH(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("MONTH(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEAR(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").add("AND").add("MONTH(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("MONTH(NOW() - INTERVAL 1 MONTH)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEAR(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").add("AND").add("MONTH(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("MONTH(NOW() + INTERVAL 1 MONTH)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEAR(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEAR(NOW())").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEAR(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEAR(NOW() - INTERVAL 1 YEAR)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                return getJoiner().add("YEAR(" + tableAlias(condition) + condition.getFieldName() + ")").add("=").add("YEAR(NOW() + INTERVAL 1 YEAR)").toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle betweenHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME) && condition.getValueArray().size() == 2) {
                StringJoiner sj = new StringJoiner(" AND ", "(", ")");
                sj.add(tableAlias(condition) + condition.getFieldName()).add(">=").add(condition.getValueArray().get(0));
                sj.add(tableAlias(condition) + condition.getFieldName()).add("<=").add(condition.getValueArray().get(1));

                return sj.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notBetweenHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME) && condition.getValueArray().size() == 2) {
                StringJoiner sj = new StringJoiner(" OR ", "(", ")");
                sj.add(tableAlias(condition) + condition.getFieldName()).add("<").add(condition.getValueArray().get(0));
                sj.add(tableAlias(condition) + condition.getFieldName()).add(">").add(condition.getValueArray().get(1));

                return sj.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notInDateHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME)) {
                StringJoiner sj = new StringJoiner(" AND ", "(", ")");
                LocalDateTime nextDay = DateUtils.parseDateStrToEndTime(condition.getValue().toString()).plusDays(1);
                sj.add(tableAlias(condition) + condition.getFieldName()).add(">=").add("'" + condition.getValue() + "'");
                sj.add(tableAlias(condition) + condition.getFieldName()).add("<").add("'" + nextDay.format(DateTimeFormatter.ISO_DATE_TIME) + "'");
                return sj.toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAllHandle(){
        return condition -> {
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsAllHandle(){
        return condition -> {
            return notFoundOperation();
        };
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }

    static String tableAlias(BaseConditionRequest condition) {
        return Optional.ofNullable(((DataBaseConditionRequest) condition).getAlias()).map(i -> i + ".").orElse("");
    }
}
