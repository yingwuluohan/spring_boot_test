package com.unisound.iot.dao.mapper.source1.release;

import com.unisound.iot.common.modle.AuthItemTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Admin on 2018/10/9.
 */
@Mapper
public interface ReleaseDao {

    /**
     * 发布组件 / 模板
     * @param authItemTemplate
     */
    void addAuthItemTemplate(AuthItemTemplate authItemTemplate);

    /**
     * 变更组件和模板审核状态
     * @param authItemTemplate
     */
    void updateCheckStatus(AuthItemTemplate authItemTemplate);

    void updateAuthItemTemplate(AuthItemTemplate authItemTemplate);
}
