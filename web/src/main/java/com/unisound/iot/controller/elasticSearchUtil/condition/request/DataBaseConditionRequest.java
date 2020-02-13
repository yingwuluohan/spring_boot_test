package com.unisound.iot.controller.elasticSearchUtil.condition.request;

import com.unisound.iot.controller.elasticSearchUtil.condition.Operator.Operator;
import com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataBaseConditionRequest extends BaseConditionRequest {

    /**
     * mysql使用
     */
    private String alias;

    private DataBaseConditionRequest(String alias, String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
        this.alias = alias;
    }

    private DataBaseConditionRequest(String alias, String fieldName, ValueType valueType, Operator operator, Object value,
                                     String nestedPath, Integer referGroupId, Integer fieldId) {
        super(fieldName, valueType, operator, value, nestedPath, referGroupId, fieldId);
        this.alias = alias;
    }

    private DataBaseConditionRequest(Boolean result){
        super(result);
    }

    public static DataBaseConditionRequest of(@NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value, String alias) {
        return new DataBaseConditionRequest(alias, fieldName, valueType, operator, value);
    }

    public static DataBaseConditionRequest of(@NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value, String alias, String nestedPath, Integer referGroupId, Integer fieldId) {
        return new DataBaseConditionRequest(alias, fieldName, valueType, operator, value, nestedPath, referGroupId, fieldId);
    }

    public static DataBaseConditionRequest of(Boolean result){
        return new DataBaseConditionRequest(result);
    }
}