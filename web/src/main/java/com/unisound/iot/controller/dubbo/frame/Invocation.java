package com.unisound.iot.controller.dubbo.frame;

/**
 * @Created by yingwuluohan on 2019/8/2.
 * @Company
 * 客户端调用对象
 */
public class Invocation {


    private String interfaceName;
    private String methodName;
    /** 参数类型*/
    private Class[] paramsTypes;
    /** 参数 */
    private Object[] params;


    public Invocation(String interfaceName, String methodName, Class[] paramsTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramsTypes = paramsTypes;
        this.params = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
