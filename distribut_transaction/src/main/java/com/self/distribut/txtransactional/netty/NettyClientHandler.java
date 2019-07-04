package com.self.distribut.txtransactional.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.self.distribut.txtransactional.transactionnal.TransactionType;
import com.self.distribut.txtransactional.transactionnal.TxTransaction;
import com.self.distribut.txtransactional.transactionnal.TxTransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    private ChannelHandlerContext context;
    public void channelActive( ChannelHandlerContext ctx ){
        context = ctx;
    }



    public synchronized void channelRead(ChannelHandlerContext ctx , Object msg ) throws Exception{
        //有服务端告知状态
        System.out.println( "接收数据" + msg );
        JSONObject jsonObject = JSON.parseObject( (String) msg );
        String groupId = jsonObject.getString( "groupId" );
        String command = jsonObject.getString( "command" );

        System.out.println( "接收commond:" + command );
        TxTransaction txTransaction = TxTransactionManager.getLbTransaction( groupId );

        if( command.equals( "rollback" )){
            txTransaction.setTransactionType(TransactionType.rollback );
        }else if( command.equals( "commit" )){
            txTransaction.setTransactionType(TransactionType.comit );
        }
        //唤醒
        txTransaction.getTask().signalTask();

    }


    public synchronized Object call( JSONObject data ){
        context.writeAndFlush( data.toJSONString() );
        return null;
    }



}
