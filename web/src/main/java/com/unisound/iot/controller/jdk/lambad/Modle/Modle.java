package com.unisound.iot.controller.jdk.lambad.Modle;

import lombok.Data;

import java.io.Serializable;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company 北京云知声技术有限公司
 */
@Data
public class Modle implements Serializable {

    private String name;

    private Integer id;

    public Modle(String name) {
        this.name = name;
    }
}
