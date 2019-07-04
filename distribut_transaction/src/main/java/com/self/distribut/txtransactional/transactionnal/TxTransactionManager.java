package com.self.distribut.txtransactional.transactionnal;

import com.alibaba.fastjson.JSONObject;
import com.self.distribut.txtransactional.netty.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TxTransactionManager {




    private static NettyClient nettyClient;

    private static ThreadLocal< TxTransaction > current = new ThreadLocal<>();

    @Autowired
    public void setNettyClient( NettyClient nettyClient ){
        TxTransactionManager.nettyClient = nettyClient;
    }

    public static Map<String , TxTransaction > LB_TRANSACION_MAP = new HashMap<>();


    public static String createTxTransactionGroup(){
        String groupId = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "groupId" , groupId );
        jsonObject.put( "command" , "create" );
        nettyClient.send( jsonObject );
        System.out.println( "创建事务组" );
        return groupId;
    }
    /** 创建事务*/
    public static TxTransaction createLbTransaction( String groupId ){
        String transactionId = UUID.randomUUID().toString();
        TxTransaction lbTransaction = new TxTransaction( groupId , transactionId );

        LB_TRANSACION_MAP.put( groupId , lbTransaction );
        System.out.println( "创建事务" );
        current.set( lbTransaction );
        return lbTransaction;
    }

    /** 提交事务*/
    public static TxTransaction addLbTransaction( TxTransaction lbTransaction , Boolean isEnd , TransactionType transactionType){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "groupId" , lbTransaction.getGroupId() );
        jsonObject.put( "transactionId" , lbTransaction.getTransactionId() );
        jsonObject.put( "transactionType" , transactionType );
        jsonObject.put( "commond" , "add" );
        jsonObject.put( "isEnd" , isEnd );
        jsonObject.put( "isEnd" , isEnd );


        return null;
    }


    //通过groupID 拿到事务
    public static TxTransaction getLbTransaction( String groupId ){
        return LB_TRANSACION_MAP.get( groupId );
    }

    /**获取当前线程的事务 */
    public static TxTransaction getCurrent(){
        return current.get();
    }













}
