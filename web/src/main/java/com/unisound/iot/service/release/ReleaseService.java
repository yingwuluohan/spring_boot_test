package com.unisound.iot.service.release;

import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.exception.BusinessException;
import com.unisound.iot.common.modle.AuthItemTemplate;
import com.unisound.iot.common.modle.DwhItem;
import com.unisound.iot.dao.mapper.source1.dwhitem.*;
import com.unisound.iot.dao.mapper.source1.release.*;
import com.unisound.iot.service.base.BaseService;
import com.unisound.iot.service.item.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Admin on 2018/10/9.
 */
@Service
public class ReleaseService extends BaseService {


    private static final Logger log = LogManager.getLogger(ReleaseService.class);

    @Autowired
    private ReleaseDao releaseDao;
    @Autowired
    private DwhItemDao dwhItemDao;
    @Autowired
    private ItemService itemService;



    /**
     * 发布组件 / 模板
     * @param authItemTemplate
     */
    @Transactional
    public void releaseItemTemplate(AuthItemTemplate authItemTemplate) {
        //TODO 调用rest 接口 平台管理中心服务
        log.info( "调用rest 接口 平台管理中心服务" );

        //入库auth_item_template 表
        try{
            if( authItemTemplate.getCreateType().intValue() == CommonConstants.CREATE_TYPE_1 ){
                releaseItem( authItemTemplate );
            }else if( authItemTemplate.getCreateType().intValue() == CommonConstants.CREATE_TYPE_2 ){
                releaseTemplate( authItemTemplate );
            }
        }catch ( BusinessException e ){
            throw new BusinessException( "发布组件 / 模板异常 , id:"+ authItemTemplate.getItemId() +
                    authItemTemplate.getTemplateId() + e.getMessage() , e );
        }

    }

    /**
     * 发布组件
     * @param authItemTemplate
     * @return
     */
    public Integer releaseItem(AuthItemTemplate authItemTemplate){
        //先查询云端组件库是否已经发布过自建的组件
        Integer existCount = dwhItemDao.findDwhItemExist( authItemTemplate );
        //先查询原组件表
        DwhItem dwhItem = itemService.findDwhItemById( authItemTemplate.getItemId() );
        if( null == existCount || 0 == existCount.intValue() ){
            //新创建云端组件
            dwhItemDao.addDwhItem( dwhItem );
            log.info( "发布时 生成的云端组件id:" + dwhItem.getDwhItemId() );
            authItemTemplate.setItemId( dwhItem.getDwhItemId() );
            releaseDao.addAuthItemTemplate( authItemTemplate );
        }else{
            //直接更新云端组件
            dwhItemDao.updateDwhItem( authItemTemplate );
            //更新云端组件权限表
            releaseDao.updateAuthItemTemplate( authItemTemplate );
        }
        return existCount;
    }

    public void releaseTemplate( AuthItemTemplate authItemTemplate ){

    }

    /**
     * 变更组件和模板审核状态
     * @param authItemTemplate
     */
    @Transactional
    public void updateCheckStatus(AuthItemTemplate authItemTemplate) {

        try{
            releaseDao.updateCheckStatus( authItemTemplate );
        }catch ( BusinessException e ){
            throw new BusinessException( "变更组件和模板审核状态异常 ，id:" +
                    authItemTemplate.getId() + e.getMessage() ,e );
        }

    }





}
