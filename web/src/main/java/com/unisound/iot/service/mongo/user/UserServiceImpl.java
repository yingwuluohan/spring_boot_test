package com.unisound.iot.service.mongo.user;

import com.unisound.iot.common.mongo.User;
import com.unisound.iot.dao.mongo.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company 北京云知声技术有限公司
 */
@Service("userService")
public class UserServiceImpl {

    @Autowired
    private UserDao userDao;

    /**
     * 查找全部
     */
    public List<User> findAll() {
        return userDao.findAll( );
    }

    /**
     * 根据id得到对象
     */
    public User getUser(String id) {
        Criteria criteria = Criteria.where("userid").is(id);
        Query query = new Query(criteria);
        List< User > list = userDao.find( query );

        return null;
    }

    /**
     * 插入一个用户
     */
    public void insert(User user) {
        userDao.save(user);
    }

    /**
     * 根据id删除一个用户
     */
    public void remove(Integer id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        userDao.delete( query );
    }


}
