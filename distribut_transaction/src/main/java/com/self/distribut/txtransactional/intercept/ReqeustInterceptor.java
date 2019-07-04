package com.self.distribut.txtransactional.intercept;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReqeustInterceptor implements HandlerInterceptor {


    public boolean preHandler(HttpServletRequest request , HttpServletResponse response ,Object handler ){
        String groupId = request.getHeader( "groupId" );
        String transactionCount = request.getHeader( "transactionCount" );
//        TxTransactionManager.setCurrentGroupId( groupId );
//        TxTransactionManager.setTransactionCount( Integer.valueOf( transactionCount == null?"0":transactionCount));

        return true;
    }


    public void postHandler( HttpServletRequest request , HttpServletResponse response ,Object handler ,ModelAndView modelAndView){

    }

    public void afterCompletion(HttpServletRequest request , HttpServletResponse response ,Object handler ,Exception ex){

    }






}
