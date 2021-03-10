package com.unisound.iot.controller.http_request_io;

import com.alibaba.fastjson.JSONObject;
import com.unisound.iot.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ReplaceStreamFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) request);
        String body = wrapper.getBodyString();
        Map<String , Object > mapResponse = new HashMap<>();
        try{
            if(!StringUtils.isEmpty( body )){
                //TODO 此处目前仅仅支持了Json对象，数组参数目前还未支持
                JSONObject json = JSONObject.parseObject( body );
                Set<String> set = json.keySet();
                for( String key : set ){
                    mapResponse.put( key , json.get( key ));
                }
            }
//            mapResponse.put( "connectorId" , "111test" );
            JSONObject jsonMap = new JSONObject( mapResponse );
            String temp = jsonMap.toString();
            wrapper.setBody( temp.getBytes() );
            chain.doFilter( wrapper, response);
        }catch ( Exception e ){
            e.printStackTrace();
            chain.doFilter( wrapper, response);
        }
    }

    @Override
    public void destroy() {
    }
}