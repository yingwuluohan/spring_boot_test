package com.unisound.iot.dao.mapper.source1.dwhitem;

import com.unisound.iot.common.modle.AuthItemTemplate;
import com.unisound.iot.common.modle.DwhItem;
import com.unisound.iot.common.vo.ItemVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
@Mapper
public interface DwhItemDao {

    /**
     * 云端自建组件查询
     * @param itemVo
     * @return
     */
    List<DwhItem> findDwhItemSelfList(ItemVo itemVo);

    /**
     * 全部公开的云端组件
     * @param itemVo
     * @return
     */
    List<DwhItem> findPublicDwhItemList(ItemVo itemVo);

    /**
     * 创建云端组件 或 模板
     * @param dwhItem
     */
    void addDwhItem(DwhItem dwhItem);

    /**
     * 查询组件库是否在云端组件库存在
     * @param authItemTemplate
     * @return
     */
    Integer findDwhItemExist(AuthItemTemplate authItemTemplate);

    /**
     * 更新云端组件模板库
     * @param authItemTemplate
     */
    void updateDwhItem(AuthItemTemplate authItemTemplate);
}
