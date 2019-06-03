package com.unisound.iot.controller.jdk.OutOfMemery;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by yingwuluohan on 2019/6/1.
 * @Company 北京云知声技术有限公司
 *
 * java.lang.OutOfMemoryError: GC overhead limit exceeded
 */
@RestController
@RequestMapping("outmemoryheap/")
public class OutMemoryJavaHeapSpace {


    private List<OutMemoryJavaHeapSpace.User> list = new ArrayList<>();
    private List< Class > noList = new ArrayList<>();

    //memory/v1
    @ResponseBody
    @RequestMapping(value = "v1" ,method = {RequestMethod.GET, RequestMethod.POST})
    public String findAlbumList( ){

        System.out.println( "********** memery begin *********" );
        heap();

        return "ok";
    }

    public static void main(String[] args) {
        OutMemoryJavaHeapSpace out = new OutMemoryJavaHeapSpace();
        out.heap();
    }

    /**
     *  堆内存溢出
     * java.lang.OutOfMemoryError: Java heap space
     */

    public void heap(){
        int i = 0 ;
        while(true){
            i++;
            OutMemoryJavaHeapSpace.User user = new OutMemoryJavaHeapSpace.User();
            user.setAge( i );
            user.setName( i+ "testNsame");
            user.setNum( i );
            list.add( user );

        }

    }

    //非堆内存溢出：（ class ）
    public void noHeap(){
        int i = 0 ;
        while(true){
            i++;


        }

    }





    class User{

        private String name;
        private int age;
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
