package com.unisound.iot.controller.elasticSearchUtil.condition.wrapperHandler;

import com.google.common.collect.ImmutableMap;
import com.unisound.iot.controller.ES.DateUtils;
import com.unisound.iot.controller.elasticSearchUtil.condition.ConditionUtils;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.BaseConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.util.ElasticUtils;
import com.unisound.iot.controller.elasticSearchUtil.condition.value.DateUnit;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

public interface ElasticSearchConditionWrapperHandle extends ConditionWrapperHandle {

    String RAW = ".raw";

    String ANALYZER = ".index_analyzer";

    String GROUP_FIELD_DATA_REFER_GROUP_ID = ".refer_group_id";
    String GROUP_FIELD_DATA_FIELD_ID = ".field_id";

    static ConditionWrapperHandle iSHandle() {

        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isMultiValue(condition.getValueType())) {
                queryBuilder = boolQuery().must(scriptQuery(ConditionUtils.getMultiValueEqScript(
                        condition.getValueArray(),condition.getFieldName() + RAW)));
            } else if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().must(termQuery(condition.getFieldName() + RAW, condition.getValueStr()));
            } else if (isTimeDate(condition.getValueType()) && condition.getValueStr().length()==10) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName())
                        .gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } else {
                queryBuilder = boolQuery().must(termQuery(condition.getFieldName(), condition.getValue()));
            }
            return fireInterceptor(queryBuilder,condition).toString();
        };
    }

    static ConditionWrapperHandle notHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isMultiValue(condition.getValueType())) {
                queryBuilder = boolQuery().mustNot(scriptQuery(ConditionUtils.getMultiValueEqScript(
                        condition.getValueArray(),condition.getFieldName() + RAW)));
            } else if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().mustNot(termQuery(condition.getFieldName() + RAW, condition.getValueStr()));
            } else if (isTimeDate(condition.getValueType())) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                queryBuilder = boolQuery().mustNot(rangeQuery(condition.getFieldName())
                        .gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } else {
                queryBuilder = boolQuery().mustNot(termQuery(condition.getFieldName(), (condition.getValue())));
            }
            return fireInterceptor(queryBuilder,condition).toString();
        };
    }

    static ConditionWrapperHandle prefixContainsHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().must(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, condition.getValueStr()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().mustNot(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, condition.getValueStr()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().must(wildcardQuery(condition.getFieldName() + RAW, "*" + (condition.getValueStr())));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().mustNot(wildcardQuery(condition.getFieldName() + RAW, "*" + (condition.getValueStr())));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().must(matchPhraseQuery(condition.getFieldName() + ANALYZER, condition.getValueStr()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().mustNot(matchPhraseQuery(condition.getFieldName() + ANALYZER, condition.getValueStr()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(termQuery(condition.getFieldName() + RAW, i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            if(condition.getValueType().equals(NUMERIC)){
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(termQuery(condition.getFieldName(), i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(termQuery(condition.getFieldName() + RAW, i)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            if(condition.getValueType().equals(NUMERIC)){
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(termQuery(condition.getFieldName(), i)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(matchPhraseQuery(condition.getFieldName() + ANALYZER, i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(matchPhraseQuery(condition.getFieldName() + ANALYZER, i)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle prefixNotContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(matchPhrasePrefixQuery(condition.getFieldName() + ANALYZER, i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.should(wildcardQuery(condition.getFieldName() + RAW, "*" + i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle suffixNotContainsAnyHandle() {
        return condition -> {
            if (isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(wildcardQuery(condition.getFieldName() + RAW, "*" + i)));
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isNullHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType()) || isMultiValue(condition.getValueType())) {
                queryBuilder = boolQuery().must(boolQuery().should(termQuery(condition.getFieldName() + RAW, "")).should(boolQuery().mustNot(existsQuery(condition.getFieldName()))).minimumShouldMatch("1"));
            } else {
                queryBuilder = boolQuery().mustNot(existsQuery(condition.getFieldName()));
            }
            return fireInterceptor(queryBuilder,condition).toString();
        };
    }

    static ConditionWrapperHandle isNotNullHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (isText(condition.getValueType())) {
                queryBuilder = boolQuery().must(existsQuery(condition.getFieldName())).mustNot(termQuery(condition.getFieldName() + RAW, ""));
            } else {
                queryBuilder = boolQuery().must(existsQuery(condition.getFieldName()));
            }
            return fireInterceptor(queryBuilder,condition).toString();
        };
    }

    static ConditionWrapperHandle greaterThanHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gt(condition.getValue()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(condition.getValue()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lt(condition.getValue()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lte(condition.getValue()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                LocalDateTime localDateMin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lt(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                LocalDateTime localDateMin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(1);
                LocalDateTime localDateMax = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusDays(1);
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lt(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                LocalDateTime localDateMin = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).plusDays(1);
                LocalDateTime localDateMax = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).plusDays(1);
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lt(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d").lt("now+7d/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-7d/d").lt("now/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastThirtyDayHandle() {
        return condition -> {
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                return boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-30d/d").lt("now/d")).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lte(condition.getValue()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(condition.getValue()));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/w").lt("now+1w/w"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1w/w").lt("now/w"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1w/w").lt("now+2w/w"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/M").lt("now+1M/M"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1M/M").lt("now/M"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1M/M").lt("now+2M/M"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/y").lt("now+1y/y"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now-1y/y").lt("now/y"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now+1y/y").lt("now+2y/y"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle betweenHandle() {
        return condition -> {
            List list = (List) condition.getValueArray();
//            if ("date".equals(condition.getValueType())){
//                BoolQueryBuilder tempBoolQuery = boolQuery();
//                LocalDateTime ld1 = DateUtils.parseDateTimeStr(list.get(0).toString());
//                LocalDateTime ld2 = DateUtils.parseDateTimeStr(list.get(1).toString());
//                String left = ld1.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                String right = ld2.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                tempBoolQuery.must(rangeQuery(condition.getFieldName()).gte(left).lte(right));
//                return tempBoolQuery.toString();
//            } else {
            if (TIME.equals(condition.getValueType())){
                BoolQueryBuilder tempBoolQuery = boolQuery();
                tempBoolQuery.must(rangeQuery(condition.getFieldName()).gte(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            } else {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                tempBoolQuery.must(rangeQuery(condition.getFieldName()).gt(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notBetweenHandle() {
        return condition -> {
            List list = (List) condition.getValueArray();
//            if ("date".equals(condition.getValueType())){
//                BoolQueryBuilder tempBoolQuery = boolQuery();
//                LocalDateTime ld1 = DateUtils.parseDateTimeStr(list2.get(0).toString());
//                LocalDateTime ld2 = DateUtils.parseDateTimeStr(list2.get(1).toString());
//                String min = ld1.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                String max = ld2.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                tempBoolQuery.mustNot(rangeQuery(condition.getFieldName()).gte(min).lte(max));
//                return tempBoolQuery.toString();
//            } else {
            if (TIME.equals(condition.getValueType())){
                BoolQueryBuilder tempBoolQuery = boolQuery();
                tempBoolQuery.mustNot(rangeQuery(condition.getFieldName()).gte(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            } else {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                tempBoolQuery.mustNot(rangeQuery(condition.getFieldName()).gt(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notInDateHandle() {
        return condition -> {
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAllHandle(){
        return condition -> {
            if (isComma(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.must(matchPhraseQuery(condition.getFieldName() + ANALYZER, i)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }else{
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.must(termQuery(condition.getFieldName(), i)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notContainsAllHandle(){
        return condition -> {
            if (isComma(condition.getValueType())) {
                BoolQueryBuilder tempBoolQueryTemp = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQueryTemp.must(matchPhraseQuery(condition.getFieldName() + ANALYZER, i)));
                BoolQueryBuilder tempBoolQuery = boolQuery();
                tempBoolQuery.mustNot(tempBoolQueryTemp);
                return fireInterceptor(tempBoolQuery,condition).toString();
            }else{
                BoolQueryBuilder tempBoolQuery = boolQuery();
                condition.getValueArray().forEach(i -> tempBoolQuery.mustNot(termQuery(condition.getFieldName(), i)));
                return fireInterceptor(tempBoolQuery,condition).toString();
            }
        };
    }

    static ConditionWrapperHandle groupInnerFieldContainsAnyHandle() {

        return condition -> {
            NestedQueryBuilder nestedQueryBuilder;
            if (isMultiValue(condition.getValueType())) {
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                                .must(scriptQuery(ConditionUtils.getMultiValueEqScript(condition.getValueArray(),condition.getFieldName() + RAW))),
                        ScoreMode.None);
            } else if (isText(condition.getValueType())) {
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                                .must(termQuery(condition.getFieldName() + RAW, condition.getValueStr())),
                        ScoreMode.None);
            } else if (isTimeDate(condition.getValueType())) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                        .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                        .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                        .must(rangeQuery(condition.getFieldName())
                                .gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                .lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))), ScoreMode.None);
            } else {
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                        .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                        .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                        .must(termQuery(condition.getFieldName(), condition.getValue())), ScoreMode.None);
            }
            return fireInterceptor(nestedQueryBuilder,condition).toString();
        };
    }

    static ConditionWrapperHandle groupInnerFieldNotContainsAnyHandle() {

        return condition -> {
            NestedQueryBuilder nestedQueryBuilder;
            if (isMultiValue(condition.getValueType())) {
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                        .must(boolQuery()
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                                .mustNot(scriptQuery(ConditionUtils.getMultiValueEqScript(
                                        condition.getValueArray(),condition.getFieldName() + RAW)))
                        ), ScoreMode.None);
            } else if (isText(condition.getValueType())) {
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                        .must(boolQuery()
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                                .mustNot(termQuery(condition.getFieldName() + RAW, condition.getValueStr()))
                        ), ScoreMode.None);
            } else if (isTimeDate(condition.getValueType())) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                        .must(boolQuery()
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                                .mustNot(rangeQuery(condition.getFieldName())
                                        .gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                        .lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
                        ), ScoreMode.None);
            } else {
                nestedQueryBuilder = nestedQuery(condition.getNestedPath(), boolQuery()
                        .must(boolQuery()
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_REFER_GROUP_ID, condition.getReferGroupId()))
                                .must(termQuery(condition.getNestedPath() + GROUP_FIELD_DATA_FIELD_ID, condition.getFieldId()))
                                .mustNot(termQuery(condition.getFieldName(), (condition.getValue())))
                        ), ScoreMode.None);
            }
            return fireInterceptor(nestedQueryBuilder,condition).toString();
        };
    }

    static ConditionWrapperHandle beforeHourMinuteHandle(){
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME)) {
                int[] time = DateUtils.split(condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.beforeHourMinuteScript(time, condition.getFieldName())));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle afterHourMinuteHandle(){
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME)) {
                int[] time = DateUtils.split(condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.afterHourMinuteScript(time, condition.getFieldName())));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle isFieldHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if(condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                String template = "if (doc['%s'].value == doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle notFieldHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if(condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                String template = "if (doc['%s'].value != doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanFieldHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if(condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                String template = "if (doc['%s'].value > doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanEqFieldHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if(condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                String template = "if (doc['%s'].value >= doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanFieldHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if(condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                String template = "if (doc['%s'].value < doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanEqFieldHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if(condition.getValueType().equals(NUMERIC) || condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                String template = "if (doc['%s'].value <= doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                queryBuilder = boolQuery().must(scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle nextHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                List<String> valueList = condition.getValueArray();
                Integer quantity = Integer.valueOf(valueList.get(0));
                String unit = valueList.get(1);
                DateUnit dateUnit = DateUnit.getByValue(unit);
                switch (dateUnit) {
                    case YEAR:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/y").lt(String.format("now+%sy/y", quantity)));
                        break;
                    case MONTH:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/M").lt(String.format("now+%sM/M", quantity)));
                        break;
                    case DAY:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d").lt(String.format("now+%sd/d", quantity)));
                        break;
                    case HOUR:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/h").lt(String.format("now+%sh/h", quantity)));
                        break;
                    case MINUTE:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/m").lt(String.format("now+%sm/m", quantity)));
                        break;
                    case SECOND:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/s").lt(String.format("now+%ss/s", quantity)));
                        break;
                    default:
                        return notFoundOperation();
                }
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lastHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) || condition.getValueType().equals(TIME_DATE)) {
                List<String> valueList = condition.getValueArray();
                Integer quantity = Integer.valueOf(valueList.get(0));
                String unit = valueList.get(1);
                DateUnit dateUnit = DateUnit.getByValue(unit);
                switch (dateUnit) {
                    case YEAR:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(String.format("now-%sy/y", quantity)).lte("now/y"));
                        break;
                    case MONTH:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(String.format("now-%sM/M", quantity)).lte("now/M"));
                        break;
                    case DAY:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(String.format("now-%sd/d", quantity)).lte("now/d"));
                        break;
                    case HOUR:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(String.format("now-%sh/h", quantity)).lte("now/h"));
                        break;
                    case MINUTE:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(String.format("now-%sm/m", quantity)).lte("now/m"));
                        break;
                    case SECOND:
                        queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(String.format("now-%ss/s", quantity)).lte("now/s"));
                        break;
                    default:
                        return notFoundOperation();
                }
                return fireInterceptor(queryBuilder, condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanCurrentHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) ) {
                String currentTime=LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gt(currentTime));
                return fireInterceptor(queryBuilder,condition).toString();
            }else if(condition.getValueType().equals(TIME_DATE)){
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gt("now/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle lessThanCurrentHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) ) {
                String currentTime=LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lt(currentTime));
                return fireInterceptor(queryBuilder,condition).toString();
            }else if(condition.getValueType().equals(TIME_DATE)){
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lt("now/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle eqCurrentHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) ) {
                String currentTime=LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String endTime=LocalDateTime.now().plusMinutes(1).withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(currentTime).lt(endTime));
                return fireInterceptor(queryBuilder,condition).toString();
            }else if(condition.getValueType().equals(TIME_DATE)){
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d").lt("now+1d/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle greaterThanEqCurrentHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) ) {
                String currentTime=LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte(currentTime));
                return fireInterceptor(queryBuilder,condition).toString();
            }else if(condition.getValueType().equals(TIME_DATE)){
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).gte("now/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }else{
                return notFoundOperation();
            }

        };
    }

    static ConditionWrapperHandle lessThanEqCurrentHandle() {
        return condition -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(TIME) ) {
                String currentTime=LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lte(currentTime));
                return fireInterceptor(queryBuilder,condition).toString();
            }else if(condition.getValueType().equals(TIME_DATE)){
                queryBuilder = boolQuery().must(rangeQuery(condition.getFieldName()).lte("now/d"));
                return fireInterceptor(queryBuilder,condition).toString();
            }else{
                return notFoundOperation();
            }
        };
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }

    static Object fireInterceptor(QueryBuilder queryBuilder, BaseConditionRequest conditionRequest){
        if(conditionRequest.getInterceptor() != null && queryBuilder != null){
            return conditionRequest.getInterceptor().apply(queryBuilder);
        }else{
            return queryBuilder;
        }
    }
}
