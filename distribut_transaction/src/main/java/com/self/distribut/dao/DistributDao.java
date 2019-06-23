package com.self.distribut.dao;


import com.self.distribut.Common.entity.Distribut;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DistributDao {


    void insertInfo( Distribut distribut );

}
