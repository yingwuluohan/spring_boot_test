package com.unisound.iot.controller.elasticSearchUtil.condition.wrapperHandler;


import com.unisound.iot.controller.elasticSearchUtil.condition.request.BaseConditionRequest;

@FunctionalInterface
public interface ConditionWrapperHandle<T> {

    T exec(BaseConditionRequest condition);
}
