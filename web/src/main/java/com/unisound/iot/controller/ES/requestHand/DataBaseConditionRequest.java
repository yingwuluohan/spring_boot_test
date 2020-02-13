package com.unisound.iot.controller.ES.requestHand;

import com.unisound.iot.controller.ES.ValueType;
import com.unisound.iot.controller.ES.enummodle.Operator;
import lombok.NonNull;

public class DataBaseConditionRequest  extends BaseConditionRequest {
    private String alias;

    private DataBaseConditionRequest(String alias, String fieldName, ValueType valueType, Operator operator, Object value) {
        super(fieldName, valueType, operator, value);
        this.alias = alias;
    }

    private DataBaseConditionRequest(String alias, String fieldName, ValueType valueType, Operator operator, Object value, String nestedPath, Integer referGroupId, Integer fieldId) {
        super(fieldName, valueType, operator, value, nestedPath, referGroupId, fieldId);
        this.alias = alias;
    }

    private DataBaseConditionRequest(Boolean result) {
        super(result);
    }

    public static DataBaseConditionRequest of(@NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value, String alias) {
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        } else if (valueType == null) {
            throw new NullPointerException("valueType");
        } else if (operator == null) {
            throw new NullPointerException("operator");
        } else {
            return new DataBaseConditionRequest(alias, fieldName, valueType, operator, value);
        }
    }

    public static DataBaseConditionRequest of(@NonNull String fieldName, @NonNull ValueType valueType, @NonNull Operator operator, Object value, String alias, String nestedPath, Integer referGroupId, Integer fieldId) {
        if (fieldName == null) {
            throw new NullPointerException("fieldName");
        } else if (valueType == null) {
            throw new NullPointerException("valueType");
        } else if (operator == null) {
            throw new NullPointerException("operator");
        } else {
            return new DataBaseConditionRequest(alias, fieldName, valueType, operator, value, nestedPath, referGroupId, fieldId);
        }
    }

    public static DataBaseConditionRequest of(Boolean result) {
        return new DataBaseConditionRequest(result);
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String toString() {
        return "DataBaseConditionRequest(alias=" + this.getAlias() + ")";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof DataBaseConditionRequest)) {
            return false;
        } else {
            DataBaseConditionRequest other = (DataBaseConditionRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$alias = this.getAlias();
                Object other$alias = other.getAlias();
                if (this$alias == null) {
                    if (other$alias != null) {
                        return false;
                    }
                } else if (!this$alias.equals(other$alias)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof DataBaseConditionRequest;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $alias = this.getAlias();
         result = result * 59 + ($alias == null ? 43 : $alias.hashCode());
        return result;
    }
}
