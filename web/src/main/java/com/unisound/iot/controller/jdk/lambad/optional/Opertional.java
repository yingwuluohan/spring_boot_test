package com.unisound.iot.controller.jdk.lambad.optional;


import com.alibaba.fastjson.JSON;
import com.unisound.iot.controller.jdk.lambad.lambda_function.Modle;
import com.unisound.iot.controller.jdk.lambad.lambda_function.User;

import java.util.*;

/**
 * public final class Optional<T> {
 *     //Null指针的封装
 *     private static final java.util.Optional<?> EMPTY = new java.util.Optional<>();
 *
 *     //内部包含的值对象
 *     private final T value;
 *
 *     private Optional() ;
 *     //返回EMPTY对象
 *     public static<T> java.util.Optional<T> empty() ;
 *
 *     //构造函数，但是value为null，会报NPE
 *     private Optional(T value);
 *
 *     //静态工厂方法，但是value为null，会报NPE
 *     public static <T> java.util.Optional<T> of(T value);
 *
 *     //静态工厂方法，value可以为null
 *     public static <T> java.util.Optional<T> ofNullable(T value) ;
 *
 *     //获取value，但是value为null，会报NoSuchElementException
 *     public T get() ;
 *
 *     //返回value是否为null
 *     public boolean isPresent();
 *
 *     //如果value不为null，则执行consumer式的函数，为null不做事
 *     public void ifPresent(Consumer<? super T> consumer) ;
 *
 *      //过滤，如果value不为null，则根据条件过滤，为null不做事
 *     public java.util.Optional<T> filter(Predicate<? super T> predicate) ;
 *
 *      //map 转换，在其外面封装Optional，如果value不为null，则map转换，为null不做事
 *     public<U> java.util.Optional<U> map(Function<? super T, ? extends U> mapper);
 *
 *      //转换，如果value不为null，则map转换，为null不做事
 *     public<U> java.util.Optional<U> flatMap(Function<? super T, java.util.Optional<U>> mapper) ;
 *
 *     //value为null时，默认提供other值
 *     public T orElse(T other);
 *
 *       //value为null时，默认提供other值
 *     public T orElseGet(Supplier<? extends T> other);
 *
 *       //value为null时，默认提供other值
 *     public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) ;
 */
public class Opertional {

    private static User user;
    private static List< Integer > list = Arrays.asList( 1 ,2,3,4,5,6,7 );

    /**
     * flatMap
     *
     */
    public static Optional<String> getOutputOpt(String input) {
        return input == null ? Optional.empty() : Optional.of("output for " + input);
    }
    static String getOutput(String input) {
        return input == null ? null : "output for " + input;
    }
    public void getMap(){
        Optional<String> s = Optional.of("input");
        System.out.println(s.map(Opertional::getOutput));
        System.out.println(s.flatMap(Opertional::getOutputOpt));
//        System.out.println(s.flatMap( Optional.of("output for " ) ));
    }


    public static void getConnection(){
        user = new User();
        user.setId( 11 );
        user.setList( list );
         Optional.ofNullable( user.getId() )
                .map( ( x ) ->  new Integer( 1 ) )
                .filter( ( x ) -> user.getId() >111    )
                .ifPresent(  ( x ) -> user.getId() );
         Optional<Integer> num = Optional.ofNullable( user.getId() )
                .map( ( x ) ->  new Integer( 1 ) )
                .filter( ( x ) -> user.getId() >1    );
        System.out.println( num.orElse( null ) );
    }

    public static Map getCollect(){
        Map< Integer , Object > map = new HashMap<>();
        //TODO 如果返回结果为空 则用 “orElseGet ”生成一个默认值
        map.put( 1 ,123 );
        map.put( 2 ,1234 );
        map.put( 3 ,12345 );
        Optional<Object > map2 =  Optional.ofNullable(map).map(r -> r.get( 1 ));//.orElseGet(HashMap::new);

        System.out.println( map2 );

        return map ;
    }

    public static User getOfNullAble(){
        User user = new User();
        int  u = Optional.ofNullable( user ).orElseThrow(() -> new RuntimeException("")).getId();
        System.out.println( " ofNullAble 返回对象 get : " + u );
        return user;
    }


    public static void main(String[] args) {
        getConnection();
        Map map = getCollect();
        System.out.println( map );
        getOfNullAble();
        User user = null;
        if( !Optional.ofNullable( user ).map(  per -> per.getId() == 1  ).orElse( false )){
            System.out.println( "1" +1 );
        }else{
            System.out.println( "2 " );
        }
        User user1 = new User();
        Modle modle = new Modle();

        user1.setId( 1 );
        modle.setCode( "234");
        modle.setNo( "2");

        user1.setModle( modle );

        String  l = JSON.toJSON( user1 ).toString();

        System.out.println( l );
    }


}
