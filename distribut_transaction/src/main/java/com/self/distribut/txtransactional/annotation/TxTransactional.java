package com.self.distribut.txtransactional.annotation;


import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE })
public @interface TxTransactional {


    boolean isStart() default true;
    boolean isEnd() default true;


}
