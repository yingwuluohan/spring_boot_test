package com.unisound.iot.controller.item;

import com.alibaba.fastjson.JSONObject;
import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.entity.ResponseCodeEnum;
import com.unisound.iot.common.exception.BusinessException;
import com.unisound.iot.common.modle.DwhItem;
import com.unisound.iot.common.modle.Item;
import com.unisound.iot.common.rest.ResponseStream;
import com.unisound.iot.common.vo.ItemVo;
import com.unisound.iot.controller.base.BaseController;
import com.unisound.iot.service.dwhitem.DwhItemService;
import com.unisound.iot.service.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件操作入口
 * Created by Admin on 2018/9/30.
 */
@RestController
@RequestMapping("rest/")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private DwhItemService dwhItemService;


    @SuppressWarnings("unchecked")
    @RequestMapping(value="v1/item/getMsgHistory/{id}",produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public ResponseStream<Object> getMsgHistory(HttpServletRequest request, @PathVariable(name="id") long id){

        System.out.println( "参数:" + id );
        //itemService.findItemDetail( 232L );
        return new ResponseStream( ResponseCodeEnum.success_code , "1111","" );

    }

    /**
     * 验证组件名称是否冲突
     * @param request
     * @param itemVo
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value="v1/item/check_item_name_unique",method = {RequestMethod.GET, RequestMethod.POST})
    public String checkItemNameUnique(HttpServletRequest request, @ModelAttribute ItemVo itemVo ){
        Map<String ,Object > map = null;
        boolean unique = itemService.checkItemNameUnique( itemVo );
        if( unique ){
            map = exchangeResult( "名称可用" , CommonConstants.CODE_200);
        }else{
            map = exchangeResult( "名称不可用" , CommonConstants.CODE_400);
        }
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }


    /**
     * 创建组件
     * @param request
     * @param
     * @return
     * @ModelAttribute
     * RequestBody&
     * rest/v1/item/add_item?domainId=111&name=ddkm3&itemType=1&productType=2&itemStructure=test
     */
    @SuppressWarnings("unchecked")
    //@ResponseBody
    @RequestMapping(value="v1/item/add_item",produces = "application/json;charset=UTF-8",method = {RequestMethod.GET, RequestMethod.POST})
    public String addItem(HttpServletRequest request, @ModelAttribute Item item ){
        Map<String ,Object > map = null;
        try{
            //TODO 获取登陆人
            item.setCreator( "" );
            itemService.addItem( item );
            map = exchangeResult( null , CommonConstants.CODE_200);
        }catch ( BusinessException e ){
            map = exchangeResult( "添加组件失败");
        }
        Object json = JSONObject.toJSON( map);
        return json.toString();

    }

    /**
     * update 前
     * 查询某个组件信息
     * @return
     * rest/v1/item/find_item?itemId=1
     */
    @ResponseBody
    @RequestMapping(value="v1/item/find_item",method = {RequestMethod.GET, RequestMethod.POST})
    public String findItemDetail(HttpServletRequest request, @ModelAttribute ItemVo itemVo ){
        Item item = itemService.findItemDetail( itemVo );
        Map<String ,Object > map = exchangeResult( item , CommonConstants.CODE_200);
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }

    /**
     * 查询合作商下所有组件列表 ，自建+ 云端
     * @param request
     * @param itemVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value="v1/item/find_item_list",method = {RequestMethod.GET, RequestMethod.POST})
    public String findItemList(HttpServletRequest request, @ModelAttribute ItemVo itemVo ){
        itemVo = new ItemVo();
        itemVo.setDomainId( 111L );
        itemVo.setItemType( 1 );
        //自建列表
        List< Item > itemList = itemService.findItemList( itemVo );
        //查询云端组件 权限类型 1：公开， 2：（c600 ，lite3,pro） ,3: 所有子公司 4,：具体某产品appkey
        /**获取上层父公司domainId*/
        Long loginDomainId = null;
        Long domainId = itemService.getParentDomainId( loginDomainId );
        itemVo.setParendDomainId( domainId );
        /**获取当前公司所有产品线*/
        List< String > productTypeList = itemService.getProductTypeList( loginDomainId );
        itemVo.setProductTypeList( productTypeList );
        //获取当前公司的所有appkey
        List< String > appkeyList = itemService.findDomainAppkeyList( loginDomainId );
        itemVo.setAppkeyList( appkeyList );
        //获取当前登录人公司维度
        String scopeType = null;
        itemVo.setScopeType( scopeType );
        List<DwhItem > dwhItemList = dwhItemService.findDwhItem( itemVo );
        Map< String ,List > mapTem = new HashMap<>();
        mapTem.put( "itemList" , itemList );
        mapTem.put( "dwhItemList" , dwhItemList );
        Map<String ,Object > map = exchangeResult( mapTem , CommonConstants.CODE_200);
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }

    /**
     * 修改组件信息
     * @param request
     * @param item
     * @return  @RequestBody
     * rest/v1/item/update_item?itemId=1&name=test&itemStructure=testJsontest
     */
    @ResponseBody
    @RequestMapping(value="v1/item/update_item",method = {RequestMethod.GET, RequestMethod.POST})
    public String updateItem(HttpServletRequest request, @RequestBody Item item ){
        Map<String ,Object > map = null;
        //TODO  test
        item.setUpdater( "test" );
        try{
            itemService.updateItem( item );
            map = exchangeResult( null , CommonConstants.CODE_200);
        }catch ( BusinessException e ){
            map = exchangeResult( "更新组件失败" );
        }
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }

    /**
     * 删除自建 或 云端 组件
     * @param request
     * @param itemId
     * @param domainId ( 被删除组件所属的合作商主键 )
     * @param itemCreateType (1:自建，2：云端 )
     * @return
     *
     * 删除自建组件：:1：需要删除自建组件，2：删除自建组件和自建模板关系 ，3：删除云端模板和组件关系
     */
    @ResponseBody
    @RequestMapping(value="v1/item/delete_item",method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteItem(HttpServletRequest request,
                               @RequestParam(value = "itemId", required = true , defaultValue = "0")Long itemId ,
                               @RequestParam(value = "itemCreateType", required = true , defaultValue = "0")Integer itemCreateType ,
                               @RequestParam(value = "domainId", required = true , defaultValue = "0")Long domainId ){
        Map<String ,Object > map = null;

        try{
            //TODO 获取当前登录人的公司类型，如果是云知声则不做限制，如果是其他合作商则追加 domainId 限制
            ItemVo itemVo = new ItemVo();
            itemVo.setItemId( itemId );
            itemVo.setDomainId( domainId );
            itemVo.setUpdater( "" );
            itemVo.setUpdaterId( 1L );
            itemVo.setItemCreateType( itemCreateType );
            //TODO 中间件获取登陆人的公司主键
            itemVo.setOperDomainId( 1L );
            itemService.deleteItem( itemVo );
            map = exchangeResult( null , CommonConstants.CODE_200);
        }catch ( BusinessException e ){
            map = exchangeResult( "更新组件失败" );
        }
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }
























}
