package com.unisound.iot.dao.mapper.source1.item;

import com.unisound.iot.common.modle.DwhItem;
import com.unisound.iot.common.modle.Item;
import com.unisound.iot.common.modle.ItemDate;
import com.unisound.iot.common.vo.ItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Admin on 2018/9/30.
 */
@Mapper
public interface ItemDao {

    /**
     * 查询组件json结构
     * @param itemVo
     * @return
     */
    Item findItemDetail( ItemVo itemVo);

    /**
     * 创建组件
     * @param item
     */
    Long addItem(Item item);

    /**
     * 验证组件名称是否冲突
     * @param itemVo
     * @return
     */
    Integer checkItemNameUnique(ItemVo itemVo);

    /**
     * 记录组件资源子表
     * @param itemList
     */
    void addItemDateList(List<ItemDate> itemList);

    /**
     * 查询组件资源子表
     * @param itemVo
     * @return
     */
    List<ItemDate> findItemDataList(ItemVo itemVo);

    /**
     * 查询组件资料列表的数据类型
     * @param itemId
     * @return
     */
    List< Integer > findItemDataType(@Param("itemId") Long itemId);

    /**
     * 查询网页URL
     * @param itemId
     * @return
     */
    String findAlbumWebUrlByItemId(@Param("itemId")Long itemId);

    /**
     * 查询专辑合集/专辑/作品 的主键列表
     * @param itemId
     * @return
     */
    List<Long> findAlbumIdListByItemId(@Param("itemId")Long itemId);

    /**
     * 查询自建组件列表
     * @param itemVo
     * @return
     */
    List<Item> findItemList(ItemVo itemVo);

    /**
     * 更新组件信息
     * @param item
     */
    void updateItem(Item item);

    /**
     * 批量更新组件资源
     * @param updateList
     */
    void updateBatchItemData(List<ItemDate> updateList);

    /**
     * 批量删除组件资源
     * @param deleteList
     */
    void deleteBatchItemDate(List<ItemDate> deleteList);

    /**
     * 批量创建组件资源
     * @param addList
     */
    void addBatchItemDate(List<ItemDate> addList);

    /**
     * 删除自建组件
     * @param itemVo

     */
    void deleteSelfItem( ItemVo itemVo );

    /**
     * 删除云端组件
     */
    void deleteDwhItem( ItemVo itemVo );

    /**
     * 删除自建组件和自建模板关系

     */
    void deleteItemTemplate(ItemVo itemVo);

    /**
     * 删除组件原有资源
     * @param itemDataIdList

     */
    void deleteItemDataByItemId(@Param( "list" ) List< Long > itemDataIdList ,@Param( "updaterId" ) Long updaterId);

    /**
     * 删除云端组件和自建模板关系
     * @param itemVo
     */
    void deleteDwhItemTemplate(ItemVo itemVo);

    /**
     * 查询组件信息映射为云端组件
     * @param itemId
     * @return
     */
    DwhItem findDwhItemById( @Param( "itemId" ) Long itemId );

    /**
     * 查询组件基本信息
     * @param itemId
     * @return
     */
    Item findItemById( @Param( "itemId" ) Long itemId);
}
