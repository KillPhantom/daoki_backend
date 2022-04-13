package com.daoki.basic.service;

import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.UpdateContentVO;
import com.daoki.basic.VO.response.ContentVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.exception.CustomException;

import java.util.List;


/**
 * @author Alan
 * 2022-02-28
 * Description: the interface of content related service layer
 */
public interface IContentService {

    /**
     * Note that the content entity object lack topicId property in the content related service layer,
     * so this property will be set in the topic related service layer
     * @param createContentVO a content module when creating a new topic
     * @return incomplete content entity object
     */
    Content createContent(CreateContentVO createContentVO);

    /**
     * update content
     * @param updateContentVO a content module when updating a topic
     */
    void updateContent(UpdateContentVO updateContentVO);

    /**
     * delete content according to content id in database
     * @param contentId id in database of content
     */
    void deleteContent(String contentId);

    /**
     * find contents by topicid and content status, and convert the contents to contentVOs
     * @param topicId topic id in database
     * @param status topic status
     * @return list of contentVO
     */
    List<ContentVO> getContentsByTopicIdAndStatus(String topicId, String status);

}
