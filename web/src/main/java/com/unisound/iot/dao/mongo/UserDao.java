package com.unisound.iot.dao.mongo;

import com.unisound.iot.common.mongo.User;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company 北京云知声技术有限公司
 */
@Repository
public class UserDao extends MongoBaseDao<User> {


    public List<User> findUser(Query query ){
       return find( query );

    }







}
