package com.unisound.iot.controller.dataRpc;

import com.unisound.iot.common.modle.dataRpc.Album;
import com.unisound.iot.service.dataRpc.DataRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据服务（ 专辑，作品，专辑合集 ，套餐，）控制类入口
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
@RestController
@RequestMapping("rest/")
public class DateRpcController {


    @Autowired
    private DataRpcService dataRpcService;



    /**
     * 分页查询专辑列表信息
     * @param album
     * @return
     * operation/contentype/find_album_list?albumType=playlist&dataOrigin=11&dataPlanCode=2006&sentenceDemand=0&domainName=audio&voiceDemand=1
     */
    @ResponseBody
    @RequestMapping(value = "v1/rpc/find_album_list" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String findAlbumList( @ModelAttribute Album album){

        String albumJson = null;
        try {
            album.setStatus( 1 );
            albumJson = dataRpcService.findAlbumList( album );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albumJson;
    }
    /**
     * 查询下拉框查询条件：内容来源：/manage/child/v1/dataOrigin/findByPage
     * @return
     *  rest/v1/data/find_album_content_source
     */
    @ResponseBody
    @RequestMapping(value = "v1/rpc/find_album_content_source" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String findAlbumContentSource(  ){
        String albumJson = null;
        try {
            albumJson = dataRpcService.findAlbumContentSource( );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albumJson;
    }

    /**
     * 下拉框 ：作品类别
     * @return
     * rest/v1/rpc/find_product_type
     */
    @ResponseBody
    @RequestMapping(value = "v1/rpc/find_product_type" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String findZuopinType(){
        String albumJson = null;
        try {
            albumJson = dataRpcService.findZuopinType( );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albumJson;
    }
    /**
     * 下拉框 ：数据套餐
     * @return
     * /rest/v1/rpc/find_data_api_plancode
     */
    @ResponseBody
    @RequestMapping(value = "v1/rpc/find_data_api_plancode" ,method = {  RequestMethod.POST})
    public String findDataApiPlanCode(@RequestParam(value = "appKey", required = true)String appKey ){
        String albumJson = null;
        try {
            albumJson = dataRpcService.V2findDataApiPlanCode(appKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return albumJson;
    }
}
