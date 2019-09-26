package com.unisound.iot.controller.jdk.threadlocal;

import lombok.Data;

/**
 * @Created by yingwuluohan on 2019/9/21.
 */
@Data
public class Info {

    private Integer id;
    private String name;
    private String code;

    public Info(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
}
