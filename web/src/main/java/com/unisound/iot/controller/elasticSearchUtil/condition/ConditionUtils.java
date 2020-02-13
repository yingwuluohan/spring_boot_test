package com.unisound.iot.controller.elasticSearchUtil.condition;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unisound.iot.controller.elasticSearchUtil.condition.Operator.Operator;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.BaseConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.DataBaseConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.MemoryConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.request.SimpleConditionRequest;
import com.unisound.iot.controller.elasticSearchUtil.condition.util.FormulaUtil;
import com.unisound.iot.controller.elasticSearchUtil.condition.wrapperHandler.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.script.Script.DEFAULT_SCRIPT_LANG;

@Slf4j
public class ConditionUtils {

    private ConditionUtils() {

    }

    private static final Map<DataSource, Map<Operator, ConditionWrapperHandle>> conditionWrapperHandleMap = Maps.newEnumMap(DataSource.class);

    static {
        Map<Operator, ConditionWrapperHandle> mysqlConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        mysqlConditionWrapperHandleMap.put(Operator.IS, MySqlConditionWrapperHandle.iSHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT, MySqlConditionWrapperHandle.notHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, MySqlConditionWrapperHandle.prefixContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, MySqlConditionWrapperHandle.prefixNotContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, MySqlConditionWrapperHandle.suffixContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, MySqlConditionWrapperHandle.suffixNotContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.CONTAINS, MySqlConditionWrapperHandle.containsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, MySqlConditionWrapperHandle.notContainsHandle());
        mysqlConditionWrapperHandleMap.put(Operator.IS_ANY, MySqlConditionWrapperHandle.isAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_ANY, MySqlConditionWrapperHandle.notAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, MySqlConditionWrapperHandle.containsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, MySqlConditionWrapperHandle.notContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, MySqlConditionWrapperHandle.prefixContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, MySqlConditionWrapperHandle.prefixNotContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, MySqlConditionWrapperHandle.suffixContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, MySqlConditionWrapperHandle.suffixNotContainsAnyHandle());
        mysqlConditionWrapperHandleMap.put(Operator.IS_NULL, MySqlConditionWrapperHandle.isNullHandle());
        mysqlConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, MySqlConditionWrapperHandle.isNotNullHandle());
        mysqlConditionWrapperHandleMap.put(Operator.GREATER_THAN, MySqlConditionWrapperHandle.greaterThanHandle());
        mysqlConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, MySqlConditionWrapperHandle.greaterThanEqHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LESS_THAN, MySqlConditionWrapperHandle.lessThanHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, MySqlConditionWrapperHandle.lessThanEqHandle());

        mysqlConditionWrapperHandleMap.put(Operator.TODAY, MySqlConditionWrapperHandle.todayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.YESTERDAY, MySqlConditionWrapperHandle.yesterdayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.TOMORROW, MySqlConditionWrapperHandle.tomorrowHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, MySqlConditionWrapperHandle.nextSevenDayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, MySqlConditionWrapperHandle.lastSevenDayHandle());
        mysqlConditionWrapperHandleMap.put(Operator.BEFORE, MySqlConditionWrapperHandle.beforeHandle());
        mysqlConditionWrapperHandleMap.put(Operator.AFTER, MySqlConditionWrapperHandle.afterHandle());
        mysqlConditionWrapperHandleMap.put(Operator.THIS_WEEK, MySqlConditionWrapperHandle.thisWeekHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_WEEK, MySqlConditionWrapperHandle.lastWeekHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_WEEK, MySqlConditionWrapperHandle.nextWeekHandle());
        mysqlConditionWrapperHandleMap.put(Operator.THIS_MONTH, MySqlConditionWrapperHandle.thisMonthHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_MONTH, MySqlConditionWrapperHandle.lastMonthHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_MONTH, MySqlConditionWrapperHandle.nextMonthHandle());
        mysqlConditionWrapperHandleMap.put(Operator.THIS_YEAR, MySqlConditionWrapperHandle.thisYearHandle());
        mysqlConditionWrapperHandleMap.put(Operator.LAST_YEAR, MySqlConditionWrapperHandle.lastYearHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NEXT_YEAR, MySqlConditionWrapperHandle.nextYearHandle());

        mysqlConditionWrapperHandleMap.put(Operator.CONTAINS_ALL, MySqlConditionWrapperHandle.containsAllHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ALL, MySqlConditionWrapperHandle.notContainsAllHandle());
        mysqlConditionWrapperHandleMap.put(Operator.BETWEEN, MySqlConditionWrapperHandle.betweenHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_BETWEEN, MySqlConditionWrapperHandle.notBetweenHandle());
        mysqlConditionWrapperHandleMap.put(Operator.NOT_IN_DATE, MySqlConditionWrapperHandle.notInDateHandle());

        conditionWrapperHandleMap.put(DataSource.MYSQL, mysqlConditionWrapperHandleMap);

        Map<Operator, ConditionWrapperHandle> elasticSearchConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        elasticSearchConditionWrapperHandleMap.put(Operator.IS, ElasticSearchConditionWrapperHandle.iSHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT, ElasticSearchConditionWrapperHandle.notHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, ElasticSearchConditionWrapperHandle.prefixContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, ElasticSearchConditionWrapperHandle.prefixNotContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, ElasticSearchConditionWrapperHandle.suffixContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, ElasticSearchConditionWrapperHandle.suffixNotContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.CONTAINS, ElasticSearchConditionWrapperHandle.containsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, ElasticSearchConditionWrapperHandle.notContainsHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.prefixContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.prefixNotContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.suffixContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.suffixNotContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.IS_ANY, ElasticSearchConditionWrapperHandle.isAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_ANY, ElasticSearchConditionWrapperHandle.notAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, ElasticSearchConditionWrapperHandle.containsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.notContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.IS_NULL, ElasticSearchConditionWrapperHandle.isNullHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, ElasticSearchConditionWrapperHandle.isNotNullHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN, ElasticSearchConditionWrapperHandle.greaterThanHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, ElasticSearchConditionWrapperHandle.greaterThanEqHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN, ElasticSearchConditionWrapperHandle.lessThanHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, ElasticSearchConditionWrapperHandle.lessThanEqHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.TODAY, ElasticSearchConditionWrapperHandle.todayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.YESTERDAY, ElasticSearchConditionWrapperHandle.yesterdayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.TOMORROW, ElasticSearchConditionWrapperHandle.tomorrowHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, ElasticSearchConditionWrapperHandle.nextSevenDayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, ElasticSearchConditionWrapperHandle.lastSevenDayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_THIRTY_DAY, ElasticSearchConditionWrapperHandle.lastThirtyDayHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.BEFORE, ElasticSearchConditionWrapperHandle.beforeHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.AFTER, ElasticSearchConditionWrapperHandle.afterHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.THIS_WEEK, ElasticSearchConditionWrapperHandle.thisWeekHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_WEEK, ElasticSearchConditionWrapperHandle.lastWeekHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_WEEK, ElasticSearchConditionWrapperHandle.nextWeekHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.THIS_MONTH, ElasticSearchConditionWrapperHandle.thisMonthHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_MONTH, ElasticSearchConditionWrapperHandle.lastMonthHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_MONTH, ElasticSearchConditionWrapperHandle.nextMonthHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.THIS_YEAR, ElasticSearchConditionWrapperHandle.thisYearHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST_YEAR, ElasticSearchConditionWrapperHandle.lastYearHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT_YEAR, ElasticSearchConditionWrapperHandle.nextYearHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.CONTAINS_ALL, ElasticSearchConditionWrapperHandle.containsAllHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ALL, ElasticSearchConditionWrapperHandle.notContainsAllHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.BETWEEN, ElasticSearchConditionWrapperHandle.betweenHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_BETWEEN, ElasticSearchConditionWrapperHandle.notBetweenHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_IN_DATE, ElasticSearchConditionWrapperHandle.notInDateHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.GROUP_INNER_FIELD_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.groupInnerFieldContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GROUP_INNER_FIELD_NOT_CONTAINS_ANY, ElasticSearchConditionWrapperHandle.groupInnerFieldNotContainsAnyHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.BEFORE_HOUR_MINUTE, ElasticSearchConditionWrapperHandle.beforeHourMinuteHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.AFTER_HOUR_MINUTE, ElasticSearchConditionWrapperHandle.afterHourMinuteHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.IS_FIELD, ElasticSearchConditionWrapperHandle.isFieldHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.NOT_FIELD, ElasticSearchConditionWrapperHandle.notFieldHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN_FIELD, ElasticSearchConditionWrapperHandle.greaterThanFieldHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ_FIELD, ElasticSearchConditionWrapperHandle.greaterThanEqFieldHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN_FIELD, ElasticSearchConditionWrapperHandle.lessThanFieldHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ_FIELD, ElasticSearchConditionWrapperHandle.lessThanEqFieldHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.NEXT, ElasticSearchConditionWrapperHandle.nextHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LAST, ElasticSearchConditionWrapperHandle.lastHandle());

        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN_CURRENT, ElasticSearchConditionWrapperHandle.greaterThanCurrentHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN_CURRENT, ElasticSearchConditionWrapperHandle.lessThanCurrentHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.EQ_CURRENT, ElasticSearchConditionWrapperHandle.eqCurrentHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ_CURRENT, ElasticSearchConditionWrapperHandle.greaterThanEqCurrentHandle());
        elasticSearchConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ_CURRENT, ElasticSearchConditionWrapperHandle.lessThanEqCurrentHandle());

        conditionWrapperHandleMap.put(DataSource.ELASTICSEARCH, elasticSearchConditionWrapperHandleMap);

        Map<Operator, ConditionWrapperHandle> simpleConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        simpleConditionWrapperHandleMap.put(Operator.IS, SimpleConditionWrapperHandle.iSHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT, SimpleConditionWrapperHandle.notHandle());
        simpleConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, SimpleConditionWrapperHandle.prefixContainsHandle());
        simpleConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, SimpleConditionWrapperHandle.prefixNotContainsHandle());
        simpleConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, SimpleConditionWrapperHandle.suffixContainsHandle());
        simpleConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, SimpleConditionWrapperHandle.suffixNotContainsHandle());
        simpleConditionWrapperHandleMap.put(Operator.CONTAINS, SimpleConditionWrapperHandle.containsHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, SimpleConditionWrapperHandle.notContainsHandle());
        simpleConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, SimpleConditionWrapperHandle.prefixContainsAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, SimpleConditionWrapperHandle.prefixNotContainsAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, SimpleConditionWrapperHandle.suffixContainsAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, SimpleConditionWrapperHandle.suffixNotContainsAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.IS_ANY, SimpleConditionWrapperHandle.isAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT_ANY, SimpleConditionWrapperHandle.notAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, SimpleConditionWrapperHandle.containsAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, SimpleConditionWrapperHandle.notContainsAnyHandle());
        simpleConditionWrapperHandleMap.put(Operator.IS_NULL, SimpleConditionWrapperHandle.isNullHandle());
        simpleConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, SimpleConditionWrapperHandle.isNotNullHandle());
        simpleConditionWrapperHandleMap.put(Operator.GREATER_THAN, SimpleConditionWrapperHandle.greaterThanHandle());
        simpleConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, SimpleConditionWrapperHandle.greaterThanEqHandle());
        simpleConditionWrapperHandleMap.put(Operator.LESS_THAN, SimpleConditionWrapperHandle.lessThanHandle());
        simpleConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, SimpleConditionWrapperHandle.lessThanEqHandle());

        simpleConditionWrapperHandleMap.put(Operator.TODAY, SimpleConditionWrapperHandle.todayHandle());
        simpleConditionWrapperHandleMap.put(Operator.YESTERDAY, SimpleConditionWrapperHandle.yesterdayHandle());
        simpleConditionWrapperHandleMap.put(Operator.TOMORROW, SimpleConditionWrapperHandle.tomorrowHandle());
        simpleConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, SimpleConditionWrapperHandle.nextSevenDayHandle());
        simpleConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, SimpleConditionWrapperHandle.lastSevenDayHandle());
        simpleConditionWrapperHandleMap.put(Operator.BEFORE, SimpleConditionWrapperHandle.beforeHandle());
        simpleConditionWrapperHandleMap.put(Operator.AFTER, SimpleConditionWrapperHandle.afterHandle());
        simpleConditionWrapperHandleMap.put(Operator.THIS_WEEK, SimpleConditionWrapperHandle.thisWeekHandle());
        simpleConditionWrapperHandleMap.put(Operator.LAST_WEEK, SimpleConditionWrapperHandle.lastWeekHandle());
        simpleConditionWrapperHandleMap.put(Operator.NEXT_WEEK, SimpleConditionWrapperHandle.nextWeekHandle());
        simpleConditionWrapperHandleMap.put(Operator.THIS_MONTH, SimpleConditionWrapperHandle.thisMonthHandle());
        simpleConditionWrapperHandleMap.put(Operator.LAST_MONTH, SimpleConditionWrapperHandle.lastMonthHandle());
        simpleConditionWrapperHandleMap.put(Operator.NEXT_MONTH, SimpleConditionWrapperHandle.nextMonthHandle());
        simpleConditionWrapperHandleMap.put(Operator.THIS_YEAR, SimpleConditionWrapperHandle.thisYearHandle());
        simpleConditionWrapperHandleMap.put(Operator.LAST_YEAR, SimpleConditionWrapperHandle.lastYearHandle());
        simpleConditionWrapperHandleMap.put(Operator.NEXT_YEAR, SimpleConditionWrapperHandle.nextYearHandle());

        simpleConditionWrapperHandleMap.put(Operator.CONTAINS_ALL, SimpleConditionWrapperHandle.containsAllHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ALL, SimpleConditionWrapperHandle.notContainsAllHandle());
        simpleConditionWrapperHandleMap.put(Operator.BETWEEN, SimpleConditionWrapperHandle.betweenHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT_BETWEEN, SimpleConditionWrapperHandle.notBetweenHandle());
        simpleConditionWrapperHandleMap.put(Operator.NOT_IN_DATE, SimpleConditionWrapperHandle.notInDateHandle());

        conditionWrapperHandleMap.put(DataSource.SIMPLE_VALUE, simpleConditionWrapperHandleMap);

        Map<Operator, ConditionWrapperHandle> javaBeanConditionWrapperHandleMap = Maps.newEnumMap(Operator.class);
        javaBeanConditionWrapperHandleMap.put(Operator.IS, JavaBeanConditionWrapperHandle.iSHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT, JavaBeanConditionWrapperHandle.notHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS, JavaBeanConditionWrapperHandle.prefixContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS, JavaBeanConditionWrapperHandle.prefixNotContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS, JavaBeanConditionWrapperHandle.suffixContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS, JavaBeanConditionWrapperHandle.suffixNotContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.CONTAINS, JavaBeanConditionWrapperHandle.containsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_CONTAINS, JavaBeanConditionWrapperHandle.notContainsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_CONTAINS_ANY, JavaBeanConditionWrapperHandle.prefixContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.PREFIX_NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.prefixNotContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_CONTAINS_ANY, JavaBeanConditionWrapperHandle.suffixContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.SUFFIX_NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.suffixNotContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.IS_ANY, JavaBeanConditionWrapperHandle.isAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_ANY, JavaBeanConditionWrapperHandle.notAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.CONTAINS_ANY, JavaBeanConditionWrapperHandle.containsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.notContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.IS_NULL, JavaBeanConditionWrapperHandle.isNullHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.IS_NOT_NULL, JavaBeanConditionWrapperHandle.isNotNullHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GREATER_THAN, JavaBeanConditionWrapperHandle.greaterThanHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ, JavaBeanConditionWrapperHandle.greaterThanEqHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LESS_THAN, JavaBeanConditionWrapperHandle.lessThanHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ, JavaBeanConditionWrapperHandle.lessThanEqHandle());

        javaBeanConditionWrapperHandleMap.put(Operator.TODAY, JavaBeanConditionWrapperHandle.todayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.YESTERDAY, JavaBeanConditionWrapperHandle.yesterdayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.TOMORROW, JavaBeanConditionWrapperHandle.tomorrowHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_SEVEN_DAY, JavaBeanConditionWrapperHandle.nextSevenDayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_SEVEN_DAY, JavaBeanConditionWrapperHandle.lastSevenDayHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.BEFORE, JavaBeanConditionWrapperHandle.beforeHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.AFTER, JavaBeanConditionWrapperHandle.afterHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.THIS_WEEK, JavaBeanConditionWrapperHandle.thisWeekHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_WEEK, JavaBeanConditionWrapperHandle.lastWeekHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_WEEK, JavaBeanConditionWrapperHandle.nextWeekHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.THIS_MONTH, JavaBeanConditionWrapperHandle.thisMonthHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_MONTH, JavaBeanConditionWrapperHandle.lastMonthHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_MONTH, JavaBeanConditionWrapperHandle.nextMonthHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.THIS_YEAR, JavaBeanConditionWrapperHandle.thisYearHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LAST_YEAR, JavaBeanConditionWrapperHandle.lastYearHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NEXT_YEAR, JavaBeanConditionWrapperHandle.nextYearHandle());

        javaBeanConditionWrapperHandleMap.put(Operator.CONTAINS_ALL, JavaBeanConditionWrapperHandle.containsAllHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_CONTAINS_ALL, JavaBeanConditionWrapperHandle.notContainsAllHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.BETWEEN, JavaBeanConditionWrapperHandle.betweenHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_BETWEEN, JavaBeanConditionWrapperHandle.notBetweenHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_IN_DATE, JavaBeanConditionWrapperHandle.notInDateHandle());

        javaBeanConditionWrapperHandleMap.put(Operator.GROUP_INNER_FIELD_CONTAINS_ANY, JavaBeanConditionWrapperHandle.groupInnerFieldContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GROUP_INNER_FIELD_NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.groupInnerFieldNotContainsAnyHandle());

        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_CONTAINS_ANY, JavaBeanConditionWrapperHandle.fieldServiceAssignContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_NOT_CONTAINS_ANY, JavaBeanConditionWrapperHandle.fieldServiceAssignNotContainsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_CONTAINS_ALL, JavaBeanConditionWrapperHandle.fieldServiceAssignContainsAllHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_NOT_CONTAINS_ALL, JavaBeanConditionWrapperHandle.fieldServiceAssignNotContainsAllHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_IS, JavaBeanConditionWrapperHandle.fieldServiceAssignIsHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_NOT, JavaBeanConditionWrapperHandle.fieldServiceAssignNotHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_GREATER_THAN, JavaBeanConditionWrapperHandle.fieldServiceAssignGreaterThanHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_GREATER_THAN_EQ, JavaBeanConditionWrapperHandle.fieldServiceAssignGreaterThanEqHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_LESS_THAN, JavaBeanConditionWrapperHandle.fieldServiceAssignLessThanHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_LESS_THAN_EQ, JavaBeanConditionWrapperHandle.fieldServiceAssignLessThanEqHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_IS_ANY, JavaBeanConditionWrapperHandle.fieldServiceAssignIsAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.FIELD_SERVICE_ASSIGN_NOT_ANY, JavaBeanConditionWrapperHandle.fieldServiceAssignNotAnyHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.BEFORE_HOUR_MINUTE, JavaBeanConditionWrapperHandle.beforeHourMinuteHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.AFTER_HOUR_MINUTE, JavaBeanConditionWrapperHandle.afterHourMinuteHandle());

        javaBeanConditionWrapperHandleMap.put(Operator.IS_FIELD, JavaBeanConditionWrapperHandle.isFieldHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.NOT_FIELD, JavaBeanConditionWrapperHandle.notFieldHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GREATER_THAN_FIELD, JavaBeanConditionWrapperHandle.greaterThanFieldHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.GREATER_THAN_EQ_FIELD, JavaBeanConditionWrapperHandle.greaterThanEqFieldHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LESS_THAN_FIELD, JavaBeanConditionWrapperHandle.lessThanFieldHandle());
        javaBeanConditionWrapperHandleMap.put(Operator.LESS_THAN_EQ_FIELD, JavaBeanConditionWrapperHandle.lessThanEqFieldHandle());

        conditionWrapperHandleMap.put(DataSource.JAVABEAN, javaBeanConditionWrapperHandleMap);
    }

    /**
     * 简单数据校验
     *
     * @param conditionList {@link SimpleConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean simpleValidate(@NonNull final List<SimpleConditionRequest> conditionList) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        return simpleValidate(generateFullAndFormula(conditionList.size(), "and"), conditionList);
    }

    /**
     * 简单数据校验
     *
     * @param conditionList {@link SimpleConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean simpleValidate(@NonNull final List<SimpleConditionRequest> conditionList, @NotNull final String connector) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        checkArgument(connector.contains("and") || connector.contains("or"));
        return simpleValidate(generateFullAndFormula(conditionList.size(), connector), conditionList);
    }

    /**
     * 简单数据校验
     *
     * @param formula       like 1 and 2 or 3
     * @param conditionList {@link SimpleConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean simpleValidate(@NonNull final String formula, @NonNull final List<SimpleConditionRequest> conditionList) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        return simpleValidate(DataSource.SIMPLE_VALUE, formula, conditionList);
    }

    /**
     * 简单数据校验
     *
     * @param dataSource    support simple value
     * @param formula       like 1 and 2 or 3
     * @param conditionList {@link SimpleConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean simpleValidate(@NonNull final DataSource dataSource, @NonNull final String formula, @NonNull final List<SimpleConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.SIMPLE_VALUE));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, Boolean> conditionWrapperMap = simpleSnippetConditionWrapper(dataSource, conditionList);
        return checkFormula(formula, conditionWrapperMap);
    }

    /**
     * 内存数据校验
     *
     * @param conditionList {@link MemoryConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean memoryValidate(@NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        return memoryValidate(generateFullAndFormula(conditionList.size(), "and"), conditionList);
    }

    /**
     * 内存数据校验
     *
     * @param formula       like 1 and 2 or 3
     * @param conditionList {@link MemoryConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean memoryValidate(@NonNull final String formula, @NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        return memoryValidate(DataSource.JAVABEAN, formula, conditionList);
    }

    /**
     * 内存数据校验
     *
     * @param dataSource    support mysql, elasticsearch, javaBean
     * @param formula       like 1 and 2 or 3
     * @param conditionList {@link MemoryConditionRequest}
     * @return true if the execute should be success; otherwise false
     */
    public static boolean memoryValidate(@NonNull final DataSource dataSource, @NonNull final String formula, @NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.JAVABEAN));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, Boolean> conditionWrapperMap = memorySnippetConditionWrapper(dataSource, conditionList);
        return checkFormula(formula, conditionWrapperMap);
    }

    /**
     * 简单数据条件包装
     *
     * @param dataSource    support simple_value
     * @param conditionList {@link  }
     * @return db like 1->"companyId=1",2->"name like data" memory like 1->true, 2->false
     */
    public static Map<Integer, Boolean> simpleSnippetConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<SimpleConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.SIMPLE_VALUE));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, Boolean> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
        final Map<Operator, ConditionWrapperHandle> handleMap = conditionWrapperHandleMap.get(dataSource);
        int[] idx = {1};
        conditionList.forEach(i -> formulaMap.put(idx[0]++,
                i.getResult() != null ? i.getResult() : (boolean) singleConditionWrapper(handleMap, i, dataSource)));
        return formulaMap;
    }

    /**
     * 内存数据条件包装
     *
     * @param dataSource    support javaBean
     * @param conditionList {@link  }
     * @return db like 1->"companyId=1",2->"name like data" memory like 1->true, 2->false
     */
    private static Map<Integer, Boolean> memorySnippetConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<MemoryConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.JAVABEAN));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, Boolean> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
        final Map<Operator, ConditionWrapperHandle> handleMap = conditionWrapperHandleMap.get(dataSource);
        int[] idx = {1};
        conditionList.forEach(i -> formulaMap.put(idx[0]++,
                i.getResult() != null ? i.getResult() : (boolean) singleConditionWrapper(handleMap, i, dataSource)));
        return formulaMap;
    }

    private static Object singleConditionWrapper(@NonNull final Map<Operator, ConditionWrapperHandle> handleMap, @NonNull final BaseConditionRequest condition, DataSource dataSource) {
        if(condition.getResult() != null){
            return condition.getResult();
        }
        if(CollectionUtils.isEmpty(condition.getAttachmentList())){
//            return handleMap.get(condition.getOperator()).exec(condition);
            return handleMap.get(condition.getOperator()).exec(null );
        }

        Operator operator= condition.getOperator();
        String joinSign = isNegativeOperator(operator) ? "or" : "and";
        List<BaseConditionRequest> conditionList = condition.getAttachmentList();
        condition.setAttachmentList(null);
        conditionList.add(condition);
        int[] idx = {1};
        if(!dataSource.equals(DataSource.JAVABEAN) && !dataSource.equals(DataSource.SIMPLE_VALUE)){
            final Map<Integer, String> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
            conditionList.forEach(i -> formulaMap.put(idx[0]++, String.valueOf(singleConditionWrapper(handleMap, i, dataSource))));
            return addBracket(dataSource, databaseCombineConditionWrapper(dataSource, formulaMap,joinSign));
        }else{
            final Map<Integer, Boolean> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
            conditionList.forEach(i -> formulaMap.put(idx[0]++, (boolean) singleConditionWrapper(handleMap, i, dataSource)));
            return checkFormula(generateFullAndFormula(conditionList.size(), joinSign), formulaMap);
        }
    }


    private static boolean isNegativeOperator(Operator operator){
        List<Operator> negativeOperatorList = Lists.newArrayList(Operator.NOT,
                Operator.NOT_ANY,Operator.NOT_BETWEEN,Operator.NOT_CONTAINS,Operator.NOT_CONTAINS_ALL,
                Operator.NOT_CONTAINS_ANY,Operator.NOT_IN_DATE,Operator.IS_NOT_NULL,Operator.PREFIX_NOT_CONTAINS,
                Operator.PREFIX_NOT_CONTAINS_ANY,Operator.SUFFIX_NOT_CONTAINS,Operator.SUFFIX_NOT_CONTAINS_ANY);

        return negativeOperatorList.contains(operator);
    }


    /**
     * 数据库条件包装，条件拼接符默认为and
     *
     * @param dataSource    support mysql, elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        Map<Integer, String> snippetConditionMap = databaseSnippetConditionWrapper(dataSource, conditionList);
        return databaseCombineConditionWrapper(dataSource, snippetConditionMap,"and");
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

    /**
     * ES搜索条件包装
     * //todo:暂时保留兼容其他任务，过后删除
     *
     * @param dataSource    support elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return like (companyId=1) and (name like data)
     */
    public static String elasticSearchSearchConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.ELASTICSEARCH));
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        conditionList.forEach(i -> boolQueryBuilder.should(matchQuery(i.getFieldName() + ".index_analyzer", i.getValueStr())));
        boolQueryBuilder.minimumShouldMatch(1);
        return boolQuery().must(boolQueryBuilder).toString();
    }

    /**
     * ES搜索条件包装
     *
     * @param dataSource    support elasticsearch
     * @param keywordFieldNameList {@link List<String>}
     * @return like (companyId=1) and (name like data)
     */
    public static String elasticSearchSearchConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<String> keywordFieldNameList , String keyword) {
        checkArgument(dataSource.equals(DataSource.ELASTICSEARCH));
        BoolQueryBuilder boolQueryBuilder = boolQuery();

        int size = keywordFieldNameList.size();
        String[] namesShingle = new String[size];
        String[] namesIndex = new String[size];
        int index = 0;
        for(String fieldName : keywordFieldNameList){
            namesShingle[index] = fieldName+".shingle_analyzer";
            namesIndex[index] = fieldName+".index_analyzer";
            index++;
        }
        boolQueryBuilder
                .must(multiMatchQuery(keyword, namesIndex).boost(0.1F))
                .should(multiMatchQuery(keyword, namesShingle).minimumShouldMatch("50%").boost(2F));

        return boolQuery().must(boolQueryBuilder).toString();
    }

    /**
     * ES包含任意条件包装
     *
     * @param dataSource    support elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @return like (companyId=1) and (name like data)
     */
    public static String elasticSearchMinimumShouldMatchConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(dataSource.equals(DataSource.ELASTICSEARCH));
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        conditionList.forEach(i -> {
            if (i.getValue() instanceof List) {
                boolQueryBuilder.should(termsQuery(i.getFieldName(), (List) i.getValue()));
            } else {
                boolQueryBuilder.should(termQuery(i.getFieldName(), i.getValue()));
            }
        });
        boolQueryBuilder.minimumShouldMatch(1);
        return boolQuery().must(boolQueryBuilder).toString();
    }

    /**
     * ES去除评分
     *
     * @param conditionStr    support elasticsearch
     * @return replace boost 1.0 -> 0.0
     */
    public static String elasticSearchRemoveBoost(@NonNull String conditionStr) {
        return StringUtils.replace(conditionStr, "\"boost\" : 1.0", "\"boost\" : 0.0");
    }

    /**
     * 数据库条件包装
     *
     * @param dataSource    support mysql, elasticsearch
     * @param conditionList {@link DataBaseConditionRequest}
     * @param formula       like 1 and 2 or 3
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList, @NonNull String formula) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        Map<Integer, String> snippetConditionMap = databaseSnippetConditionWrapper(dataSource, conditionList);
        return databaseCombineConditionWrapper(dataSource, formula, snippetConditionMap);
    }

    /**
     * 数据库条件片段包装
     *
     * @param dataSource    support mysql, elasticsearch
     * @param conditionList {@link  }
     * @return db like 1->"companyId=1",2->"name like data" memory like 1->true, 2->false
     */
    public static Map<Integer, String> databaseSnippetConditionWrapper(@NonNull final DataSource dataSource, @NonNull final List<DataBaseConditionRequest> conditionList) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        checkArgument(CollectionUtils.isNotEmpty(conditionList));
        final Map<Integer, String> formulaMap = Maps.newLinkedHashMapWithExpectedSize(conditionList.size());
        final Map<Operator, ConditionWrapperHandle> handleMap = conditionWrapperHandleMap.get(dataSource);
        int[] idx = {1};
        conditionList.forEach(i -> formulaMap.put(idx[0]++, String.valueOf(singleConditionWrapper(handleMap, i, dataSource))));
        return formulaMap;
    }

    /**
     * 数据库条件合并包装，条件拼接符默认为and
     *
     * @param dataSource          support mysql, elasticsearch
     * @param snippetConditionMap like 1->"companyId=1",2->"name like data"
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseCombineConditionWrapper(@NonNull final DataSource dataSource, Map<Integer, String> snippetConditionMap) {
        return databaseCombineConditionWrapper(dataSource, snippetConditionMap,"and");
    }

    /**
     * 数据库条件合并包装，条件拼接符默认为and
     *
     * @param dataSource          support mysql, elasticsearch
     * @param snippetConditionMap like 1->"companyId=1",2->"name like data"
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseCombineConditionWrapper(@NonNull final DataSource dataSource, Map<Integer, String> snippetConditionMap, String joinSign) {
        String formula = generateFullAndFormula(snippetConditionMap.size(), StringUtils.isEmpty(joinSign) ? "and" : joinSign);
        return databaseCombineConditionWrapper(dataSource, formula, snippetConditionMap);
    }

    /**
     * 数据库条件合并包装
     *
     * @param dataSource          support mysql, elasticsearch
     * @param formula             like 1 and 2 or 3
     * @param snippetConditionMap like 1->"companyId=1",2->"name like data"
     * @return like (companyId=1) and (name like data)
     */
    public static String databaseCombineConditionWrapper(@NonNull final DataSource dataSource, @NonNull String formula, Map<Integer, String> snippetConditionMap) {
        checkArgument(!dataSource.equals(DataSource.JAVABEAN));
        if (1 == snippetConditionMap.size()) {
            return snippetConditionMap.get(1);
        }
        FormulaUtil evaluator = new FormulaUtil(dataSource);
        return evaluator.doIt(evaluator, formula, snippetConditionMap);
    }

    /**
     * @param tableName table name
     * @param condition like name = 'zs'
     * @param orders    like name asc, age desc
     * @return whole mysql query
     */
    public static String mysqlQueryWrapper(@NonNull String condition, @NonNull String tableName, String orders, int pageNum, int pageSize) {
        String query = "SELECT %s FROM %s WHERE 1=1 AND %s limit %s, %s";
        return String.format(query, "id", tableName, condition, pageNum - 1, pageSize);
    }

    /**
     * @param tableName table name
     * @param condition like name = 'zs'
     * @return whole mysql query
     */
    public static String mysqlQueryCountWrapper(@NonNull String tableName, @NonNull String condition) {
        String query = "SELECT %s FROM %s WHERE 1=1 AND %s";
        return String.format(query, "COUNT(*)", tableName, condition);
    }

    /**
     * 表达式验证
     *
     * @param formula like 1 and 2 or 3
     * @param number  like 3
     * @return true if the execute should be success; otherwise false
     */
    public static boolean checkFormula(@NonNull String formula, final int number) {
        checkArgument(number > 0);
        Map<Integer, Boolean> numberMap = Maps.newLinkedHashMapWithExpectedSize(number);
        for (int i = 1; i < number + 1; i++) {
            numberMap.put(i, true);
        }
        return checkFormula(formula, numberMap);
    }

    /**
     * input a greater than 0 number then return 1 and 2 ... and a string
     *
     * @param number   a greater than 0 number
     * @param joinSign and or
     * @return 1 and 2 ... and a
     */
    public static String generateFullAndFormula(final int number, @NonNull final String joinSign) {
        checkArgument(number > 0);
        checkArgument("and".equals(joinSign) || "or".equals(joinSign));
        if (1 == number) {
            return "1";
        }
        StringJoiner sj = new StringJoiner(" " + joinSign + " ");
        for (int i = 1; i < number + 1; i++) {
            sj.add(String.valueOf(i));
        }
        return sj.toString();
    }

    /**
     * 表达式验证
     *
     * @param formula    like 1 and 2 or 3
     * @param formulaMap like 1->TRUE,2->FALSE
     * @return true if the execute should be success; otherwise false
     */
    private static boolean checkFormula(@NonNull String formula, Map<Integer, Boolean> formulaMap) {
        String formatFormula = handleFormulaBeforeCheck(formula);
        if (!checkFormulaFormat(formatFormula)) {
            return false;
        }
        formatFormula = checkOrderAndReplaceValue(formatFormula, formulaMap);
        if (formatFormula == null) {
            return false;
        }
        ExpressionParser parser = new SpelExpressionParser();

        try {
            return parser.parseExpression(formatFormula).getValue(Boolean.class);
        } catch (SpelParseException | SpelEvaluationException e) {
            return false;
        }
    }

    /**
     * 校验括号是否成对
     *
     * @param formula like 1 and 2 or 3
     * @return true if the execute should be success; otherwise false
     */
    private static boolean checkBracket(@NonNull String formula) {
        Deque<Character> stack = new LinkedList<>();

        char[] array = formula.toCharArray();
        for (char c : array) {
            switch (c) {
                case '(':
                    stack.push(c);
                    break;
                case ')':
                    if (CollectionUtils.isNotEmpty(stack) && stack.peek() == '(')
                        stack.pop();
                    else
                        return false;
                    break;
                default:
                    break;
            }
        }
        return CollectionUtils.isEmpty(stack);
    }

    /**
     * 校验序号是否在并且替换
     *
     * @param formula    like 1 and 2 or 3
     * @param formulaMap like 1->TRUE,2->FALSE
     * @return like 1 and 2 or 3
     */
    private static String checkOrderAndReplaceValue(@NonNull String formula, @NonNull Map<Integer, Boolean> formulaMap) {
        Pattern regex = Pattern.compile("[1-9]\\d*");
        Matcher matcher = regex.matcher(formula);
        StringBuffer sb = new StringBuffer();

        Integer order;
        while (matcher.find()) {
            order = Integer.valueOf(matcher.group(0));
            if (!formulaMap.containsKey(order)) {
                return null;
            }
            matcher.appendReplacement(sb, String.valueOf(formulaMap.get(order)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    private static boolean checkFormulaFormat(@NonNull String formula) {
        return checkBracket(formula) && formula.matches("\\s*\\(*\\s*([1-9]\\d*)(\\s+([Aa][Nn][Dd]|[Oo][Rr])\\s+\\(*\\s*([1-9]\\d*)\\s*\\)*)*\\s*\\)*\\s*");
    }

    private static String handleFormulaBeforeCheck(@NonNull String formula) {
        return formula.replace('（', '(')
                .replace('）', ')');
    }

    private static String addBracket(@NonNull final DataSource dataSource, String condition){
        if(dataSource.equals(DataSource.ELASTICSEARCH)){
//            return boolQuery().must(condition).toString();
            return condition;
        }
        if(dataSource.equals(DataSource.MYSQL)){
            return String.format("(%s)", condition);
        }
        return null;
    }

}
