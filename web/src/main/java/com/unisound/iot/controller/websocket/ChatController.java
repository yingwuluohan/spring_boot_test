package com.unisound.iot.controller.websocket;

import com.unisound.iot.api.chart.ChartRoomService;
import com.unisound.iot.api.chart.ChatRoomServerClientService;
import com.unisound.iot.api.chart.ChatRoomServerInitService;
import com.unisound.iot.api.chart.ClientServiceApi;
import com.unisound.iot.common.mongo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by fn on 2017/5/19.
 */
@RequestMapping("/chat")
@Controller
public class ChatController {

    @Autowired
    private ChartRoomService chartRoomService;
    @Autowired
    private ClientServiceApi clientService;
    // 实验2
    @Autowired
    private ChatRoomServerClientService chatRoomServerClientService;
    @Autowired
    private ChatRoomServerInitService chatRoomServerInitService;

    @ResponseBody
    @RequestMapping( value="/initHttpChat" ,method= RequestMethod.GET )
    public void initHttpChat(){
        try {
            new WebsocketChatServer( 8090 ).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping( value="/goHttpChat" ,method= RequestMethod.GET )
    public String goHttpChat(User user , HttpServletRequest request ){
        if( null != user ){
            request.setAttribute( "id" , user.getId() );
            request.setAttribute( "mobile" , user.getPassword());
        }
        return "chat/HttpChatNio";
    }
    /**
     * 初始化聊天服务
     * my.global.com:8080/chat/initChatServer
     */
    @RequestMapping( value="/initChatServer" ,method= RequestMethod.GET )
    public void initChatServer(){

        //chartRoomService.init( 19999 );
        try {
            chatRoomServerInitService.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping( value="/goChat" ,method= RequestMethod.GET )
    public String goChat(){
        try {
            chatRoomServerClientService.initClientService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "chat/clientWindow";
    }





    /**
     * 发送消息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping( value="/sendMessage" ,method= RequestMethod.POST )
    public String sendMessage(HttpServletRequest request , HttpServletResponse response ){
        //ClientService.getInstance();
        //clientService.sendMsg( "sdf3rrg" );

        //在主线程中 从键盘读取数据输入到服务器端
        chatRoomServerClientService.writeContent(  "25dfgwe3" );
        return "shoudao";
    }

    /**
     * 接收消息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping( value="/receiveMsg" ,method= RequestMethod.POST )
    public String receiveMsg(HttpServletRequest request , HttpServletResponse response ){
        String message = clientService.receiveMsg(  );

        return message;
    }
}
