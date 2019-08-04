package com.unisound.iot.controller.dubbo.rpcProtocol.httpProtocol;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company 北京云知声技术有限公司
 */
public class DispatcherServlet extends HttpServlet {



    @Override
    protected void service(HttpServletRequest request , HttpServletResponse response){
        new HttpServerHandler().handler( request ,response );

    }



}
