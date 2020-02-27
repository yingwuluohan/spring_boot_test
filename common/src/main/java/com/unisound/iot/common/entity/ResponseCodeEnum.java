package com.unisound.iot.common.entity;

/**
 * @Created by yingwuluohan on 2018/10/6.
 * @Company fn
 */
public class ResponseCodeEnum {

    public final static String success_code="200";
    public final static String fail_code="300";
    /**
     * 组件码
     */
    private static Integer item_code = 1000;
    /**
     * 模板码
     */
    private static Integer template_code = 2000;
    /**
     * 消息码
     */
    private static Integer message_code = 3000;
    /**
     * 日志码
     */
    private static Integer log_code = 4000;

    private static String add;
    private static String update;
    private static String del;
    private static String oth;

    public enum itemCode {
        addItemFail( 100100 , "创建组件异常"),
        addItemSuccess( 100101 , "创建组件成功"),
        updateItem( 100102  , "更新组件异常"),
        delItem(100103 , "删除组件异常"),
        not_unique_name(100110 ,"名称不可用"),
        other(100000  , "其它");

        private int index;
        private String value;

        itemCode(int index, String value) {
            this.index = index;
            this.value = value;
        }

        public static ResponseCodeEnum.itemCode getSource(int index) {
            for (ResponseCodeEnum.itemCode p : ResponseCodeEnum.itemCode.values()) {
                if (p.getIndex() == index  ) {
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
