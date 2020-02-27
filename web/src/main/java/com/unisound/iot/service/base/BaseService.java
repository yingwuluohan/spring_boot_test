package com.unisound.iot.service.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unisound.iot.common.constants.CommonConstants;
import com.unisound.iot.common.exception.BusinessException;
import com.unisound.iot.common.modle.Topic;
import com.unisound.iot.common.modle.TopicData;
import com.unisound.iot.common.modle.base.BaseModle;
import com.unisound.iot.common.modle.dataRpc.Album;
import com.unisound.iot.common.modle.dataRpc.AlbumDetail;
import com.unisound.iot.common.modle.dataRpc.AlbumType;
import com.unisound.iot.common.rpc.BaseServiceApi;
import com.unisound.iot.common.sign.AlbumRequestUtil;
import com.unisound.iot.common.util.HttpRequestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company fn
 */
@Service
@PropertySource({"classpath:setting.properties" })
public abstract class BaseService implements BaseServiceApi {

    Logger log = LogManager.getLogger(this.getClass());

    /**  内容来源*/
    @Value("${AULBUM_DETAIL_URL}")
    public String AULBUM_DETAIL_URL;
    /** 请求专辑的接口地址 */
    @Value("${ALBUM_CONTENT_SOURCE}")
    public String ALBUM_CONTENT_SOURCE;
    /** 作品类别 */
    @Value("${ZUPPIN_TYPE_URL}")
    public String ZUPPIN_TYPE_URL;
    /***/
    @Value(value ="${AULBUM_URL}")
    public String AULBUM_URL;
    /** 数据套餐地址 */
    @Value("${DATA_PLAN_URL}")
    public String DATA_PLAN_URL;


    public String getAULBUM_DETAIL_URL() {
        return AULBUM_DETAIL_URL;
    }

    public void setAULBUM_DETAIL_URL(String AULBUM_DETAIL_URL) {
        this.AULBUM_DETAIL_URL = AULBUM_DETAIL_URL;
    }

    public String getALBUM_CONTENT_SOURCE() {
        return ALBUM_CONTENT_SOURCE;
    }

    public void setALBUM_CONTENT_SOURCE(String ALBUM_CONTENT_SOURCE) {
        this.ALBUM_CONTENT_SOURCE = ALBUM_CONTENT_SOURCE;
    }

    public String getZUPPIN_TYPE_URL() {
        return ZUPPIN_TYPE_URL;
    }

    public void setZUPPIN_TYPE_URL(String ZUPPIN_TYPE_URL) {
        this.ZUPPIN_TYPE_URL = ZUPPIN_TYPE_URL;
    }

    public String getAULBUM_URL() {
        return AULBUM_URL;
    }

    public void setAULBUM_URL(String AULBUM_URL) {
        this.AULBUM_URL = AULBUM_URL;
    }

    public String getDATA_PLAN_URL() {
        return DATA_PLAN_URL;
    }

    public void setDATA_PLAN_URL(String DATA_PLAN_URL) {
        this.DATA_PLAN_URL = DATA_PLAN_URL;
    }


    /**
     * 新版数据服务查询全部数据来源
     * @param httpUrl
     * @return
     * @throws Exception
     */
    public String findAllPlanCodeDataApi(String httpUrl ,String appKey ) throws Exception{
        HttpRequestUtil request = new HttpRequestUtil();
        Map<String ,String > map = AlbumRequestUtil.getCommonParams();
        map.put( "appKey" , appKey );
        String signResult = AlbumRequestUtil.getSign( map );
        String url =  httpUrl+"?appKey="+appKey+"&key=02be86b7fe35b2405f9049dda19ced42&sign="+signResult+"&scenario=child";
        String json = request.sendGet( url);
        log.info( "查询数据来源请求结果:" + json );
        return json;
    }
    /**
     * 查询下拉框查询条件：内容来源：/manage/child/v1/dataOrigin/findByPage
     * @return
     */
    public String findContentSourceDataApi( String httpUrl ) throws Exception {
        HttpRequestUtil request = new HttpRequestUtil();
        Map<String ,String > map = AlbumRequestUtil.getCommonParams();
        map.put( "allData" , "true" );
        String signResult = AlbumRequestUtil.getSign( map );
        String url =  httpUrl +"?key=02be86b7fe35b2405f9049dda19ced42&sign="+signResult+"&allData=true&scenario=child";
        String json = request.sendGet( url);
        return json;
    }
    /**
     * 查询作品类型
     * @return
     * @throws Exception
     */
    public String findZuoPinDataApi( String httpUrl ) throws Exception {
        HttpRequestUtil request = new HttpRequestUtil();
        Map<String ,String > map = AlbumRequestUtil.getCommonParams();
        map.put( "allData" , "true" );
        String signResult = AlbumRequestUtil.getSign( map );
        String url =  httpUrl+"?key=02be86b7fe35b2405f9049dda19ced42&sign="+signResult+"&allData=true&scenario=child";
        String json = request.sendGet( url);
        log.info( "查询内容来源请求结果:" + json );
        return json;
    }
    /**
     * 查询AppKey对应数据来源
     * @param httpUrl
     * @return
     * @throws Exception
     */
    public String findAppPlanCodeDataApi(String httpUrl, String appKey ) throws Exception {
        HttpRequestUtil request = new HttpRequestUtil();
        Map<String ,String > map = AlbumRequestUtil.getCommonParams();
        map.put( "appKey" , appKey );
        String signResult = AlbumRequestUtil.getSign( map );
        String url =  httpUrl+"?key=02be86b7fe35b2405f9049dda19ced42&sign="+signResult+"&appKey="+appKey+"&scenario=child";
        String json = request.sendGet( url);
        log.info( "查询数据来源请求结果:" + json );
        return json;
    }

