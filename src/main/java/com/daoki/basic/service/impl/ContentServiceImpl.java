package com.daoki.basic.service.impl;

import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.UpdateContentVO;
import com.daoki.basic.VO.response.ContentVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.enums.ContentStatusEnum;
import com.daoki.basic.mapper.ContentConvert;
import com.daoki.basic.repository.ContentRepository;
import com.daoki.basic.repository.UserRepository;
import com.daoki.basic.service.IContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alan
 * 2022-03-06
 * Description: the implement of the content related service layer interface
 */
@Slf4j
@Service
public class ContentServiceImpl implements IContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Note that the content entity object lack topicId property in the content related service layer,
     * so this property will be set in the topic related service layer
     * @param createContentVO a content module when creating a new topic
     * @return incomplete content entity object
     */
    @Override
    public Content createContent(CreateContentVO createContentVO) {
        Content content = ContentConvert.INSTANCE.createVo2Do(createContentVO);
        content.setGmtCreate(new Date());
        content.setLastEdit(new Date());
        content.setStatus(ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode());
        return content;
    }

    /**
     * @param updateContentVO a content module when updating a topic
     */
    @Override
    public void updateContent(UpdateContentVO updateContentVO) {
        Content content = contentRepository.findContentById(updateContentVO.getContentId());
        Content contentSave = ContentConvert.INSTANCE.updateVo2Do(updateContentVO);
        contentSave.setGmtCreate(content.getGmtCreate());
        contentSave.setLastEdit(new Date());
        contentSave.setStatus(content.getStatus());
        contentRepository.save(contentSave);
    }

    @Override
    public void deleteContent(String contentId) {
        Content content = contentRepository.findContentById(contentId);
        content.setStatus(ContentStatusEnum.STATUS_CONTENT_DELETED.getCode());
        contentRepository.save(content);
    }

    @Override
    public List<ContentVO> getContentsByTopicIdAndStatus(String topicId, String status){
        List<Content> contentList = contentRepository.findContentsByTopicIdAndStatus(topicId, status);
        return contentList.stream().map(ContentConvert.INSTANCE::do2Vo).collect(Collectors.toList());
    }

}
