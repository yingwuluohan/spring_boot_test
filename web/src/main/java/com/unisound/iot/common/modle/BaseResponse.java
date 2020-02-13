package com.unisound.iot.common.modle;





import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 */

@Data
//@JsonInclude(value = Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

    /**
     * 响应编码
     */

    private int code;
    /**
     * 响应消息
     */

    private String message;
    /**
     * 是否可见
     */

    private boolean visible;


    @AllArgsConstructor
    @Data
    public class Paging {
        private int pageNum;
        private int pageSize;
        private long total;
    }
}
