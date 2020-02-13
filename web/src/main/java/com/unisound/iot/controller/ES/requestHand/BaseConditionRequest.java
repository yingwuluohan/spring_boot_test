package com.unisound.iot.controller.ES.requestHand;

import com.google.common.collect.Lists;
import com.unisound.iot.controller.ES.ValueType;
import com.unisound.iot.controller.ES.enummodle.Operator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class BaseConditionRequest {
    private String fieldName;
    private ValueType valueType;
    private Operator operator;
    private Object value;
    private Boolean result;
    private List<BaseConditionRequest> attachmentList;
    private String nestedPath;
    private Integer referGroupId;
    private Integer fieldId;
    private Function interceptor;

    protected BaseConditionRequest(String fieldName, ValueType valueType, Operator operator, Object value) {
        this.fieldName = fieldName;
        this.valueType = valueType;
        this.operator = operator;
        this.value = value;
    }

    protected BaseConditionRequest(String fieldName, ValueType valueType, Operator operator, Object value, String nestedPath, Integer referGroupId, Integer fieldId) {
        this.fieldName = fieldName;
        this.valueType = valueType;
        this.operator = operator;
        this.value = value;
        this.nestedPath = nestedPath;
        this.referGroupId = referGroupId;
        this.fieldId = fieldId;
    }

    protected BaseConditionRequest(Boolean result) {
        this.result = result;
    }

    public String getValueStr() {
        return String.valueOf(this.value).trim();
    }

    public List<String> getValueArray() {
        if (null == this.value) {
            return Collections.emptyList();
        } else if (this.value instanceof List) {
            return (List)((List)this.value).stream().map(String::valueOf).collect(Collectors.toList());
        } else if (this.value instanceof Set) {
            return (List)((Set)this.value).stream().map(String::valueOf).collect(Collectors.toList());
        } else if (this.value instanceof Object[]) {
            return (List) Arrays.stream((Object[])((Object[])this.value)).map(String::valueOf).collect(Collectors.toList());
        } else if (this.value instanceof String) {
            return Lists.newArrayList(String.valueOf(this.value).split(","));
        } else {
            throw new AssertionError();
        }
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public ValueType getValueType() {
        return this.valueType;
    }

    public Operator getOperator() {
        return this.operator;
    }

    public Object getValue() {
        return this.value;
    }

    public Boolean getResult() {
        return this.result;
    }

    public List<BaseConditionRequest> getAttachmentList() {
        return this.attachmentList;
    }

    public String getNestedPath() {
        return this.nestedPath;
    }

    public Integer getReferGroupId() {
        return this.referGroupId;
    }

    public Integer getFieldId() {
        return this.fieldId;
    }

    public Function getInterceptor() {
        return this.interceptor;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public void setAttachmentList(List<BaseConditionRequest> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public void setNestedPath(String nestedPath) {
        this.nestedPath = nestedPath;
    }

    public void setReferGroupId(Integer referGroupId) {
        this.referGroupId = referGroupId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public void setInterceptor(Function interceptor) {
        this.interceptor = interceptor;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseConditionRequest)) {
            return false;
        } else {
            BaseConditionRequest other = (BaseConditionRequest)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$fieldName = this.getFieldName();
                Object other$fieldName = other.getFieldName();
                if (this$fieldName == null) {
                    if (other$fieldName != null) {
                        return false;
                    }
                } else if (!this$fieldName.equals(other$fieldName)) {
                    return false;
                }

                Object this$valueType = this.getValueType();
                Object other$valueType = other.getValueType();
                if (this$valueType == null) {
                    if (other$valueType != null) {
                        return false;
                    }
                } else if (!this$valueType.equals(other$valueType)) {
                    return false;
                }

                Object this$operator = this.getOperator();
                Object other$operator = other.getOperator();
                if (this$operator == null) {
                    if (other$operator != null) {
                        return false;
                    }
                } else if (!this$operator.equals(other$operator)) {
                    return false;
                }

                label110: {
                    Object this$value = this.getValue();
                    Object other$value = other.getValue();
                    if (this$value == null) {
                        if (other$value == null) {
                            break label110;
                        }
                    } else if (this$value.equals(other$value)) {
                        break label110;
                    }

                    return false;
                }

                label103: {
                    Object this$result = this.getResult();
                    Object other$result = other.getResult();
                    if (this$result == null) {
                        if (other$result == null) {
                            break label103;
                        }
                    } else if (this$result.equals(other$result)) {
                        break label103;
                    }

                    return false;
                }

                Object this$attachmentList = this.getAttachmentList();
                Object other$attachmentList = other.getAttachmentList();
                if (this$attachmentList == null) {
                    if (other$attachmentList != null) {
                        return false;
                    }
                } else if (!this$attachmentList.equals(other$attachmentList)) {
                    return false;
                }

                label89: {
                    Object this$nestedPath = this.getNestedPath();
                    Object other$nestedPath = other.getNestedPath();
                    if (this$nestedPath == null) {
                        if (other$nestedPath == null) {
                            break label89;
                        }
                    } else if (this$nestedPath.equals(other$nestedPath)) {
                        break label89;
                    }

                    return false;
                }

                label82: {
                    Object this$referGroupId = this.getReferGroupId();
                    Object other$referGroupId = other.getReferGroupId();
                    if (this$referGroupId == null) {
                        if (other$referGroupId == null) {
                            break label82;
                        }
                    } else if (this$referGroupId.equals(other$referGroupId)) {
                        break label82;
                    }

                    return false;
                }

                Object this$fieldId = this.getFieldId();
                Object other$fieldId = other.getFieldId();
                if (this$fieldId == null) {
                    if (other$fieldId != null) {
                        return false;
                    }
                } else if (!this$fieldId.equals(other$fieldId)) {
                    return false;
                }

                Object this$interceptor = this.getInterceptor();
                Object other$interceptor = other.getInterceptor();
                if (this$interceptor == null) {
                    if (other$interceptor != null) {
                        return false;
                    }
                } else if (!this$interceptor.equals(other$interceptor)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseConditionRequest;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $fieldName = this.getFieldName();
        result = result * 59 + ($fieldName == null ? 43 : $fieldName.hashCode());
        Object $valueType = this.getValueType();
        result = result * 59 + ($valueType == null ? 43 : $valueType.hashCode());
        Object $operator = this.getOperator();
        result = result * 59 + ($operator == null ? 43 : $operator.hashCode());
        Object $value = this.getValue();
        result = result * 59 + ($value == null ? 43 : $value.hashCode());
        Object $result = this.getResult();
        result = result * 59 + ($result == null ? 43 : $result.hashCode());
        Object $attachmentList = this.getAttachmentList();
        result = result * 59 + ($attachmentList == null ? 43 : $attachmentList.hashCode());
        Object $nestedPath = this.getNestedPath();
        result = result * 59 + ($nestedPath == null ? 43 : $nestedPath.hashCode());
        Object $referGroupId = this.getReferGroupId();
        result = result * 59 + ($referGroupId == null ? 43 : $referGroupId.hashCode());
        Object $fieldId = this.getFieldId();
        result = result * 59 + ($fieldId == null ? 43 : $fieldId.hashCode());
        Object $interceptor = this.getInterceptor();
        result = result * 59 + ($interceptor == null ? 43 : $interceptor.hashCode());
        return result;
    }

    public String toString() {
        return "BaseConditionRequest(fieldName=" + this.getFieldName() + ", valueType=" + this.getValueType() + ", operator=" + this.getOperator() + ", value=" + this.getValue() + ", result=" + this.getResult() + ", attachmentList=" + this.getAttachmentList() + ", nestedPath=" + this.getNestedPath() + ", referGroupId=" + this.getReferGroupId() + ", fieldId=" + this.getFieldId() + ", interceptor=" + this.getInterceptor() + ")";
    }
}
