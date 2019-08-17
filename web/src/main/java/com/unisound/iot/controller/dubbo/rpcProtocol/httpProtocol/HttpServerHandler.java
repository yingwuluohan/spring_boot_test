package com.unisound.iot.controller.dubbo.rpcProtocol.httpProtocol;

import com.alibaba.fastjson.JSONObject;
import com.unisound.iot.controller.dubbo.ServiceProvider.LocalRegister;
import com.unisound.iot.controller.dubbo.frame.Invocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 */
public class HttpServerHandler {


    public void handler(HttpServletRequest request , HttpServletResponse response) throws NoSuchMethodException {
        //处理HTTP请求的逻辑
        System.out.println( "" + request.getAuthType() );
        try {
            Invocation invocation = JSONObject.parseObject( request.getInputStream(), Invocation.class);
            //根据接口名找到实现类
            Class impClass = LocalRegister.get( invocation.getInterfaceName() );
            Method method = impClass.getMethod( invocation.getMethodName(),invocation.getParamsTypes());

            String result = (String)method.invoke( impClass.newInstance(),invocation.getParams());

//            IOUtils.write( request , response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }










}
