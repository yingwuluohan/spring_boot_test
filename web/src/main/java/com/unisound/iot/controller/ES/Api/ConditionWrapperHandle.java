package com.unisound.iot.controller.ES.Api;


import com.unisound.iot.controller.ES.requestHand.BaseConditionRequest;

public interface ConditionWrapperHandle <T> {
    T exec(BaseConditionRequest var1);
}
