package com.unisound.iot.controller.spring.bean_init;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @Created by yingwuluohan on 2019/7/28.
 * @Company 北京云知声技术有限公司
 */
@Component("testServiceBean")
public class TestFactoryBean implements FactoryBean {

    @Nullable
    @Override
    public Object getObject() throws Exception {
        return new SpringBean() ;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return SpringBean.class;
    }
}
