package com.daoki.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.daoki.basic.VO.response.HotTopicFormVO;
import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.entity.Topic;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.enums.TopicStatusEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.repository.TopicRepository;
import com.daoki.basic.service.IHotTopicService;
import com.daoki.basic.service.ITopicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alan
 * 2022-03-08
 * Description: the implement of the hot topic related service layer interface
 */
@Service
@Slf4j
public class HotTopicServiceImpl implements IHotTopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ITopicService topicService;

    @Value("${daoki.web.hotTopicUrlPre:}${server.servlet.context-path:}")
    private String linkPre;

    /**
     * get the form of hot topics
     * @return list of hot topics
     */
    @Override
    public List<HotTopicFormVO> getHotTopics() {

        log.info("<getting the hot topics form>");

        // get the sorted hot topics by sorting the viewCount property
        Sort sort = Sort.by(Sort.Direction.DESC, "viewCount");
        Pageable pageable = PageRequest.of(0,10,sort);
        List<Topic> hotTopics =
                topicRepository.findTopicsByStatus(
                        pageable, TopicStatusEnum.STATUS_TOPIC_RELEASED.getCode()
                ).getContent();

        if (hotTopics.isEmpty()) {
            log.debug("no hot topics be found");
        }
        log.info("<successfully get the hot topics form with ids {}>",
                JSON.toJSONString(
                        hotTopics.stream().map(Topic::getId).collect(Collectors.toList())));

        return hotTopics.stream().map(topic -> {
            HotTopicFormVO hotTopicVO = new HotTopicFormVO();
            hotTopicVO.setTitle(topic.getName());
            hotTopicVO.setLink(linkPre+"/topic/id?id="+topic.getId());
            hotTopicVO.setDescription("nothing");
            return hotTopicVO;
        }).collect(Collectors.toList());
    }

    /**
     * get the hot topic by topic id
     * @param topicId id of topic in database
     * @return topic vo
     */
    @Override
    public TopicVO getHotTopic(String topicId){
        return topicService.getTopicById(topicId);
    }
}
