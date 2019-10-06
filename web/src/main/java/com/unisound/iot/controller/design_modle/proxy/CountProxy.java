package com.unisound.iot.controller.design_modle.proxy;
/** 
* @author  dfn  : 
* @date 创建时间：2015年12月31日 下午6:08:34 
* @version 1.0 
* @parameter   
* @return  
*/
public class CountProxy implements Count {  
    private CountImpl countImpl;  
  
    /** 
     * 覆盖默认构造器 
     *  
     * @param countImpl 
     */  
    public CountProxy(CountImpl countImpl) {  
        this.countImpl = countImpl;  
    }  
  
    @Override  
    public void queryCount() {  
        System.out.println("事务处理之前");  
        // 调用委托类的方法;  
        //countImpl.queryCount();
        System.out.println("事务处理之后");  
    }  
  
    @Override  
    public void updateCount() {  
        System.out.println("事务处理之前");  
        // 调用委托类的方法;  
        countImpl.updateCount();  
        System.out.println("事务处理之后");  
  
    } 

}
