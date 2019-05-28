<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebSocket Chat</title>
</head>
<%
    String mobile = (String) request.getAttribute( "mobile" );
    String id = (String) request.getAttribute( "id" );
%>
<body>
<script src="/js/jquery-1.8.2.min.js"></script>
<script src="/js/json2.js"></script>
<script src="/js/json_parse.js"></script>
<script type="text/javascript">
    var socket;
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        socket = new WebSocket("ws://127.0.0.1:8080/websocket");
        socket.onmessage = function(event) {
            var ta = document.getElementById('responseText');
            var ta2 = document.getElementById('lineText');
            var content = event.data;

            if( content.indexOf( "said:" ) == -1  ){
                ta.value = ta.value + '\n' + event.data
            }else{
                ta2.value = ta2.value + '\n' + event.data
            }
        };
        socket.onopen = function(event) {
            var ta = document.getElementById('responseText');
            ta.value = "连接开启!";
            socket.send( "said:"+Math.random() + "上线");
        };
        socket.onclose = function(event) {
            var ta = document.getElementById('responseText');
            ta.value = ta.value + "连接被关闭";
        };
    } else {
        alert("你的浏览器不支持 WebSocket！");
    }

    function send(message) {
        if (!window.WebSocket) {
            return;
        }

        var mobile = <%=mobile%>;

        socket.send(message);
        if (socket.readyState == WebSocket.OPEN) {
            socket.send(message);
        } else {
            alert("连接没有开启.");
        }
    }
</script>
<form onsubmit="return false;">
    <h3>WebSocket 聊天室：</h3>
    <textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
    <br>
    <input type="text" name="message"  style="width: 300px" value="Welcome to www.waylau.com">
    <br>
    <h3>WebSocket 在线人：</h3>
    <textarea id="lineText" style="width: 500px; height: 100px;"></textarea>
    <br>
    <input type="button" value="发送消息" onclick="send(this.form.message.value)">
    <input type="button" value="初始化服务" onclick="serverInit()">
    <input type="button" onclick="javascript:document.getElementById('responseText').value=''" value="清空聊天记录">
</form>
<br>
<br>
<script type="text/javascript">
    function serverInit(){
//        $.ajax({
//            url: "/chat/initHttpChat",
//            async :false,
//            cache:false,
//            type:'get',
//            dataType: 'jsonp',
//            data:{
//                userName : "234"
//            },
//            success:function(dataSso){
//                alert( "ok ");
//            }
//        })
    }
</script>

</body>
</html>
