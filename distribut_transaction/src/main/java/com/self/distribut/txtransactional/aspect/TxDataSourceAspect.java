package com.self.distribut.txtransactional.aspect;


import com.self.distribut.txtransactional.connection.TxConnection;
import com.self.distribut.txtransactional.transactionnal.TxTransaction;
import com.self.distribut.txtransactional.transactionnal.TxTransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class TxDataSourceAspect {

    @Around( "execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around( ProceedingJoinPoint point){

        try {
            Connection connection = (Connection) point.proceed();
            //LCN 不生 两个方法在同一个事务里，用什么方法进行事务的传递
            TxTransaction txTransaction = TxTransactionManager.getCurrent();

            return new TxConnection( connection ,txTransaction );
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return null;
    }

}
