package com.unisound.iot.controller.dubbo.ServiceProvider;

import com.unisound.iot.controller.dubbo.frame.Url;
import com.unisound.iot.controller.dubbo.register.RemoteMapRegister;
import com.unisound.iot.controller.dubbo.rpcProtocol.httpProtocol.HttpServer;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company 北京云知声技术有限公司
 */
public class Provider {

    public static void main(String[] args) {
        //本地注册
        LocalRegister.regist( HellowService.class.getName() , HellowServiceImpl.class);

        //远程注册
        Url url = new Url( "localhost" , 8080 );
        RemoteMapRegister.setRegister( HellowService.class.getName() , url );
        //tmcat
        HttpServer httpServer = new HttpServer();
        httpServer.start( "" , 8080 );


    }



}
