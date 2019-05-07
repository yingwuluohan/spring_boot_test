<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP ‘index.jsp‘ starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta content="text/html";charset="utf-8">
    <script src="<%=request.getContextPath()%>/js/jquery-2.1.4.min.js" ></script>
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
</head>

<body>
<form>
    <h1>WebSocket送信实例</h1>
    <input type="text" id="message"  disabled="disabled"  />
    <input type="button" onclick="echo()" disabled="disabled" value="提交">
</form>
</body>
<script>
    if (!window.WebSocket && window.MozWebSocket)
        window.WebSocket = window.MozWebSocket;
    if (!window.WebSocket) {
        alert("此浏览器不支持WebSocket");
    }
    //创建WebSocket,location.host获得主机名+端口号
    var ws = new WebSocket("ws://" + location.host + "/websocket/websocket");
    //连接建立后调用的函数
    ws.onopen = function() {
        //将我们的form改变为可以输入的形式
        $("form *").attr("disabled", false);
    }
    //接受服务器传入的数据的处理
    ws.onmessage = function(event) {
        alert(event.data);
    }
    //点击提交按钮后调用的参数
    function echo() {
        ws.send($("#message").val());
    }
</script>
</html>