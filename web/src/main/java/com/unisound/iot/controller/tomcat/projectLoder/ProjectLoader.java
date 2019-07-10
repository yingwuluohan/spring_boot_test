package com.unisound.iot.controller.tomcat.projectLoder;


import javax.servlet.Servlet;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 加载项目class文件
 */
public class ProjectLoader {

    /**
     * 遍历指定的文件夹
     */
    public static Map<String ,ProjectConfigInfo > load() throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {

        /** 记录每个项目project 信息的加载*/
        Map<String ,ProjectConfigInfo > projectConfigInfoMap = new HashMap<>();

        //读取文件
        String fileUrl = "/application/apache-tomcat-9.0.20/webapps/ROOT";
//        File[] projects = new File( fileUrl ).listFiles( file -> file.isDirectory() );
        File[] projects = new File( fileUrl ).listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        //TODO
        for( File projectFile : projects ){
            // 获取项目中有多少servlet ： 读取配置文件，获取读取注解
            ProjectConfigInfo projectConfigInfo = null ; // 解析xml 文件
            //获取了类文件后，需要实例化类 ,加载class文件
            projectConfigInfo.loadServlet();
            projectConfigInfoMap.put( projectFile.getName() , projectConfigInfo );

        }
        return projectConfigInfoMap;

    }

    public class ProjectConfigInfo{


        public String projectPath = null;
        //TODO 把web.xml 解析成对象
        /** servlet class 信息 */
        public Map<String ,Object > servlets = new HashMap<>();

        /** Url 映射信息 */
        public Map<String ,String > servletsMapping = new HashMap<>();
        /** 保存servlet 的路径 */
        public Map<String ,Servlet > servletsInstances = new HashMap<>();



        /** */
        public void loadServlet() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
            //利用JDK ,类加载工具，需要加载外部类的路径
            URL classFile = new URL( "" + projectPath + "\\WEB-INF\\class\\");
            URLClassLoader urlClassLoader = new URLClassLoader( new URL[]{ classFile}  );
            //遍历项目里面的servlet
            Set<Map.Entry<String , Object >> entits = this.servlets.entrySet();
            for( Map.Entry<String , Object > entity :entits ){
                String servletName = entity.getValue().toString();
                String servletClassName = entity.getValue().toString();
                //1. 把class加载到JVM
                Class<?> classes = urlClassLoader.loadClass( servletClassName );
                //2.创建对象实例
                Servlet servlet = (Servlet) classes.newInstance();

                /** servlet 协议规范 , */
                // 3.最后存储servlet ,用于可以根据URL调用servlet -------servlet 容器
                servletsInstances.put( servletName , servlet );

            }


        }




    }









}
