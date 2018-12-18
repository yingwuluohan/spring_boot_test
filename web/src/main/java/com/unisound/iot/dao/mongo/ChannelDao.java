package com.unisound.iot.dao.mongo;

import com.unisound.iot.common.mongo.Channel;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company 北京云知声技术有限公司
 */
@Repository
public class ChannelDao extends MongoBaseDao<Channel>  {


    @Override
    public void save(Channel channel ){
        insert( channel );
    }

    public List<Channel> findChannelById(Query query ){
        List<Channel> channel = find( query );
        return channel;
    }


}
