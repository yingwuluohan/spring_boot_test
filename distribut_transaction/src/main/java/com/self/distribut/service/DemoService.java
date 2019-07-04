package com.self.distribut.service;


import com.self.distribut.dao.DemoDao;
import com.self.distribut.txtransactional.annotation.TxTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {


    @Autowired
    private DemoDao demoDao;


    @Transactional
    @TxTransactional( isStart = true )
    public void test(){
        demoDao.insert( "" , "" );
//        HttpClient.

        int i = 100/0;
    }















}
