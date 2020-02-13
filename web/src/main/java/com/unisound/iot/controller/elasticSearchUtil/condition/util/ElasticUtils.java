package com.unisound.iot.controller.elasticSearchUtil.condition.util;

import com.google.common.collect.ImmutableMap;
import com.unisound.iot.controller.ES.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.util.Map;

import static org.elasticsearch.script.Script.DEFAULT_SCRIPT_LANG;

@Slf4j
public class ElasticUtils {

    private ElasticUtils() {}

    /**
     * script
     * @param expression 表达式
     * @param params 参数
     * @return {@link Script}
     */
    public static Script script(String expression, Map<String, Object> params) {
        return new Script(ScriptType.INLINE, DEFAULT_SCRIPT_LANG, expression, params);
    }

    /**
     * 早于时分script
     * @param time 时分, hour: time[0], minute: time[1]
     * @param fieldName field
     * @return {@link Script}
     */
    public static Script beforeHourMinuteScript(int[] time, String fieldName) {
        Map<String, Object> params = ImmutableMap.of(DateUtils.HOUR_VAR, time[0], DateUtils.MINUTE_VAR, time[1]);
        String conditionTemplate = "if (!doc['%s'].empty && ((params.%s > doc['%s'].date.hourOfDay) || (params.%s == doc['%s'].date.hourOfDay && params.%s > doc['%s'].date.minuteOfHour))) {return true;}";
        String expression = String.format(conditionTemplate, fieldName, DateUtils.HOUR_VAR, fieldName, DateUtils.HOUR_VAR, fieldName, DateUtils.MINUTE_VAR, fieldName);
        return new Script(ScriptType.INLINE, DEFAULT_SCRIPT_LANG, expression,params);
    }

    /**
     * 晚于时分script
     * @param time 时分, hour: time[0], minute: time[1]
     * @param fieldName field
     * @return {@link Script}
     */
    public static Script afterHourMinuteScript(int[] time, String fieldName) {
        Map<String, Object> params = ImmutableMap.of(DateUtils.HOUR_VAR, time[0], DateUtils.MINUTE_VAR, time[1]);
        String conditionTemplate = "if ((params.%s < doc['%s'].date.hourOfDay) || (params.%s == doc['%s'].date.hourOfDay && params.%s < doc['%s'].date.minuteOfHour)) {return true;}";
        String expression = String.format(conditionTemplate, DateUtils.HOUR_VAR, fieldName, DateUtils.HOUR_VAR, fieldName, DateUtils.MINUTE_VAR, fieldName);
        return new Script(ScriptType.INLINE, DEFAULT_SCRIPT_LANG, expression,params);
    }

}
