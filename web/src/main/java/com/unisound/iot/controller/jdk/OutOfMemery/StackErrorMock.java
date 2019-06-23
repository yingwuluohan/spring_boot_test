package com.unisound.iot.controller.jdk.OutOfMemery;

/**
 * @Created by yingwuluohan on 2019/6/12.
 * @Company 北京云知声技术有限公司
 */
public class StackErrorMock {

    private static int index = 1;

    public void call(){
        index++;
        call();
    }

    public static void main(String[] args) {
        StackErrorMock mock = new StackErrorMock();
        try {
            mock.call();
        }catch (Throwable e){
            System.out.println("Stack deep : "+index);
            e.printStackTrace();
        }
    }
}
