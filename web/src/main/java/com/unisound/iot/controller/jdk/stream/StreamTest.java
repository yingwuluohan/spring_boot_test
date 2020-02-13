package com.unisound.iot.controller.jdk.stream;


import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 操作步骤
 * 1. 创建stream
 * 2. 中间操作
 * 3. 终止操作
 */
public class StreamTest {


    private static List<User> list = Arrays.asList(
            new User( 1 ,"a" ),
            new User( 2 ,"b" ),
            new User( 3 ,"c" ),
            new User( 4 ,"d" ),
            new User( 5 ,"dd" )
    ) ;
    //创建stream
    public void creatStream(){
        //1.可以通过Collection 系列集合提供的stream 或者paralleStream()
        List< String > list = new ArrayList<>();
        Stream< String > stream = list.stream();

        //2. 也可以通过Arrays 数组中的静态方法stream() 获取数组
        User[] user = new User[ 10 ];
        Stream< User > stream2 = Arrays.stream( user );

        //3.通过Stream类中的静态方法of()
        Stream< String > stream3 = Stream.of( "a" ,"b" ,"c","d" );
        //4.创建无限流
        //迭代  : UnaryOperation 一元运算操作
        Stream < Integer > stream4 = Stream.iterate( 1, ( x )-> x + 2 );
        stream4.forEach( System.out::println  );
        stream4.limit( 10 );
        //生成
    }

    //TODO 对stream 流做中间操作：
    /**
     * 1. 筛选
     *  filter ： 接收lambda ，从中排除某些元素
     *  limit ：截断流，使得元素不超过给定数量
     *  skip( n ) : 跳过元素 ， 返回一个扔掉了前n个元素的流，若流中元素不足n个，则返回空与limit互补
     *  distinct ： 筛选，通过流所生成元素的hashcode（） 和equals() 去除重复元素
     */
    public static void streamTest(){

        //TODO filter 的参数是：Predicate<T>  里面有一个boolean 类型的test方法
        Stream< User > s = list.stream().filter( (x )-> {
            System.out.println( "zhcaozuo********" );
            return  x.getId() > 3;
        });
        //TODO 终止操作 ： 结果是惰性求值 ，上面的中间操作不会执行任何操作
        s.forEach( System.out::println );

        s.limit( 2 ).forEach( System.out::println );
    }

    //TODO
    /**
     * 映射：map ---接收 Lambda ，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被应用到每个元素上
     *       并将其映射成一个新的元素。
     *       flatMap ---接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个新流
     *       map的返回值一定是流stream
     *
     *       2. flatMap : 将流中的每个值
     */
    public static void maptest(){
        List< String > listt = Arrays.asList( "a" ,"b", "c" ,"d" ,"e" );
        //TODO 将流中的每个元素都执行一次下面的函数
        listt.stream().map( ( str ) -> str.toUpperCase() ).forEach( System.out:: println );


    }

    /**
     * 规约：
     *     reduce :将流中的元素反复结合起来，得到一个值
     *
     *
     *
     */
    public static void reduce(){
        List< Integer > listt = Arrays.asList( 1,2,3,4,5,6,7 );
        List< String > list2 = Arrays.asList( "a" ,"b", "c" ,"d" ,"e" );
        Integer sum =  listt.stream().reduce(  2 , ( x ,y ) -> x+y );
        String str = list2.stream().reduce( "" , ( x , y ) -> x + y );

        System.out.println( sum );
        System.out.println( str );
    }

    /**
     *
     */
    public static void collect(){
        Map<Integer, User > fieldDataMap = list.stream().collect(Collectors.toMap(  User::getId , Function.identity() ) );

        System.out.println( fieldDataMap );
    }

    public static void filter(){
        List< User > list = new ArrayList<>( );
        for( int i = 0 ;i< 5;i++ ){
            User user = new User();
            user.setId( i );
            user.setName( "name:" + i );
            list.add( user );
        }
        Map<Integer, User > fieldDataMap = list.stream().collect( Collectors.toMap( User::getId  , Function.identity() ));

        System.out.println( fieldDataMap );
    }

    /**
     *  ：
     *     collect : 将流转换成其他形式 ，接收一个collector接口的实现，用于给stream 中元素做汇总的方法
     *
     *
     *
     */
    public static void main(String[] args) {
//        streamTest();
//        maptest();
//        Stream < Integer > stream4 = Stream.iterate( 1, ( x )-> x + 2 );
//        stream4.limit( 10 ).forEach( System.out::println  );
        reduce();
    }









}
