package com.unisound.iot.controller.ES.Api;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.unisound.iot.controller.ES.DateUtils;
import com.unisound.iot.controller.ES.ElasticUtils;
import com.unisound.iot.controller.ES.ValueType;
import com.unisound.iot.controller.ES.enummodle.DateUnit;
import com.unisound.iot.controller.ES.requestHand.BaseConditionRequest;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.script.Script.DEFAULT_SCRIPT_LANG;

public interface ElasticSearchConditionWrapperHandle extends ConditionWrapperHandle {
    String RAW = ".raw";
    String ANALYZER = ".index_analyzer";
    String GROUP_FIELD_DATA_REFER_GROUP_ID = ".refer_group_id";
    String GROUP_FIELD_DATA_FIELD_ID = ".field_id";

    static ConditionWrapperHandle iSHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (ValueType.isMultiValue(condition.getValueType())) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery( getMultiValueEqScript(condition.getValueArray(), condition.getFieldName() + ".raw")));
            } else if (ValueType.isText(condition.getValueType())) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getFieldName() + ".raw", condition.getValueStr()));
            } else if (ValueType.isTimeDate(condition.getValueType()) && condition.getValueStr().length() == 10) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } else {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getFieldName(), condition.getValue()));
            }

            return fireInterceptor(queryBuilder, condition).toString();
        };
    }
    public static Script getMultiValueEqScript(List<String> userInput, String fieldName){
        if(userInput == null){
            userInput = Collections.emptyList();
        }
        Map<String,Object> params = Maps.newHashMap();
        params.put("param1",userInput);
        params.put("param2",userInput.size());
        String expression = String.format("if(params.param2 == doc['%s'].length " +
                "&& params.param1.containsAll(doc['%s'].values))" +
                "{return true;}",fieldName,fieldName);

        return new Script(ScriptType.INLINE, DEFAULT_SCRIPT_LANG, expression,params);
    }

    static ConditionWrapperHandle notHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (ValueType.isMultiValue(condition.getValueType())) {
                queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.scriptQuery(getMultiValueEqScript(condition.getValueArray(), condition.getFieldName() + ".raw")));
            } else if (ValueType.isText(condition.getValueType())) {
                queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(condition.getFieldName() + ".raw", condition.getValueStr()));
            } else if (ValueType.isTimeDate(condition.getValueType())) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
            } else {
                queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(condition.getFieldName(), condition.getValue()));
            }

            return fireInterceptor(queryBuilder, condition).toString();
        };
    }

    static ConditionWrapperHandle prefixContainsHandle() {
        return (condition) -> {
            if (ValueType.isText(condition.getValueType())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchPhrasePrefixQuery(condition.getFieldName() + ".index_analyzer", condition.getValueStr()));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle prefixNotContainsHandle() {
        return (condition) -> {
            if (ValueType.isText(condition.getValueType())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.matchPhrasePrefixQuery(condition.getFieldName() + ".index_analyzer", condition.getValueStr()));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle suffixContainsHandle() {
        return (condition) -> {
            if (ValueType.isText(condition.getValueType())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.wildcardQuery(condition.getFieldName() + ".raw", "*" + condition.getValueStr()));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle suffixNotContainsHandle() {
        return (condition) -> {
            if (ValueType.isText(condition.getValueType())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.wildcardQuery(condition.getFieldName() + ".raw", "*" + condition.getValueStr()));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle containsHandle() {
        return (condition) -> {
            if (ValueType.isText(condition.getValueType())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchPhraseQuery(condition.getFieldName() + ".index_analyzer", condition.getValueStr()));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle notContainsHandle() {
        return (condition) -> {
            if (ValueType.isText(condition.getValueType())) {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.matchPhraseQuery(condition.getFieldName() + ".index_analyzer", condition.getValueStr()));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle isAnyHandle() {
        return (condition) -> {
            BoolQueryBuilder tempBoolQuery;
            if (ValueType.isMultiValue(condition.getValueType())) {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.should(QueryBuilders.termQuery(condition.getFieldName() + ".raw", i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else if (condition.getValueType().equals(ValueType.NUMERIC)) {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.should(QueryBuilders.termQuery(condition.getFieldName(), i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle notAnyHandle() {
        return (condition) -> {
            BoolQueryBuilder tempBoolQuery;
            if (ValueType.isMultiValue(condition.getValueType())) {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.mustNot(QueryBuilders.termQuery(condition.getFieldName() + ".raw", i));
                });
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else if (condition.getValueType().equals(ValueType.NUMERIC)) {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.mustNot(QueryBuilders.termQuery(condition.getFieldName(), i));
                });
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle containsAnyHandle() {
        return (condition) -> {
            if (ValueType.isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.should(QueryBuilders.matchPhraseQuery(condition.getFieldName() + ".index_analyzer", i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle notContainsAnyHandle() {
        return (condition) -> {
            if (ValueType.isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.mustNot(QueryBuilders.matchPhraseQuery(condition.getFieldName() + ".index_analyzer", i));
                });
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle prefixContainsAnyHandle() {
        return (condition) -> {
            if (ValueType.isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.should(QueryBuilders.matchPhrasePrefixQuery(condition.getFieldName() + ".index_analyzer", i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle prefixNotContainsAnyHandle() {
        return (condition) -> {
            if (ValueType.isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.mustNot(QueryBuilders.matchPhrasePrefixQuery(condition.getFieldName() + ".index_analyzer", i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle suffixContainsAnyHandle() {
        return (condition) -> {
            if (ValueType.isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.should(QueryBuilders.wildcardQuery(condition.getFieldName() + ".raw", "*" + i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle suffixNotContainsAnyHandle() {
        return (condition) -> {
            if (ValueType.isMultiValue(condition.getValueType())) {
                BoolQueryBuilder tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.mustNot(QueryBuilders.wildcardQuery(condition.getFieldName() + ".raw", "*" + i));
                });
                tempBoolQuery.minimumShouldMatch(1);
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle isNullHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (!ValueType.isText(condition.getValueType()) && !ValueType.isMultiValue(condition.getValueType())) {
                queryBuilder = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(condition.getFieldName()));
            } else {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().should(QueryBuilders.termQuery(condition.getFieldName() + ".raw", "")).should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(condition.getFieldName()))).minimumShouldMatch("1"));
            }

            return fireInterceptor(queryBuilder, condition).toString();
        };
    }

    static ConditionWrapperHandle isNotNullHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (ValueType.isText(condition.getValueType())) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(condition.getFieldName())).mustNot(QueryBuilders.termQuery(condition.getFieldName() + ".raw", ""));
            } else {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(condition.getFieldName()));
            }

            return fireInterceptor(queryBuilder, condition).toString();
        };
    }

    static ConditionWrapperHandle greaterThanHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gt(condition.getValue()));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle greaterThanEqHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(condition.getValue()));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lessThanHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lt(condition.getValue()));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lessThanEqHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lte(condition.getValue()));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle todayHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/d").lt("now+1d/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle yesterdayHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now-1d/d").lt("now/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle tomorrowHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now+1d/d").lt("now+2d/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle nextSevenDayHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/d").lt("now+7d/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lastSevenDayHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now-7d/d").lt("now/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lastThirtyDayHandle() {
        return (condition) -> {
            return !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE) ? notFoundOperation() : QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now-30d/d").lt("now/d")).toString();
        };
    }

    static ConditionWrapperHandle beforeHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lte(condition.getValue()));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle afterHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(condition.getValue()));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle thisWeekHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/w").lt("now+1w/w"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lastWeekHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now-1w/w").lt("now/w"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle nextWeekHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now+1w/w").lt("now+2w/w"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle thisMonthHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/M").lt("now+1M/M"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lastMonthHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now-1M/M").lt("now/M"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle nextMonthHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now+1M/M").lt("now+2M/M"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle thisYearHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/y").lt("now+1y/y"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lastYearHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now-1y/y").lt("now/y"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle nextYearHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now+1y/y").lt("now+2y/y"));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle betweenHandle() {
        return (condition) -> {
            List list = condition.getValueArray();
            BoolQueryBuilder tempBoolQuery;
            if (ValueType.TIME.equals(condition.getValueType())) {
                tempBoolQuery = QueryBuilders.boolQuery();
                tempBoolQuery.must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                tempBoolQuery = QueryBuilders.boolQuery();
                tempBoolQuery.must(QueryBuilders.rangeQuery(condition.getFieldName()).gt(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notBetweenHandle() {
        return (condition) -> {
            List list = condition.getValueArray();
            BoolQueryBuilder tempBoolQuery;
            if (ValueType.TIME.equals(condition.getValueType())) {
                tempBoolQuery = QueryBuilders.boolQuery();
                tempBoolQuery.mustNot(QueryBuilders.rangeQuery(condition.getFieldName()).gte(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                tempBoolQuery = QueryBuilders.boolQuery();
                tempBoolQuery.mustNot(QueryBuilders.rangeQuery(condition.getFieldName()).gt(list.get(0)).lte(list.get(1)));
                return fireInterceptor(tempBoolQuery, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notInDateHandle() {
        return (condition) -> {
            return notFoundOperation();
        };
    }

    static ConditionWrapperHandle containsAllHandle() {
        return (condition) -> {
            BoolQueryBuilder tempBoolQuery;
            if (ValueType.isComma(condition.getValueType())) {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.must(QueryBuilders.matchPhraseQuery(condition.getFieldName() + ".index_analyzer", i));
                });
                return fireInterceptor(tempBoolQuery, condition).toString();
            } else {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.must(QueryBuilders.termQuery(condition.getFieldName(), i));
                });
                return fireInterceptor(tempBoolQuery, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notContainsAllHandle() {
        return (condition) -> {
            BoolQueryBuilder tempBoolQuery;
            if (ValueType.isComma(condition.getValueType())) {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.must(QueryBuilders.matchPhraseQuery(condition.getFieldName() + ".index_analyzer", i));
                });
                BoolQueryBuilder tempBoolQueryx = QueryBuilders.boolQuery();
                tempBoolQueryx.mustNot(tempBoolQuery);
                return fireInterceptor(tempBoolQueryx, condition).toString();
            } else {
                tempBoolQuery = QueryBuilders.boolQuery();
                condition.getValueArray().forEach((i) -> {
                    tempBoolQuery.mustNot(QueryBuilders.termQuery(condition.getFieldName(), i));
                });
                return fireInterceptor(tempBoolQuery, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle groupInnerFieldContainsAnyHandle() {
        return (condition) -> {
            NestedQueryBuilder nestedQueryBuilder;
            if (ValueType.isMultiValue(condition.getValueType())) {
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).must(QueryBuilders.scriptQuery(getMultiValueEqScript(condition.getValueArray(), condition.getFieldName() + ".raw"))), ScoreMode.None);
            } else if (ValueType.isText(condition.getValueType())) {
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).must(QueryBuilders.termQuery(condition.getFieldName() + ".raw", condition.getValueStr())), ScoreMode.None);
            } else if (ValueType.isTimeDate(condition.getValueType())) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))), ScoreMode.None);
            } else {
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).must(QueryBuilders.termQuery(condition.getFieldName(), condition.getValue())), ScoreMode.None);
            }

            return fireInterceptor(nestedQueryBuilder, condition).toString();
        };
    }

    static ConditionWrapperHandle groupInnerFieldNotContainsAnyHandle() {
        return (condition) -> {
            NestedQueryBuilder nestedQueryBuilder;
            if (ValueType.isMultiValue(condition.getValueType())) {
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).mustNot(QueryBuilders.scriptQuery(getMultiValueEqScript(condition.getValueArray(), condition.getFieldName() + ".raw")))), ScoreMode.None);
            } else if (ValueType.isText(condition.getValueType())) {
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).mustNot(QueryBuilders.termQuery(condition.getFieldName() + ".raw", condition.getValueStr()))), ScoreMode.None);
            } else if (ValueType.isTimeDate(condition.getValueType())) {
                LocalDate localDate = DateUtils.parseDateStr(condition.getValueStr());
                LocalDateTime localDateMin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime localDateMax = LocalDateTime.of(localDate, LocalTime.MAX);
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).mustNot(QueryBuilders.rangeQuery(condition.getFieldName()).gte(localDateMin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).lte(localDateMax.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))), ScoreMode.None);
            } else {
                nestedQueryBuilder = QueryBuilders.nestedQuery(condition.getNestedPath(), QueryBuilders.boolQuery().must(QueryBuilders.boolQuery().must(QueryBuilders.termQuery(condition.getNestedPath() + ".refer_group_id", condition.getReferGroupId())).must(QueryBuilders.termQuery(condition.getNestedPath() + ".field_id", condition.getFieldId())).mustNot(QueryBuilders.termQuery(condition.getFieldName(), condition.getValue()))), ScoreMode.None);
            }

            return fireInterceptor(nestedQueryBuilder, condition).toString();
        };
    }

    static ConditionWrapperHandle beforeHourMinuteHandle() {
        return (condition) -> {
            if (condition.getValueType().equals(ValueType.TIME)) {
                int[] time = DateUtils.split(condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.beforeHourMinuteScript(time, condition.getFieldName())));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle afterHourMinuteHandle() {
        return (condition) -> {
            if (condition.getValueType().equals(ValueType.TIME)) {
                int[] time = DateUtils.split(condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.afterHourMinuteScript(time, condition.getFieldName())));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle isFieldHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                String template = "if (doc['%s'].value == doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle notFieldHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                String template = "if (doc['%s'].value != doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle greaterThanFieldHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                String template = "if (doc['%s'].value > doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle greaterThanEqFieldHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                String template = "if (doc['%s'].value >= doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lessThanFieldHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                String template = "if (doc['%s'].value < doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lessThanEqFieldHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.NUMERIC) && !condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                String template = "if (doc['%s'].value <= doc['%s'].value) {return true;}";
                String expression = String.format(template, condition.getFieldName(), condition.getValue());
                Map<String, Object> params = ImmutableMap.of(condition.getFieldName(), condition.getFieldName(), String.valueOf(condition.getValue()), condition.getValue());
                BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.scriptQuery(ElasticUtils.script(expression, params)));
                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle nextHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                List<String> valueList = condition.getValueArray();
                Integer quantity = Integer.valueOf((String)valueList.get(0));
                String unit = (String)valueList.get(1);
                DateUnit dateUnit = DateUnit.getByValue(unit);
                BoolQueryBuilder queryBuilder;
                switch(dateUnit) {
                    case YEAR:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/y").lt(String.format("now+%sy/y", quantity)));
                        break;
                    case MONTH:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/M").lt(String.format("now+%sM/M", quantity)));
                        break;
                    case DAY:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/d").lt(String.format("now+%sd/d", quantity)));
                        break;
                    case HOUR:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/h").lt(String.format("now+%sh/h", quantity)));
                        break;
                    case MINUTE:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/m").lt(String.format("now+%sm/m", quantity)));
                        break;
                    case SECOND:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/s").lt(String.format("now+%ss/s", quantity)));
                        break;
                    default:
                        return notFoundOperation();
                }

                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle lastHandle() {
        return (condition) -> {
            if (!condition.getValueType().equals(ValueType.TIME) && !condition.getValueType().equals(ValueType.TIME_DATE)) {
                return notFoundOperation();
            } else {
                List<String> valueList = condition.getValueArray();
                Integer quantity = Integer.valueOf((String)valueList.get(0));
                String unit = (String)valueList.get(1);
                DateUnit dateUnit = DateUnit.getByValue(unit);
                BoolQueryBuilder queryBuilder;
                switch(dateUnit) {
                    case YEAR:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(String.format("now-%sy/y", quantity)).lte("now/y"));
                        break;
                    case MONTH:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(String.format("now-%sM/M", quantity)).lte("now/M"));
                        break;
                    case DAY:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(String.format("now-%sd/d", quantity)).lte("now/d"));
                        break;
                    case HOUR:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(String.format("now-%sh/h", quantity)).lte("now/h"));
                        break;
                    case MINUTE:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(String.format("now-%sm/m", quantity)).lte("now/m"));
                        break;
                    case SECOND:
                        queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(String.format("now-%ss/s", quantity)).lte("now/s"));
                        break;
                    default:
                        return notFoundOperation();
                }

                return fireInterceptor(queryBuilder, condition).toString();
            }
        };
    }

    static ConditionWrapperHandle greaterThanCurrentHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(ValueType.TIME)) {
                String currentTime = LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gt(currentTime));
                return fireInterceptor(queryBuilder, condition).toString();
            } else if (condition.getValueType().equals(ValueType.TIME_DATE)) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gt("now/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanCurrentHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(ValueType.TIME)) {
                String currentTime = LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lt(currentTime));
                return fireInterceptor(queryBuilder, condition).toString();
            } else if (condition.getValueType().equals(ValueType.TIME_DATE)) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lt("now/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle eqCurrentHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(ValueType.TIME)) {
                String currentTime = LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String endTime = LocalDateTime.now().plusMinutes(1L).withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(currentTime).lt(endTime));
                return fireInterceptor(queryBuilder, condition).toString();
            } else if (condition.getValueType().equals(ValueType.TIME_DATE)) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/d").lt("now+1d/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle greaterThanEqCurrentHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(ValueType.TIME)) {
                String currentTime = LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte(currentTime));
                return fireInterceptor(queryBuilder, condition).toString();
            } else if (condition.getValueType().equals(ValueType.TIME_DATE)) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).gte("now/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static ConditionWrapperHandle lessThanEqCurrentHandle() {
        return (condition) -> {
            BoolQueryBuilder queryBuilder;
            if (condition.getValueType().equals(ValueType.TIME)) {
                String currentTime = LocalDateTime.now().withSecond(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lte(currentTime));
                return fireInterceptor(queryBuilder, condition).toString();
            } else if (condition.getValueType().equals(ValueType.TIME_DATE)) {
                queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.rangeQuery(condition.getFieldName()).lte("now/d"));
                return fireInterceptor(queryBuilder, condition).toString();
            } else {
                return notFoundOperation();
            }
        };
    }

    static Void notFoundOperation() {
        throw new AssertionError("opration is not exists!");
    }

    static Object fireInterceptor(QueryBuilder queryBuilder, BaseConditionRequest conditionRequest) {
        return conditionRequest.getInterceptor() != null && queryBuilder != null ? conditionRequest.getInterceptor().apply(queryBuilder) : queryBuilder;
    }


}
