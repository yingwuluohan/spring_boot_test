package com.unisound.iot.common.entity;

/**
 * Created by Admin on 2018/9/30.
 */
public class ResponseStatusEnum {


    public enum findResultEnum {
        success(200, "成功"),
        error(500, "系统异常"),
        fail_100(100, "业务操作失败"),
        other(4, "其它");

        private int index;
        private String value;

        findResultEnum(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public static findResultEnum getSource(int index) {
            for (findResultEnum p : findResultEnum.values()) {
                if (p.getIndex() == index) {
                    return p;
                }
            }
            return null;
        }

        /**
         * 获取index
         *
         * @return index int
         */
        public int getIndex() {
            return index;
        }

        /**
         * 设置index
         *
         * @param index int
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * 获取value
         *
         * @return value String
         */
        public String getValue() {
            if (null == value) {
                value = "其它";
            }
            return value;
        }

        /**
         * 设置value
         *
         * @param value String
         */
        public void setValue(String value) {
            this.value = value;
        }
    }


}
