package com.self.distribut.service;


import com.self.distribut.Common.entity.Distribut;
import com.self.distribut.dao.DistributDao;
import com.self.distribut.txtransactional.annotation.TxTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DistributService {

    @Autowired
    private DistributDao distributDao;


    @Transactional
    @TxTransactional( isStart = true )
    public void addInfo(){
        Distribut distribut = new Distribut();
        distributDao.insertInfo( distribut );
        //TODO 调用其他系统rest接口
        int i = 100 / 0;

    }
}
