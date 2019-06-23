package com.unisound.iot.api.api_extend;

public interface BaseApi extends TestApi {




    class baseinfo{
        private String info;

        public void getinfo(String key ){
            System.out.println( key );
        }

    }
}
