package com.unisound.iot.common.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Created by yingwuluohan on 2019/1/18.
 * @Company 北京云知声技术有限公司
 */
public class CollectionLamUtil {


    private List<Map<String , Object >> list = new ArrayList<>();



    public void foreache( List<Map<String , Object >> list , String key ){

        list.forEach( ( ( map ) -> {
            System.out.println( map.get( key ) );
        }));

    }


















}
