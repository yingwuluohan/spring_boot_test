package com.unisound.iot.controller.jdk.OutOfMemery;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * 运行时常量池是方法区的一部分，他们都属于HotSpot虚拟机中的永久代内存区域。
 * 方法区用于存放Class的相关信息，Java的反射和动态代理可以动态产生Class，
 * 另外第三方的CGLIB可以直接操作字节码，也可以动态产生Class，实验通过CGLIB来演示
 */
@RestController
@RequestMapping("memoryold/")
public class HeapMethodOldController {

    /**
     * memoryold/v1
     *
     */
    @ResponseBody
    @RequestMapping(value = "v1" ,method = {RequestMethod.GET, RequestMethod.POST})
    public static void testMethod( ){

        while(true){

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor(){
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)throws Throwable{
                    Object objProxy = proxy.invokeSuper(obj, args);

                    return objProxy;
                }
            });
            Class[] classes = new Class[ ]{ enhancer.createClass() };
            Object[] objects = new Object[]{"1"};
            enhancer.create( classes , objects );
        }

    }
    class OOMObject{

        public OOMObject(String name ){
            this.name = name;
        }

        private String name;
        private Long num;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getNum() {
            return num;
        }

        public void setNum(Long num) {
            this.num = num;
        }
    }

    public static void main(String[] args) {
        testMethod( );
    }
}
