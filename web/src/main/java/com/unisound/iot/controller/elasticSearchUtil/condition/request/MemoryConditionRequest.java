package com.unisound.iot.controller.elasticSearchUtil.condition.request;

import com.unisound.iot.controller.elasticSearchUtil.condition.Operator.Operator;
import com.unisound.iot.controller.elasticSearchUtil.condition.type.ValueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemoryConditionRequest extends BaseConditionRequest {

    private Object memoryObject;
    private Boolean isMap;

    private MemoryConditionRequest(Boolean result){
        super(result);
    }

    private MemoryConditionRequest(Object memoryObject, String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
        this.memoryObject = memoryObject;
    }

    private MemoryConditionRequest(Object memoryObject, String fieldName, ValueType valueType, Operator operator, Object value, Boolean isMap) {
        super(fieldName, valueType, operator, value);
        this.memoryObject = memoryObject;
        this.isMap = isMap;
    }

    public static MemoryConditionRequest of(@NonNull Object memoryObject, @NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value) {
        return new MemoryConditionRequest(memoryObject, fieldName, valueType, operator, value);
    }

    public static MemoryConditionRequest of(@NonNull Object memoryObject, @NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value, Boolean isMap) {
        return new MemoryConditionRequest(memoryObject, fieldName, valueType, operator, value, isMap);
    }

    public static MemoryConditionRequest of(Boolean result){
        return new MemoryConditionRequest(result);
    }
}