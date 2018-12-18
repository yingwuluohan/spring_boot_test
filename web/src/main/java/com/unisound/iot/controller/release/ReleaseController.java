package com.unisound.iot.controller.release;

import com.alibaba.fastjson.JSONObject;
import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.exception.BusinessException;
import com.unisound.iot.common.modle.AuthItemTemplate;
import com.unisound.iot.controller.base.BaseController;
import com.unisound.iot.service.release.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 发布组件和模板接口
 * Created by Admin on 2018/10/9.
 */
@RestController
@RequestMapping("rest/")
public class ReleaseController extends BaseController{

    @Autowired
    private ReleaseService releaseService;

    /**
     * 发布组件 , 模板 入口
     * @return
     */
    @ResponseBody
    @RequestMapping(value="v1/auth/release_item_template",method = {RequestMethod.GET, RequestMethod.POST})
    public String releaseItemTemplate(@ModelAttribute AuthItemTemplate authItemTemplate ){
        Map<String ,Object > map = null;
        try{
            //TODO 获取当前登陆人
            authItemTemplate.setDomainName( "" );
            releaseService.releaseItemTemplate( authItemTemplate );
            map = exchangeResult( null , CommonConstants.CODE_200);
        }catch ( BusinessException e ){
            map = exchangeResult( "发布组件/模板失败");
        }
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }

    /**
     * 变更组件和模板审核状态
     *
     *  @param审核主键
     *  @param审核结果
     *  @param审核人
     * @return
     */
    @ResponseBody
    @RequestMapping(value="v1/auth/updata_check_status",method = {RequestMethod.GET, RequestMethod.POST})
    public String updateCheckStatus(@ModelAttribute AuthItemTemplate authItemTemplate ){
        Map<String ,Object > map = null;
        try{
            releaseService.updateCheckStatus( authItemTemplate );
            map = exchangeResult( null , CommonConstants.CODE_200);
        }catch ( BusinessException e ){
            map = exchangeResult( "变更组件和模板审核状态失败");
        }
        Object json = JSONObject.toJSON( map);
        return json.toString();
    }

















}
