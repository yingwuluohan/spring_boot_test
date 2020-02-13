package com.unisound.iot.controller.jdk.lambad;


import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.*;

/**
 *  1.1. 对象 :: 实例方法名
 * 1.2. 类 :: 静态方法名
 * 注意事项：lambda体中调用方法的参数列表与返回值类型 要与函数式接口中的方法的函数列表和返回值类型保持一致才可以使用。
 * 1.3. 类 :: 实例方法名
 * 注意事项：如果lambda 参数列表中第一个参数是实例方法的调用者，第二个参数是实例方法的参数时可以使用该格式
 * 可以用：  ClassName :: method
 */
public class MethodMaohao {

    //1
    public void tersObject(){
        PrintStream ps = System.out;
        Consumer< String > con = ( x ) -> ps.println( x );
        //可以转换成
        Consumer<String > con1 = ps::println;
        con1.accept( "test" );
        //

    }

    //2
    public void testClass(){
        Comparator<Integer > com = ( x , y ) -> Integer.compare( x, y );
        //可以转换成
        Comparator< Integer > com1 = Integer::compareTo;
    }


    //3
    //TODO 构造器的参数列表与接口中方法的参数列表一样就可以这样使用
    public void construct(){
        Supplier<User> sup = () -> new User( 1 ,"" );

        Supplier< User > sup2 = User::new;
        User user = sup2.get();

    }
    //两个参数的构造
    public void towConstruct(){
        Function< String ,User > fun = ( x ) -> new User( );
        Function< Integer ,User > fun1 = User::new;
        BiFunction< Integer , Integer , User > fun2 = User::new ;

        Supplier<HashMap> map = HashMap::new;
        Supplier<HashMap> map2 = () -> new HashMap();

        BiConsumer< HashMap,HashMap  > biConsumer = HashMap::putAll;
        BiConsumer< HashMap,HashMap  > biConsumer2 = ( x ,y ) -> y.putAll(new HashMap<>() )  ;
    }


    //   定义一个方法，参数传递String类型对的数组和两个Consumer接口，泛型使用String
    public static void printInfo(String[] arr, Consumer<String> con1,Consumer<String> con2){
//        遍历字符串数组
        for (String message : arr) {
//            使用andThen方法连接两个Consumer接口，消费字符串
            con1.andThen(con2).accept(message);
            System.out.println( "con1:"+con1 );
        }
    }

    public static void main(String[] args) {
        String[] array = new String[]{ "e" , "a","sa","da" };
        printInfo( array , x-> x .endsWith( "a" ) , y -> y.indexOf( "a" ));


    }




}