    /**
     *
     * @param albumIdList
     * @param baseModle
     * @return
     *  dataType : 1：专辑合集类型，2：专辑类型，3：作品类型，4：网页类型, 5：专题
     */
    public BaseModle getDataApiResult(List< Long > albumIdList , BaseModle baseModle ){
        //查询专辑对象
        List<Object> albumDetailList = new LinkedList<Object>();
        try {
            for( int i = 0; i <albumIdList.size();i++ ){
                if( 2 == baseModle.getDataType() || 3 == baseModle.getDataType() ){
                    String albumDetailJson = findDateApiDetail( baseModle.getDataType() ,albumIdList.get( i ) );
                    AlbumDetail albumDetail = (AlbumDetail)formartJson( baseModle.getDataType() , albumDetailJson );
                    if( null != albumDetail ){
                        albumDetail.setLevel( albumIdList.size() - i );
                        albumDetailList.add( albumDetail );
                    }
                }else if( 1 == baseModle.getDataType()){
                    AlbumType albumType = findAlbumType( albumIdList.get( i ) );
                    if( null != albumType ){
                        albumType.setLevel(albumIdList.size() - i );
                        albumDetailList.add( albumType );
                    }else{
                        log.info( "数据服务接口返回结果为空，json参数:"  );
                    }
                }else if( 5 == baseModle.getDataType()){
                    Topic topic = findTopicDetail( albumIdList.get( i ) );
                    albumDetailList.add( topic );
                }
            }
        } catch ( BusinessException e1 ){
            log.error( "获取远程内容服务结果json解析异常" );
            throw new BusinessException("获取远程内容服务结果异常" , e1 );
        } catch (Exception e) {
            e.printStackTrace();
            log.error( "获取远程内容服务结果json解析异常" );
            throw new BusinessException("获取远程内容服务结果异常" , e );
        }
        baseModle.setList( albumDetailList );
        return baseModle;
    }

    /**
     * 格式化列表查询json结果
     * @param dataType
     * @param jsonStr
     * @return
     */
    public Object formartJson( int dataType , String jsonStr ){
        if( dataType == 2 || dataType == 3 ){
            JSONObject json = JSONObject.parseObject(jsonStr);
            log.info( dataType + "类型，解析结果：" + json );
            if( null != json ){
                Object object = json.get( "data" );
                if( null != object ){
                    String listJson =  object.toString();
                    JSONObject json2 = JSONObject.parseObject(listJson);
                    Object object2 = json2.get( "list" );
                    if( null != object2 ){
                        String listJson2 = object2.toString();
                        JSONObject contentJson = JSONObject.parseObject(listJson2.substring( 1 ,listJson2.length() -1));
                        log.info( "专辑对象：" + listJson2 );
                        AlbumDetail albumDetail = JSON.parseObject( contentJson.toString() ,AlbumDetail.class );
                        return  albumDetail;
                    }
                }
            }
        }else if( dataType == 1 ){//专辑合集类型
            AlbumType albumType = JSON.parseObject( jsonStr ,AlbumType.class );
            return  albumType;
        }else if( dataType == 5 ){//专题类型
            AlbumType albumType = JSON.parseObject( jsonStr ,AlbumType.class );
            return  albumType;
        }
        return null;
    }

    public String findDateApiDetail(Integer dataType , Long apiId ) throws Exception {
        String result = null;
        if( 1 == dataType ){//专辑合集
            result = findAlbumTypeInfo( apiId );
        }else if( 2 == dataType ){//专辑类型
            result =findAlbumDetailDataApi( AULBUM_URL ,apiId );
//            result =findAlbumDetailDataApi( "" ,apiId );
        }else if( 3 == dataType ){//作品资源类型
//            result =findAlbumDetailDataApi( appBannerService.getAULBUM_DETAIL_URL() ,apiId );
            result =findAlbumDetailDataApi( "" ,apiId );
        }
        return result;
    }

