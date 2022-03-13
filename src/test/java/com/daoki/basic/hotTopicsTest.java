package com.daoki.basic;

import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.response.TopicVO;
import com.daoki.basic.entity.Topic;
import com.daoki.basic.repository.TopicRepository;
import com.daoki.basic.service.IHotTopicService;
import com.daoki.basic.service.impl.HotTopicServiceImpl;
import com.google.common.collect.Lists;
import net.sf.jsqlparser.statement.select.Top;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class hotTopicsTest {

    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void getHotTopic() {
        Sort sort = Sort.by(Sort.Direction.DESC, "viewCount");
        Pageable pageable = PageRequest.of(0,10,sort);
        Page<Topic> hotTopics = topicRepository.findAll(pageable);
        List<Topic> results = hotTopics.getContent();
        System.out.println(results);
    }

}
