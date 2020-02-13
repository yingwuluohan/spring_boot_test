package com.unisound.iot.controller.elasticSearchUtil.condition;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by fanfever on 2017/6/15.
 * Email fanfeveryahoo@gmail.com
 * Url https://github.com/fanfever
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonFormat()
@JsonFilter("dataSyncEsFilter")
public class DataSyncEsDTO  {

    @JsonProperty("company_id")
    private Integer companyId;

    @JsonProperty("create_user_id")
    private Integer createUserId;

    @JsonProperty("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonProperty("update_user_id")
    private Integer updateUserId;

    @JsonProperty("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @JsonProperty("enqueue_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enqueueTime;

    private String remark;

    @JsonProperty("delete_flag")
    private Integer deleteFlag;

    /**
     * 对象ID
     */
    @JsonProperty("custom_object_id")
    private Integer customObjectId;
    @JsonProperty("owner_type")
    private Integer ownerType;
    /**
     * 所有人
     */
    @JsonProperty("owner_id")
    private Integer ownerId;

    /**
     * 所有人岗位ID
     */
    @JsonProperty("owner_position_id")
    private Integer ownerPositionId;

    @JsonProperty("permission_user_id_list")
    private List<Integer> permissionUserIdList;

    @JsonProperty("permission_group_id_list")
    private List<Integer> permissionGroupIdList;

    @JsonProperty("teammember_user_id_list")
    private List<Integer> teammemberUserIdList;

    /**
     * 指定需要序列化的null字段列表
     */
    @JsonIgnore
    private List<String> nullFieldList;

    /**
     * v_1字段固化为对象主字段，在创建对象的同时创建customField并绑定该字段
     */
    private Object v1;
    private Object v2;
    private Object v3;
    private Object v4;
    private Object v5;
    private Object v6;
    private Object v7;
    private Object v8;
    private Object v9;
    private Object v10;
    private Object v11;
    private Object v12;
    private Object v13;
    private Object v14;
    private Object v15;
    private Object v16;
    private Object v17;
    private Object v18;
    private Object v19;
    private Object v20;
    private Object v21;
    private Object v22;
    private Object v23;
    private Object v24;
    private Object v25;
    private Object v26;
    private Object v27;
    private Object v28;
    private Object v29;
    private Object v30;
    private Object v31;
    private Object v32;
    private Object v33;
    private Object v34;
    private Object v35;
    private Object v36;
    private Object v37;
    private Object v38;
    private Object v39;
    private Object v40;
    private Object v41;
    private Object v42;
    private Object v43;
    private Object v44;
    private Object v45;
    private Object v46;
    private Object v47;
    private Object v48;
    private Object v49;
    private Object v50;
    private Object v51;
    private Object v52;
    private Object v53;
    private Object v54;
    private Object v55;
    private Object v56;
    private Object v57;
    private Object v58;
    private Object v59;
    private Object v60;
    /**
     * f_1字段固化为创建用户ID，在创建对象的同时创建customField并绑定该字段
     */
    private BigDecimal f1;
    /**
     * f_2字段固化为更新用户ID，在创建对象的同时创建customField并绑定该字段
     */
    private BigDecimal f2;
    /**
     * f_3字段固化为所有人ID，在创建对象的同时创建customField并绑定该字段
     */
    private BigDecimal f3;
    private BigDecimal f4;
    private BigDecimal f5;
    private BigDecimal f6;
    private BigDecimal f7;
    private BigDecimal f8;
    private BigDecimal f9;
    private BigDecimal f10;
    private BigDecimal f11;
    private BigDecimal f12;
    private BigDecimal f13;
    private BigDecimal f14;
    private BigDecimal f15;
    private BigDecimal f16;
    private BigDecimal f17;
    private BigDecimal f18;
    private BigDecimal f19;
    private BigDecimal f20;
    private BigDecimal f21;
    private BigDecimal f22;
    private BigDecimal f23;
    private BigDecimal f24;
    private BigDecimal f25;
    private BigDecimal f26;
    private BigDecimal f27;
    private BigDecimal f28;
    private BigDecimal f29;
    private BigDecimal f30;
    private BigDecimal f31;
    private BigDecimal f32;
    private BigDecimal f33;
    private BigDecimal f34;
    private BigDecimal f35;
    private BigDecimal f36;
    private BigDecimal f37;
    private BigDecimal f38;
    private BigDecimal f39;
    private BigDecimal f40;
    private BigDecimal f41;
    private BigDecimal f42;
    private BigDecimal f43;
    private BigDecimal f44;
    private BigDecimal f45;
    private BigDecimal f46;
    private BigDecimal f47;
    private BigDecimal f48;
    private BigDecimal f49;
    private BigDecimal f50;
    private BigDecimal f51;
    private BigDecimal f52;
    private BigDecimal f53;
    private BigDecimal f54;
    private BigDecimal f55;
    private BigDecimal f56;
    private BigDecimal f57;
    private BigDecimal f58;
    private BigDecimal f59;
    private BigDecimal f60;
    private BigDecimal f61;
    private BigDecimal f62;
    private BigDecimal f63;
    private BigDecimal f64;
    private BigDecimal f65;
    private BigDecimal f66;
    private BigDecimal f67;
    private BigDecimal f68;
    private BigDecimal f69;
    private BigDecimal f70;
    private BigDecimal f71;
    private BigDecimal f72;
    private BigDecimal f73;
    private BigDecimal f74;
    private BigDecimal f75;
    private BigDecimal f76;
    private BigDecimal f77;
    private BigDecimal f78;
    private BigDecimal f79;
    private BigDecimal f80;
    private BigDecimal f81;
    private BigDecimal f82;
    private BigDecimal f83;
    private BigDecimal f84;
    private BigDecimal f85;
    private BigDecimal f86;
    private BigDecimal f87;
    private BigDecimal f88;
    private BigDecimal f89;
    private BigDecimal f90;
    /**
     * d1字段固化为创建时间，在创建对象的同时创建customField并绑定该字段
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d1;
    /**
     * d2字段固化为更新时间，在创建对象的同时创建customField并绑定该字段
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d2;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d3;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d4;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d5;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d6;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d7;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d8;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d9;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d10;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d11;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d12;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d13;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d14;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d15;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d16;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d17;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d18;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d19;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d20;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d21;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d22;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d23;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d24;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d25;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d26;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d27;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d28;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d29;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime d30;

    private String g1;
    private String g2;
    private String g3;

    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;
    private String c6;
    private String c7;
    private String c8;
    private String c9;
    private String c10;
    private String c11;
    private String c12;
    private String c13;
    private String c14;
    private String c15;
    private String c16;
    private String c17;
    private String c18;
    private String c19;
    private String c20;
    private String c21;
    private String c22;
    private String c23;
    private String c24;
    private String c25;
    private String c26;
    private String c27;
    private String c28;
    private String c29;
    private String c30;
    private String c31;
    private String c32;
    private String c33;
    private String c34;
    private String c35;
    private String c36;
    private String c37;
    private String c38;
    private String c39;
    private String c40;
    private String c41;
    private String c42;
    private String c43;
    private String c44;
    private String c45;
    private String c46;
    private String c47;
    private String c48;
    private String c49;
    private String c50;

    @JsonProperty("look_up_display")
    private List<LookUpDisplay> lookUpDisplay;

    @JsonProperty("field_group_data")
    private List<FieldGroupData> fieldGroupDataList;

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class LookUpDisplay{
        @JsonProperty("field_name")
        private String fieldName;
        private Integer id;
        private String display;
    }

    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class FieldGroupData{
        @JsonProperty("refer_group_id")
        private Integer referGroupId;
        @JsonProperty("field_id")
        private Integer fieldId;
        @JsonProperty("line_number")
        private Integer lineNumber;
        @JsonProperty("field_type")
        private Integer fieldType;
        /**
         * 数字
         */
        private BigDecimal f1;
        /**
         * 日期
         */
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime d1;
        /**
         * 文本
         */
        private Object v1;
        /**
         * 大文本
         */
        private String c1;

    }
}
