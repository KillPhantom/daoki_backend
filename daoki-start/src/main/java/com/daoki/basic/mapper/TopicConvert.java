package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.request.UpdateTopicVO;
import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Alan
 * 2022-03-06
 * Description: a mapper interface for converting between the topic vo and topic do
 */
@Mapper
public interface TopicConvert {
    TopicConvert INSTANCE = Mappers.getMapper(TopicConvert.class);

    /**
     * convert the created topic vo to topic do
     * Note that some properties of topic do should be set in service layer
     * @param createTopicVO a topic vo when creating a new topic
     * @return incomplete topic do which will be completed in service layer
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "viewCount", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "gmtCreate", ignore = true),
            @Mapping(target = "extra", ignore = true)
    })
    Topic createVo2Do(CreateTopicVO createTopicVO);

    /**
     * convert the updated topic vo to topic do
     * Note that some properties of topic do should be set in service layer
     * @param updateTopicVO a topic vo when updating a new topic
     * @return incomplete topic do which will be completed in service layer
     */
    @Mappings({
            @Mapping(target = "id", source = "topicId"),
            @Mapping(target = "viewCount", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "gmtCreate", ignore = true),
            @Mapping(target = "extra", ignore = true)
    })
    Topic updateVo2Do(UpdateTopicVO updateTopicVO);


    /**
     * convert the topic do to topic vo
     * Note that some properties of topic vo should be set in service layer
     * @param topic topic do searched in database
     * @return incomplete topic vo will be completed in service layer
     */
    @Mappings({
            @Mapping(target = "content", ignore = true),
            @Mapping(target = "topicId", source = "id")
    })
    TopicVO do2Vo(Topic topic);
}
