package com.unisound.iot.service.dwhitem;

import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.modle.DwhItem;
import com.unisound.iot.common.vo.ItemVo;
import com.unisound.iot.dao.mapper.source1.dwhitem.*;
import com.unisound.iot.service.base.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
@Service
public class DwhItemService extends BaseService {


    @Autowired
    private DwhItemDao dwhItemDao;


    public List<DwhItem > findDwhItem(ItemVo itemVo ){
        List<DwhItem > list = new ArrayList<>();
        //权限类型 1：公开， 2：（c600 ，lite3,pro） ,3: 所有子公司 4,：具体某产品appkey
        //自建云端的
        List<DwhItem > dwhItemSelfList = dwhItemDao.findDwhItemSelfList( itemVo );
        //全部公开的云端组件 1
        itemVo.setAuthType( CommonConstants.AUTH_TYPE_1 );
        List<DwhItem > publicDwhItemList = dwhItemDao.findPublicDwhItemList( itemVo );
        //查询某产品线的组件 2
        itemVo.setAuthType( CommonConstants.AUTH_TYPE_2 );
        List<DwhItem > productDwhItemList = dwhItemDao.findPublicDwhItemList( itemVo );
        //全部子公司的  3
        itemVo.setAuthType( CommonConstants.AUTH_TYPE_3 );
        List<DwhItem > parentDwhItemList = dwhItemDao.findPublicDwhItemList( itemVo );
        //具体appkey产品 4
        itemVo.setAuthType( CommonConstants.AUTH_TYPE_4 );
        List<DwhItem > appkeyDwhItemList = dwhItemDao.findPublicDwhItemList( itemVo );

        if(CollectionUtils.isNotEmpty( dwhItemSelfList )){
            list.addAll( dwhItemSelfList );
        }
        if(CollectionUtils.isNotEmpty( publicDwhItemList )){
            list.addAll( publicDwhItemList );
        }
        if(CollectionUtils.isNotEmpty( productDwhItemList )){
            list.addAll( productDwhItemList );
        }
        if(CollectionUtils.isNotEmpty( parentDwhItemList )){
            list.addAll( parentDwhItemList );
        }
        if(CollectionUtils.isNotEmpty( appkeyDwhItemList )){
            list.addAll( appkeyDwhItemList );
        }
        return list;
    }
}
