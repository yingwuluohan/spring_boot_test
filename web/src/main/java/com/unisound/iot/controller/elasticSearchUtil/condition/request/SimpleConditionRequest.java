package com.unisound.iot.controller.elasticSearchUtil.condition.request;

import com.unisound.iot.controller.elasticSearchUtil.condition.Operator.Operator;
import com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class SimpleConditionRequest extends BaseConditionRequest {

    private Object waitCompareValue;

    private SimpleConditionRequest(Object waitCompareValue, @NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value){
        super(fieldName, valueType, operator, value);
        this.waitCompareValue = waitCompareValue;
    }

    private SimpleConditionRequest(Boolean result){
        super(result);
    }

    public static SimpleConditionRequest of(Object waitCompareValue, @NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value) {
        return new SimpleConditionRequest(waitCompareValue, fieldName, valueType, operator, value);
    }

    public static SimpleConditionRequest of(Boolean result){
        return new SimpleConditionRequest(result);
    }
}
