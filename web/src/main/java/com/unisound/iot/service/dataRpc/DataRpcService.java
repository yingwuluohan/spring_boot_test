package com.unisound.iot.service.dataRpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.modle.dataRpc.Album;
import com.unisound.iot.service.base.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
@Service

@PropertySource({"classpath:setting.properties" })
public class DataRpcService extends BaseService{
    Logger log = LogManager.getLogger((this.getClass()));



    /**
     * 查询下拉框查询条件：内容来源：/manage/child/v1/dataOrigin/findByPage
     *
     * @return
     */
    public String findAlbumContentSource() throws Exception {
        String json = findContentSourceDataApi(ALBUM_CONTENT_SOURCE);
        log.info("find content source request result  :" + json);
        return json;
    }

    /**
     * 查询作品类型
     *
     * @return
     * @throws Exception
     */
    public String findZuopinType() throws Exception {
        String json = findZuoPinDataApi(ZUPPIN_TYPE_URL);
        log.info("find work 类型 request result :" + json);
        return json;
    }

    /**
     * 新版数据服务新版数据套餐查询接口
     * @param appKey
     * @return
     * @throws Exception
     */
    public String V2findDataApiPlanCode(String appKey) throws Exception {
        String json = null;
        String jsonApp = findAllPlanCodeDataApi(DATA_PLAN_URL, appKey);
        if(!"unisound".equals(appKey)){
            JSONArray jsonArrayApp = getJsonArray(jsonApp);
            if( null != jsonArrayApp && appKey.split( "," ).length != jsonArrayApp.size() ){
                return null;
            }
            if("0".equals(JSON.parseObject(jsonApp).get("returnCode"))){
                json = generateCodeNamePlan( jsonArrayApp );
            }else{
                List list = new ArrayList();
                Map listMap = new HashMap();
                Map dataMap = new HashMap();
                listMap.put("list",list);
                dataMap.put("data",listMap);
                dataMap.put("code",CommonConstants.CODE_200);
                json = JSON.toJSONString(dataMap);
            }
        }else { //查询YZS的全部套餐
            json = formatJson( jsonApp );
        }
        log.info("find data 套餐 request result :" + json);
        return json;
    }

    public String formatJson(String jsonStr ){
        JSONObject json = JSONObject.parseObject( jsonStr );
        JSONArray jsonArrayUnisound = json.getJSONArray("data");
        JSONObject jsonObjectPlans = jsonArrayUnisound.getJSONObject( 0 );
        JSONArray jsonArray = jsonObjectPlans.getJSONArray("dataPlans");
        List<Map> jsonList = new ArrayList<Map>();
        if( null != jsonArray ){
            for( int i = 0 ;i < jsonArray.size();i++ ){
                Map<String,String> map = new HashMap<String, String>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String code = jsonObject.getString( "code" );
                String name = jsonObject.getString( "name" );
                map.put( "code" , code );
                map.put( "name" , name );
                jsonList.add( map );
            }
        }

        Map listMap = new HashMap();
        Map dataMap = new HashMap();
        listMap.put("list",jsonList);
        dataMap.put("data",listMap);
        dataMap.put("code",CommonConstants.CODE_200);
        String resultJson = JSON.toJSONString(dataMap);
        return resultJson;
    }

    public String generateCodeNamePlan( JSONArray jsonArrayApp ){

        //存放code 的交集数量
        Map<String ,Integer > codeNumMap = new HashMap<String,Integer>();
        Map<String , String > disMap = new HashMap<String , String >();
        Integer codeNum = 0;
        //获取map中num的数值等于codeNum 的数字
        List<Map> jsonList = new ArrayList<Map>();
        try{
            //appKey对应数据套餐code去重放到list中
            for (int i = 0; i < jsonArrayApp.size(); i++) {
                codeNum = jsonArrayApp.size();
                Set<String> codeSet = new HashSet<>();
                JSONArray jsonPlanArray = jsonArrayApp.getJSONObject(i).getJSONArray("dataPlans");
                if( null != jsonPlanArray ){
                    for( int j = 0 ; j < jsonPlanArray.size();j++ ){
                        JSONObject jsonObject = jsonPlanArray.getJSONObject(j);
                        Integer code = jsonObject.getInteger( "code" );
                        String name = jsonObject.getString( "name" );
                        String codeStr = code +"";
                        codeSet.add( codeStr );
                        disMap.put( codeStr , name );
                    }
                }
                for( String code : codeSet ){
                    if( codeNumMap.get( code ) != null ){
                        Integer num = codeNumMap.get( code );
                        codeNumMap.put( code , num+1 );
                    }else{
                        codeNumMap.put( code , 1 );
                    }
                }
            }

            for( String key : codeNumMap.keySet()){
                if( codeNumMap.get( key ).intValue()== codeNum.intValue() ){
                    Map<String,String> tempMap = new HashMap<String, String>();
                    tempMap.put("code", key );
                    tempMap.put("name",disMap.get( key ));
                    jsonList.add(tempMap);
                }
            }
        }catch ( Exception e ){
            log.error( "data api 套餐异常," + e.getMessage() ,e );
        }
        //封装成{"data":{"list":[{}]}}形式
        Map listMap = new HashMap();
        Map dataMap = new HashMap();
        listMap.put("list",jsonList);
        dataMap.put("data",listMap);
        if(jsonList.size() > 0){
            dataMap.put("code",CommonConstants.CODE_200);
        }else{
            dataMap.put("code",CommonConstants.CODE_400);
            dataMap.put("msg","返回结果为空");
        }
        String jsonStr = JSON.toJSONString(dataMap);
        return jsonStr;
    }

    /**
     * 查询专辑
     * @param album
     * @return
     * @throws Exception
     */
    public String findAlbumList(Album album) throws Exception {
        return findAlbumListDateApi( album , AULBUM_URL );
    }
}
