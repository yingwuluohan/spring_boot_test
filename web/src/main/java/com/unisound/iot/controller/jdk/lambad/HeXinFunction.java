package com.unisound.iot.controller.jdk.lambad;


import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ．Java lambda内置核心接口
 * 1. Consumer < T >   ：消费型接口
 *   Void accept( T t );
 *
 * 2. Supplier< T >   ： 供给型接口
 *   T get();
 *
 * 3. Function < T , R >  : 函数式接口
 *   R  apply (  T t )
 *
 * 4. Predicate < T  > : 断言型接口
 */
public class HeXinFunction {


    //TODO 消费型接口
    public void happy(Integer num , Consumer< Integer > money ){
        money.accept( num );
    }
    public Integer getNum(){
        happy( 100 , ( x )-> System.out.println( x ) );
        return 1;
    }
    //TODO 例子2 ： 产生整数
    public static List< Integer > getList(int num , Supplier< Integer > supplier ){
        List< Integer > list = new ArrayList<>( );
        for( int i =0;i < num ;i++ ){
            list.add( supplier.get() );
        }
        return list;
    }

    public static  void supplierTest(){
        List list = getList( 10 , (   )-> ( int )(Math.random() * 100 ) );
        System.out.println("list: " + list.size() );
    }
    public static void main(String[] args) {
        supplierTest();
    }
    //TODO Function 例子
    public String functionTest(String str , Function< String , String > func ){
        return func.apply( str );
    }

    public void testFunc(){
        String result = functionTest( " test " , ( x ) -> x.trim() + "ds"  );
        System.out.println( result );
    }


    //TODO 方法引用
    public void test(){
        User user = new User( 1 , "2" );
        Supplier< String > sup = () -> user.getName();
        String str = sup.get();

        //上面可以替换成
        Supplier< String > sup2 = user::getName;
    }














}
