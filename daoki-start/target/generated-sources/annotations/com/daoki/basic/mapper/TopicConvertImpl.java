package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.request.UpdateTopicVO;
import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.entity.Topic;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-09T00:41:29+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class TopicConvertImpl implements TopicConvert {

    @Override
    public Topic createVo2Do(CreateTopicVO createTopicVO) {
        if ( createTopicVO == null ) {
            return null;
        }

        Topic topic = new Topic();

        topic.setName( createTopicVO.getName() );

        return topic;
    }

    @Override
    public Topic updateVo2Do(UpdateTopicVO updateTopicVO) {
        if ( updateTopicVO == null ) {
            return null;
        }

        Topic topic = new Topic();

        topic.setId( updateTopicVO.getTopicId() );
        topic.setName( updateTopicVO.getName() );

        return topic;
    }

    @Override
    public TopicVO do2Vo(Topic topic) {
        if ( topic == null ) {
            return null;
        }

        TopicVO topicVO = new TopicVO();

        topicVO.setTopicId( topic.getId() );
        topicVO.setName( topic.getName() );
        topicVO.setViewCount( String.valueOf( topic.getViewCount() ) );

        return topicVO;
    }
}
