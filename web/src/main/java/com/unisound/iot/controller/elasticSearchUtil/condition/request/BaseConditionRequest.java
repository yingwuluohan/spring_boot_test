package com.unisound.iot.controller.elasticSearchUtil.condition.request;

import com.google.common.collect.Lists;
import com.unisound.iot.controller.elasticSearchUtil.condition.Operator.Operator;
import com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public abstract class BaseConditionRequest {

    private String fieldName;
    private ValueType valueType;
    private Operator operator;
    private Object value;

    private Boolean result;
    /**
     * 附属条件，暂时支持and
     */
    private List<BaseConditionRequest> attachmentList;

    /**
     * 字段组嵌套查询参数
     */
    private String nestedPath;
    /**
     * 字段组嵌套查询参数
     */
    private Integer referGroupId;
    /**
     * 字段组嵌套查询参数
     */
    private Integer fieldId;

    /**
     * 拦截器
     */
    private Function interceptor;

    protected BaseConditionRequest(String fieldName, ValueType valueType, Operator operator, Object value){
        this.fieldName = fieldName;
        this.valueType = valueType;
        this.operator = operator;
        this.value = value;
    }

    protected BaseConditionRequest(String fieldName, ValueType valueType, Operator operator, Object value,
                                   String nestedPath, Integer referGroupId, Integer fieldId){
        this.fieldName = fieldName;
        this.valueType = valueType;
        this.operator = operator;
        this.value = value;
        this.nestedPath = nestedPath;
        this.referGroupId = referGroupId;
        this.fieldId = fieldId;
    }

    protected BaseConditionRequest(Boolean result){
        this.result = result;
    }

    public String getValueStr() {
        return String.valueOf(value).trim();
    }

    @SuppressWarnings("unchecked")
    public List<String> getValueArray() {
//        return null == value ? new String[]{} : String.valueOf(value).split(",");
        if (null == value) {
            return Collections.emptyList();
        }
        if (value instanceof List) {
            return ((List<?>) value).stream().map(String::valueOf).collect(Collectors.toList());
        }
        if (value instanceof Set) {
            return ((Set<?>) value).stream().map(String::valueOf).collect(Collectors.toList());
        }
        if (value instanceof Object[]) {
            return Arrays.stream((Object[])value).map(String::valueOf).collect(Collectors.toList());
        }
        if (value instanceof String) {
            return Lists.newArrayList(String.valueOf(value).split(","));
        }
        throw new AssertionError();
    }
}
