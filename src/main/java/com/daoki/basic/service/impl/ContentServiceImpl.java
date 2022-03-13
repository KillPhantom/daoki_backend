package com.daoki.basic.service.impl;

import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.UpdateContentVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.ContentConvert;
import com.daoki.basic.repository.ContentRepository;
import com.daoki.basic.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author Alan
 * 2022-03-06
 * Description: the implement of the content related service layer interface
 */
@Service
public class ContentServiceImpl implements IContentService {

    @Autowired
    private ContentRepository contentRepository;

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
        return content;
    }

    /**
     * @param updateContentVO a content module when updating a topic
     */
    @Override
    public void updateContent(UpdateContentVO updateContentVO) {
        Content content = ContentConvert.INSTANCE.updateVo2Do(updateContentVO);
        // if the updated content contain the content id, the content already exist in database
        if (Objects.nonNull(updateContentVO.getContentId())){
            content.setId(updateContentVO.getContentId());
        }
        // if the updated content dont contain the content id, the content need be created
        else {
            content.setGmtCreate(new Date());
        }
        content.setLastEdit(new Date());
        contentRepository.save(content);
    }

    @Override
    public void deleteContent(String contentId) {
        // if the content with this content id cannot be found in database, an error will be thrown
        if(Objects.isNull(
                contentRepository.findContentById(contentId))){
            throw new CustomException(ErrorEnum.DELETE_CONTENT_ERROR, "deleteContent");
        }
        contentRepository.deleteContentById(contentId);
    }
}
