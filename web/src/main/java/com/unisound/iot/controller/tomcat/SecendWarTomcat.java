package com.unisound.iot.controller.tomcat;


import com.unisound.iot.controller.tomcat.projectLoder.ProjectLoader;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 读取实例的war包
 *
 * 根据请求的URL 执行 响应的servlet
 */
public class SecendWarTomcat {

    private static ExecutorService executor = new ThreadPoolExecutor( 4,
            4 ,2000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10)  );





    public static void main(String[] args ) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        //1. 加载项目
        Map<String ,ProjectLoader.ProjectConfigInfo > projectConfigInfoMap = null;//ProjectLoader.load();



        ServerSocket serverSocket = new ServerSocket( 8088 );
        System.out.println( "server start ****************************" );
        while( !serverSocket.isClosed() ){
            Socket request = serverSocket.accept();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println( "开始接收数据 " );
                    try {
                        InputStream inputStream = request.getInputStream();
                        System.out.println( "接收请求 " );
                        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ));
                        String msg = "";
                        StringBuffer sb = new StringBuffer();
                        while ( (msg = reader.readLine()) != null ){
                            if( msg.length() == 0 ){
                                break;
                            }
                            sb.append( msg ).append("\r\n");

                        }
                        System.out.println( "data:" +sb.toString() );
                        //TODO  给浏览器一个响应
                        byte[]  response = "成功接收到请求，这里是Tomcat服务器".getBytes();
                        OutputStream outputStream = request.getOutputStream();
                        //TODO 必须按照http协议返回浏览器才能读的到返回数据
                        outputStream.write( "HTTP/1.1 200 OK \r\n".getBytes() );
                        outputStream.write( "Content-Type: text/html;charset=UTF-8 \r\n".getBytes() );
                        outputStream.write( ("Content-Length: 20480 \r\n").getBytes() );

                        outputStream.write( response );
                        outputStream.flush();
                        // 根据URL 匹配具体的servlet请求 ，即需要解析request http请求的协议
                        /**
                         * data:GET / HTTP/1.1
//                         Host: localhost:8088
//                         User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:67.0) Gecko/20100101 Firefox/67.0
//                         Accept: text/html,application/xhtml+xml,application/xml;q=0.9,;q=0.8
//                         Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2
//                         Accept-Encoding: gzip, deflate
//                         Connection: keep-alive
//                         Cookie: JSESSIONID=4EC457D49C01A9193C12E3EAE399892A
//                         Upgrade-Insecure-Requests: 1
                         **/
                        String firstLine = sb.toString().split("\r\n" )[ 0 ];
                        System.out.println( "firstLine:" + firstLine );
                        String projectName = firstLine.split( " " )[ 1 ].split( "/" )[ 1 ];
                        String servletPath = firstLine.split( " " )[ 1 ].replace( "" + projectName , "" );
                        String servletName = projectConfigInfoMap.get( projectName ).servletsMapping.get( servletPath );
                        Servlet servlet = projectConfigInfoMap.get(projectName ).servletsInstances.get( servletName );

                        ServletRequest request1 = createRequest();
                        ServletResponse response1 = createResponse();

                        try {
                            servlet.service( request1 , response1 );
                        } catch (ServletException e) {
                            e.printStackTrace();
                        }

                        System.out.println( "结束 " );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            request.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });

        }




    }



    private static HttpServletRequest createRequest(){
        return new HttpServletRequest() {
            @Override
            public String getAuthType() {
                return null;
            }

            @Override
            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            @Override
            public long getDateHeader(String name) {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return null;
            }

            @Override
            public int getIntHeader(String name) {
                return 0;
            }

            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public String getPathInfo() {
                return null;
            }

            @Override
            public String getPathTranslated() {
                return null;
            }

            @Override
            public String getContextPath() {
                return null;
            }

            @Override
            public String getQueryString() {
                return null;
            }

            @Override
            public String getRemoteUser() {
                return null;
            }

            @Override
            public boolean isUserInRole(String role) {
                return false;
            }

            @Override
            public Principal getUserPrincipal() {
                return null;
            }

            @Override
            public String getRequestedSessionId() {
                return null;
            }

            @Override
            public String getRequestURI() {
                return null;
            }

            @Override
            public StringBuffer getRequestURL() {
                return null;
            }

            @Override
            public String getServletPath() {
                return null;
            }

            @Override
            public HttpSession getSession(boolean create) {
                return null;
            }

            @Override
            public HttpSession getSession() {
                return null;
            }

            @Override
            public boolean isRequestedSessionIdValid() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            @Override
            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            @Override
            public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
                return false;
            }

            @Override
            public void login(String username, String password) throws ServletException {

            }

            @Override
            public void logout() throws ServletException {

            }

            @Override
            public Collection<Part> getParts() throws IOException, IllegalStateException, ServletException {
                return null;
            }

            @Override
            public Part getPart(String name) throws IOException, IllegalStateException, ServletException {
                return null;
            }

            @Override
            public Object getAttribute(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public void setCharacterEncoding(String env) throws UnsupportedEncodingException {

            }

            @Override
            public int getContentLength() {
                return 0;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getParameter(String name) {
                return null;
            }

            @Override
            public Enumeration<String> getParameterNames() {
                return null;
            }

            @Override
            public String[] getParameterValues(String name) {
                return new String[0];
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return null;
            }

            @Override
            public String getProtocol() {
                return null;
            }

            @Override
            public String getScheme() {
                return null;
            }

            @Override
            public String getServerName() {
                return null;
            }

            @Override
            public int getServerPort() {
                return 0;
            }

            @Override
            public BufferedReader getReader() throws IOException {
                return null;
            }

            @Override
            public String getRemoteAddr() {
                return null;
            }

            @Override
            public String getRemoteHost() {
                return null;
            }

            @Override
            public void setAttribute(String name, Object o) {

            }

            @Override
            public void removeAttribute(String name) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return null;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public RequestDispatcher getRequestDispatcher(String path) {
                return null;
            }

            @Override
            public String getRealPath(String path) {
                return null;
            }

            @Override
            public int getRemotePort() {
                return 0;
            }

            @Override
            public String getLocalName() {
                return null;
            }

            @Override
            public String getLocalAddr() {
                return null;
            }

            @Override
            public int getLocalPort() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public AsyncContext startAsync() {
                return null;
            }

            @Override
            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) {
                return null;
            }

            @Override
            public boolean isAsyncStarted() {
                return false;
            }

            @Override
            public boolean isAsyncSupported() {
                return false;
            }

            @Override
            public AsyncContext getAsyncContext() {
                return null;
            }

            @Override
            public DispatcherType getDispatcherType() {
                return null;
            }
        };
    }

    private static HttpServletResponse createResponse(){
        return new HttpServletResponse() {
            @Override
            public void addCookie(Cookie cookie) {

            }

            @Override
            public boolean containsHeader(String name) {
                return false;
            }

            @Override
            public String encodeURL(String url) {
                return null;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return null;
            }

            @Override
            public String encodeUrl(String url) {
                return null;
            }

            @Override
            public String encodeRedirectUrl(String url) {
                return null;
            }

            @Override
            public void sendError(int sc, String msg) throws IOException {

            }

            @Override
            public void sendError(int sc) throws IOException {

            }

            @Override
            public void sendRedirect(String location) throws IOException {

            }

            @Override
            public void setDateHeader(String name, long date) {

            }

            @Override
            public void addDateHeader(String name, long date) {

            }

            @Override
            public void setHeader(String name, String value) {

            }

            @Override
            public void addHeader(String name, String value) {

            }

            @Override
            public void setIntHeader(String name, int value) {

            }

            @Override
            public void addIntHeader(String name, int value) {

            }

            @Override
            public void setStatus(int sc) {

            }

            @Override
            public void setStatus(int sc, String sm) {

            }

            @Override
            public int getStatus() {
                return 0;
            }

            @Override
            public String getHeader(String name) {
                return null;
            }

            @Override
            public Collection<String> getHeaders(String name) {
                return null;
            }

            @Override
            public Collection<String> getHeaderNames() {
                return null;
            }

            @Override
            public String getCharacterEncoding() {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return null;
            }

            @Override
            public PrintWriter getWriter() throws IOException {
                return null;
            }

            @Override
            public void setCharacterEncoding(String charset) {

            }

            @Override
            public void setContentLength(int len) {

            }

            @Override
            public void setContentType(String type) {

            }

            @Override
            public void setBufferSize(int size) {

            }

            @Override
            public int getBufferSize() {
                return 0;
            }

            @Override
            public void flushBuffer() throws IOException {

            }

            @Override
            public void resetBuffer() {

            }

            @Override
            public boolean isCommitted() {
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setLocale(Locale loc) {

            }

            @Override
            public Locale getLocale() {
                return null;
            }
        };
    }

}
