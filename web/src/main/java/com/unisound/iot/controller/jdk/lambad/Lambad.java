package com.unisound.iot.controller.jdk.lambad;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Lambad {


    public static <T,U,R> Map<U,T> transHashMapFromList(List<R> list, Function<R,U> keyFunction,
                                                        Function<R,T> valueFunction){
        if(list == null){
            return Maps.newHashMap();
        }
        list.stream().collect( () -> new ArrayList< >() , ( k , v ) -> k.add( keyFunction.apply( v )) ,  ( x , y ) -> x.add(  new ArrayList< >()));

        //
        list.stream().collect( (  )-> new HashMap() ,( k , v )-> k.put( keyFunction.apply( v ), valueFunction.apply(v) ),( x ,y ) -> x.putAll(new HashMap<>() ) );
        BiConsumer< U ,R > biConsumer = null;
        return list.stream().collect(HashMap::new , (m, v)->m.put(keyFunction.apply(v), valueFunction.apply(v)), HashMap::putAll);
    }


    public static void main(String[] args) {
        LambadApi result = () ->  ServiceImpl.getStr(
                ()-> ServiceImpl.getTest( "" ),
                "" );
        result.appliy();

        List< String > list = new ArrayList<>();
        list.add( "1" );
        list.add( "2" );
        list.add( "3" );
        list.stream().collect( HashMap::new,   ( m ,v ) -> m.put( "" ,"" ) , HashMap::putAll );


        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println( "" );
            }
        };
        Runnable r2 = () -> System.out.println( "" );
        //******************************
        TestParm testParm = ( x ) -> System.out.println( x );
        testParm.getIntValue( 1 );

        Paramtow paramtow = ( a, b ) -> String.valueOf( a + b );
        String result2 = paramtow.compare( 1, 2 );
        Paramtow paramtow2 = ( a, b ) -> {
            System.out.println( );
            return String.valueOf( a + b );
        };
    }
    //TODO 函数式接口 开始11
    public Integer callFunction( Integer num ){
        Integer value = functionInteger( 2 , ( x ) -> 2*x  );
        return value;
    }

    //利用函数式编程，接口的具体实现可以放在调用functionInteger方法时用lambda实现
    public Integer functionInteger( Integer in , Functions fun ){
        return fun.getValue( in );
    }
    //函数式接口
    interface Functions< T >{
        Integer getValue(Integer num);
    }
    //TODO 函数式接口 结束1
    interface Paramtow{
        String compare(int a, int b);
    }


    public interface TestApi{
        String getInfo();
        default int getValue(){
            System.out.println( "Test Api 执行" );
            return 1;
        }
    }

    public interface TestParm{
        void getIntValue(int key);
    }




}
