package com.unisound.iot.common.modle.kafka;

import lombok.Data;

import java.util.Date;

/**
 * @Created by yingwuluohan on 2019/4/9.
 * @Company 北京云知声技术有限公司
 */
@Data
public class Message {
    private Long id;    //id
    private String msg; //消息
    private Date sendTime;  //时间戳
}
