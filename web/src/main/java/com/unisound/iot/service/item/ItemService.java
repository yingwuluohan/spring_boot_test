package com.unisound.iot.service.item;

import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.exception.BusinessException;
import com.unisound.iot.common.modle.DwhItem;
import com.unisound.iot.common.modle.Item;
import com.unisound.iot.common.modle.ItemDate;
import com.unisound.iot.common.vo.ItemVo;
import com.unisound.iot.service.base.BaseService;
import com.unisound.iot.service.report.ReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.unisound.iot.dao.mapper.source1.item.*;
/**
 * 组件服务
 * Created by Admin on 2018/9/30.
 */
@Service
public class ItemService extends BaseService{

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(ItemService.class);

    @Autowired
    private ItemDao itemDao;
    @Autowired
    private ReportService reportService;

    /**
     * 查询组件及资源详情
     * @param itemVo
     * @return
     */
    public Item findItemDetail(ItemVo itemVo ){
        Item item = itemDao.findItemDetail( itemVo );
        if (null != item) {
            List< Integer> dataTypeList = itemDao.findItemDataType(item.getItemId());
            Integer dataType = dataTypeList != null ? dataTypeList.get( 0 ) : 0;
            item.setDataType( dataType );
            if (null != dataType && 4 == dataType) {//网页类型
                String webTypeUrl = itemDao.findAlbumWebUrlByItemId( item.getItemId() );
                item.setWebUrl(webTypeUrl);
                return item;
            } else if (null != dataType && 4 != dataType) {
                //查询专辑合集/专辑/作品 / 专题 的主键列表
                List<Long> albumIdList = itemDao.findAlbumIdListByItemId(item.getItemId());
                if (null != albumIdList) {
                    item = (Item) getDataApiResult(albumIdList, item);
                }
            }
        }
//        return appBanner;
//        //查询组件资源表
//        List< ItemDate> itemDateList = itemDao.findItemDataList( itemVo );
        return item ;
    }

    /**
     * 创建组件
     * @param item
     */
   // @Transactional
    public Item addItem(Item item) {
        try{
            //创建组件
            itemDao.addItem( item );
            reportService.findAllAppkey();
            String str = null;
            str.length();
            //记录组件资源
            List<ItemDate > itemList = item.getList();
            if(CollectionUtils.isNotEmpty( itemList )){
//                itemList.forEach( ( ItemDate itemDate) -> itemDate.setItemId( item.getItemId() ) );
                for( int i = 0 ;i < itemList.size();i++ ){
                    itemList.get( i ).setSortNum( i + 1 );
                    itemList.get( i ).setItemId( item.getItemId() );
                    itemList.get( i ).setCreator( item.getCreator() );
                }
            itemDao.addItemDateList( itemList );
            }
        }catch (BusinessException e ){
            e.printStackTrace();
            throw new BusinessException( "创建组件异常" +e.getMessage() , e );
        }
        return item;
    }