    public Topic findTopicDetail( Long topicId ){
        //查询专题主表
        Topic topic = null;//topicDao.findTopicDetail( topicId );
        if( null != topic ){
            //专题子表
            List<TopicData > topicDataList = null;//topicDao.findTopicDataDetail( topicId );
            topic.setList( topicDataList );
//            Object json = JSONObject.toJSON( topic );
//            return json.toString();
        }
        return topic;
    }

    public AlbumType findAlbumType(Long albumTypeId ){
        AlbumType albumType = null;//soundAlbumDao.findAlbumTypeInfo( albumTypeId );
        return albumType;
    }
    /**
     * 查询单个专辑合集信息
     * @param albumTypeId
     * @return
     */
    public String findAlbumTypeInfo(Long albumTypeId ){
        AlbumType albumType = null;//soundAlbumDao.findAlbumTypeInfo( albumTypeId );
        if( null != albumType ){
            Object json = JSONObject.toJSON( albumType );
            return json.toString();
        }
        return null;
    }

    /**
     * 查询单条的专辑详细信息内容接口
     * @param   albumId
     * @return
     * @throws Exception
     */
    public String findAlbumDetailDataApi(String httpUrl , Long albumId) throws Exception {
        HttpRequestUtil request = new HttpRequestUtil();
        Map<String ,String > map = AlbumRequestUtil.getCommonParams();
        map.put( "id" ,String.valueOf( albumId) );
        map.put( "status" , "1" );
        map.put( "pageCount" , "1" );
        map.put( "pageNo" , "1" );
        String signResult = AlbumRequestUtil.getSign( map );
        //System.out.println( "签名结果:" + signResult );
        String url =  httpUrl+"?id="+albumId+"&key=02be86b7fe35b2405f9049dda19ced42&pageCount=1&pageNo=1&scenario=child&status=1&sign="+signResult;
        try{
            String json = request.sendGet( url);
            return json;
        }catch (Exception e ){
            log.error( "rpc内容服务api调用异常 " + e.getMessage() ,e );
            throw new BusinessException("rpc内容服务api调用异常 " + e.getMessage() ,e );
        }

    }

    public JSONArray getJsonArray(String jsonStr){
        JSONObject json = JSONObject.parseObject( jsonStr );
        if( null != json ){
            JSONArray jsonArray = json.getJSONArray("data");
            return jsonArray;
        }
        return null;
    }

    /**
     * 查询专辑列表信息
     * @param album
     * @return
     * @throws Exception
     */
    public String findAlbumListDateApi(Album album , String httpUrl ) throws Exception {
        String result = getRemoteDateService( album , httpUrl , CommonConstants.MANAGE_KEY );
        return result;
    }

    /**
     * 调用数据远程接口封装的方法
     * @param bean   ：参数对象
     * @param httpUrl  ：每个数据接口的请求地址
     * @param key      ：秘钥key
     * @return
     * @throws Exception
     */
    public String getRemoteDateService( Album  bean , String httpUrl  ,String key) throws Exception {
        HttpRequestUtil request = new HttpRequestUtil();
        StringBuilder sb = new StringBuilder();
        Map<String ,String > map = AlbumRequestUtil.getCommonParams();
        sb.append( httpUrl +"?key=" + key );
        Class remoteBean = (Class) bean.getClass();
        /** 得到类中的所有属性集合 */
        Field[] fs = remoteBean.getDeclaredFields();
        //获取所有参数
        for(int i = 0 ; i < fs.length; i++){
            Field field = fs[i];
            field.setAccessible(true); //设置属性是可以访问的
            Object val = field.get( bean );//得到此属性的值
            String type = field.getType().toString();//得到此属性的类型
            if (type.endsWith("String")) {
                if( null != val && !"".equals( val )){
                    map.put( field.getName() , (String)val );
                    sb.append( "&"+field.getName()+"="+ URLEncoder.encode( (String)val , "utf8") );
                }
            }else if(type.endsWith("int") || type.endsWith("Integer")){
                if( null != val ){
                    map.put( field.getName() , String.valueOf( (Integer)val) );
                    sb.append( "&"+field.getName()+"="+(Integer)val );
                }
            }else if(type.endsWith("long") || type.endsWith("Long") ){
                if( !"serialVersionUID".equals(field.getName()) && null != val ){
                    map.put( field.getName() , String.valueOf( (Long)val) );
                    sb.append( "&"+field.getName()+"="+(Long)val );
                }
            }
        }
        sb.append( "&scenario=child" );
        String signResult = AlbumRequestUtil.getSign( map );
        sb.append( "&sign=" + signResult );
        try{
            String json = request.sendGet( sb.toString() );
            log.info( "data service Url:" + sb.toString());
            log.info( "data service response :" + json);
            return json;
        }catch (Exception e ){
            log.error( "rpc search exception " + e.getMessage() ,e );
            throw new BusinessException("rpc查询调用异常 " + e.getMessage() ,e );
        }
    }
}
