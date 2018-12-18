package com.unisound.iot.dao.mapper.source1.topic;

import com.unisound.iot.common.modle.Topic;
import com.unisound.iot.common.modle.TopicData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专题持久化操作
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
@Mapper
public interface TopicDao {
    /**
     * 查询专题主表信息
     * @param topicId
     * @return
     */
    Topic findTopicDetail( @Param("topicId") Long topicId );

    /**
     * 查询专题子表
     * @param topicId
     * @return
     */
    List<TopicData> findTopicDataDetail( @Param("topicId")Long topicId );
}
