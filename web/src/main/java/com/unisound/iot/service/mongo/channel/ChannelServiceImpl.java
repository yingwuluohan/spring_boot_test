package com.unisound.iot.service.mongo.channel;

import com.unisound.iot.common.mongo.Channel;
import com.unisound.iot.dao.mongo.ChannelDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/12/18.
 * @Company 北京云知声技术有限公司
 */
@Service("channelService")
public class ChannelServiceImpl {

    @Autowired
    private ChannelDao channelDao;

    public void addChannel( Channel channel ){
        channelDao.insert( channel );
    }

    public List<Channel> findChannel(String code ){
        Criteria criteria = Criteria.where("code").is(code );
        Query query = new Query(criteria);
        List<Channel >  channel = channelDao.findChannelById( query );
        return channel;
    }

}
