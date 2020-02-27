package com.unisound.iot.controller.jdk.lambad.stream;

import com.unisound.iot.controller.jdk.lambad.Modle.Modle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Created by yingwuluohan on 2019/7/16.
 * @Company fn
 */
public class Filter_map_count_limit {


    private Stream< String > stringStream = Stream.of( "" ,"" ,"" ,"" ,"" );

    public void fileter(){
        /**
         * 备注：stream是管道流，只能使用一次，第一个stream流调用完方法后，
         * 数据就会流转到下一个stream上
           上一个stream流使用一次后就会关闭掉。
         */
        Stream<String> streamresult = stringStream.
                filter( (String name ) -> { return name.startsWith( "d" );});
        Stream<String> result = streamresult.filter( list -> list.startsWith( "" ));

    }
    /**
     * map：主要是改变集合的数据结构
     * Stream < R > map( Function< ? Super T ,? Extends R > mapper );
     *
     Function 函数式接口，只有一个抽象方法： apply（ T t ） ,即可以将T种类型转换成R类型，即映射
     */
    public void map(){
        //第一步 stream.map( () -> {} );
        Stream<Integer > set = stringStream.map( ( String str ) -> {
            return Integer.valueOf( str );
        });

    }

    /**
     * 案例：
     * 1将两个list集合合并
     * 2把里面的数据封装到对象中
     * 3.打印对象信息
     *
     */
    public void togatherListChangeObject(){
        List<String > list1 = new ArrayList<>();
        List<String > list2 = new ArrayList<>();
        list1.add( "2" );
        list1.add( "3" );
        list2.add( "4" );
        list2.add( "5" );
        list2.add( "6" );
        Stream.concat( list1.stream() ,list2.stream() ).
                map(  name -> new Modle( name )).
                forEach( modle-> System.out.println( modle.getName() ));

    }














}
