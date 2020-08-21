package com.unisound.iot.controller.jdk.DirectByteBuffer;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryLocat   {


    private static Direct direct;



    public PhantomReference setReference(Direct direct ){
        ReferenceQueue referenceQueue= new ReferenceQueue<>();

        PhantomReference phantomReference= new PhantomReference( direct ,referenceQueue);
        phantomReference.get();
        return phantomReference;
    }

    public static final BigDecimal zero = BigDecimal.ZERO.setScale(4,BigDecimal.ROUND_HALF_UP);
    public static final BigDecimal zero1 = new BigDecimal( 0 );
    public static void main(String[] args) {
//        MemoryLocat me = new MemoryLocat();
//        direct = new Direct();
//        direct.setId( 2 );
//        direct.setName( "name" );
//        PhantomReference reference = me.setReference( direct );
//        System.out.println( reference.get() );
//        System.out.println( "结果:" + ((Direct)reference.get()).getName() );

            System.out.println(  zero.floatValue() == zero1.floatValue()  );
            System.out.println(  zero.floatValue() == 0  );
        List<BigDecimal> list = new ArrayList<>();
        list.add( new BigDecimal( 0.123));
        list.add( new BigDecimal( 0.723));
        list.add( new BigDecimal( 0.023));
        list.add( new BigDecimal( 0.523));
        list.add( new BigDecimal( 0.003));
        list.add( new BigDecimal( 0.00));
//        System.out.println(list);//执行结果：[4, 2, 5, 3, 1]
        //升序
        Collections.reverse(list);
        BigDecimal tmp = new BigDecimal( 0.001);
        BigDecimal[] array = new BigDecimal[ list.size() ];
        for( int i = 0 ; i< list.size();i++ ){
            array[ i ]= list.get( i );
        }
        for( int i = 0 ; i< array.length ;i++ ){
            for( int j = array.length-1 ; j > i;j--){
                if( array[ i ].floatValue() < array[ j ].floatValue()){
                    tmp = array[ i ];
                    array[ i ] = array[ j ];
                    array[ j ] = tmp;
                }
            }
        }
        System.out.println( isNumeric( "9009" ) );//执行结果：[1, 2, 3, 4, 5]
        System.out.println( isNumeric( "901p09" ) );//执行结果：[1, 2, 3, 4, 5]

//        System.out.println( "result:" + ( tmp.compareTo( new BigDecimal(0 )) < 0 ));
    }



    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
