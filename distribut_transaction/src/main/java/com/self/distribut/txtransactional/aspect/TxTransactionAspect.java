package com.self.distribut.txtransactional.aspect;


import com.self.distribut.txtransactional.annotation.TxTransactional;
import com.self.distribut.txtransactional.transactionnal.TransactionType;
import com.self.distribut.txtransactional.transactionnal.TxTransaction;
import com.self.distribut.txtransactional.transactionnal.TxTransactionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class TxTransactionAspect implements Ordered{


    @Around("@annotation(com.self.distribut.txtransactional.annotation.TxTransactional)")
    public void invoke(ProceedingJoinPoint point ){

        MethodSignature signature =(MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        TxTransactional txTransactional = method.getAnnotation(TxTransactional.class);

        System.out.println( "Aspect:"+ txTransactional.isStart() );

        String groupId = "";
        if( txTransactional.isStart() ){
            groupId = TxTransactionManager.createTxTransactionGroup();
        }
        TxTransaction txTransaction = TxTransactionManager.createLbTransaction( groupId );

        try {
            // 走spring的逻辑 ，比spring优先级低
            point.proceed();

            TxTransactionManager.addLbTransaction( txTransaction , txTransactional.isEnd() , TransactionType.comit);
        } catch (Throwable throwable) {
            TxTransactionManager.addLbTransaction( txTransaction , txTransactional.isEnd() ,TransactionType.rollback );
            throwable.printStackTrace();
        }

    }


    @Override
    public int getOrder() {
        return 10000;
    }
}