    /**
     * 验证组件名称是否冲突
     * @param itemVo
     */
    public boolean checkItemNameUnique( ItemVo itemVo ) {
        Integer exist = itemDao.checkItemNameUnique( itemVo );
        if( null == exist || exist.intValue() == 0  ){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查询自建组件列表
     * @param itemVo
     * @return
     */
    public List<Item> findItemList(ItemVo itemVo) {
        List< Item > itemList = itemDao.findItemList( itemVo );
        return itemList;
    }

    /**
     * 更新组件
     * @param item
     */
    @Transactional
    public void updateItem(Item item) {
        log.info( "更新组件" );
        try{

            itemDao.updateItem( item );
            updateItemDataList( item );

        }catch ( BusinessException e ){
            throw new BusinessException( "更新组件异常,组件id:" + item.getItemId() + ","  + e.getMessage() ,e );
        }

    }

    /**
     * 更新组件资源
     * @param item
     * @throws Exception
     */
    public void updateItemDataList( Item item ){
        Integer dataType = item.getDataType();
        List<Long > albumIdList = item.getLongList();
        if(CollectionUtils.isNotEmpty( albumIdList )){
            List< ItemDate > itemDateList = new ArrayList<>();
            for( int i =0 ; i < albumIdList.size();i++ ){
                ItemDate itemDate = new ItemDate();
                itemDate.setCreator( item.getUpdater() );
                itemDate.setAlbumId( albumIdList.get( i ) );
                itemDate.setItemId( item.getItemId() );
                itemDate.setDataType( item.getDataType() );
                itemDate.setSortNum( albumIdList.size() - i );
                itemDateList.add( itemDate );
            }
            //删除组件原有资源
            deleteItemDate( item.getItemDataIdList() , item.getUpdaterId() );
            //新增变更资源
            itemDao.addItemDateList( itemDateList );
        }
    }

    /**
     *  删除组件原有资源
     * @param itemDataIdList  组件资源关系的主键

     */
    public void deleteItemDate( List< Long > itemDataIdList , Long updaterId  ){
        itemDao.deleteItemDataByItemId( itemDataIdList ,updaterId );
    }


    /**
     * 批量创建组件资源
     */
    public void addBatchItemDate(List<ItemDate> addList , Long itemId ){
        try{
            itemDao.addBatchItemDate( addList );
        }catch (BusinessException e ){
            throw new BusinessException( "批量创建组件资源异常 ,组件id：" + itemId + "," + e.getMessage() ,e );
        }
    }

    /**
     * 批量更新组件资源
     * @param updateList
     * @param itemId
     */
    public void updateBatchItemDate(List<ItemDate> updateList , Long itemId ){
        try{
            itemDao.updateBatchItemData( updateList );
        }catch (BusinessException e ){
            throw new BusinessException( "批量更新组件资源异常 ,组件id：" + itemId + "," + e.getMessage() ,e );
        }
    }

    /**
     * 批量删除组件资源
     * @param deleteList
     * @param itemId
     */
    public void deleteBatchItemDate(List<ItemDate> deleteList , Long itemId ){
        try{
            itemDao.deleteBatchItemDate( deleteList );
        }catch (BusinessException e ){
            throw new BusinessException( "批量删除组件资源异常 ,组件id：" + itemId + "," + e.getMessage() ,e );
        }
    }

    /**
     * 删除组件
     * @param itemVo

     *
     * 删除自建组件：:1：需要删除自建组件，2：删除自建组件和自建模板关系 ，
     * 删除云端组件：1：需删除云端组件，2：删除自建模板和云端组件关系，3：
     */
    @Transactional
    public void deleteItem(ItemVo itemVo ) {
        //如果是非云知声合作商操作 ，需要 保证操作的domainId 与当前的组件所属的domainId相等 ; 云知声操作无限制
        if( itemVo.getOperDomainId().longValue() == CommonConstants.YZS_DOMAIN_ID ){
            itemVo.setDomainId( null );
        }
        //删除自建
        if( 1 == itemVo.getItemCreateType().intValue() ){
            itemDao.deleteSelfItem( itemVo );
        }//删除云端
         else if( 2 == itemVo.getItemCreateType().intValue() ){
            itemDao.deleteDwhItem( itemVo );
        }
        //删除自建组件/云端组件 和自建模板关系
        itemDao.deleteItemTemplate( itemVo );

    }

    /**
     * 获取上层父公司domainId
     * @param loginDomainId
     * @return
     */
    public Long getParentDomainId(Long loginDomainId) {
        Long parentDomainId = null;
        //TODO 远程接口

        return parentDomainId;
    }

    /**
     * 获取当前公司所有产品线
     * @param loginDomainId
     * @return
     */
    public List<String> getProductTypeList(Long loginDomainId) {
        List<String > productList = new ArrayList<>();
        //TODO 远程接口


        return productList;
    }

    /**
     * 获取当前公司的所有appkey
     * @param loginDomainId
     * @return
     */
    public List<String> findDomainAppkeyList(Long loginDomainId) {
        List<String > domainAppkeyList = new ArrayList<>();
        //TODO 远程接口


        return domainAppkeyList;
    }

    /**
     * 根据主键查询组件的基本信息
     * @param itemId
     * @return
     */
    public DwhItem findDwhItemById(Long itemId) {
        return itemDao.findDwhItemById( itemId );
    }
    public Item findItemById(Long itemId) {
        return itemDao.findItemById( itemId );
    }
}
