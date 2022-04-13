package com.daoki.basic.service.impl;

import com.alibaba.fastjson.JSON;
import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.UpdateContentVO;
import com.daoki.basic.VO.response.ContentVO;
import com.daoki.basic.VO.response.user.UserVO;
import com.daoki.basic.entity.Content;
import com.daoki.basic.entity.User;
import com.daoki.basic.enums.ContentStatusEnum;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.ContentConvert;
import com.daoki.basic.mapper.UserConvert;
import com.daoki.basic.repository.ContentRepository;
import com.daoki.basic.repository.UserRepository;
import com.daoki.basic.service.IContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public Content createContent(CreateContentVO createContentVO) throws CustomException {
        if (isContributorsExist(createContentVO.getContributors())){
            Content content = ContentConvert.INSTANCE.createVo2Do(createContentVO);
            content.setGmtCreate(new Date());
            content.setLastEdit(new Date());
            content.setStatus(ContentStatusEnum.STATUS_CONTENT_RELEASED.getCode());
            return content;
        }
        log.debug("the some contributors in {} don't existed in database"
                ,JSON.toJSONString(createContentVO.getContributors()));
        throw new CustomException(ErrorEnum.CREATE_CONTENT_ERROR, "createContent");
    }

    /**
     * @param updateContentVO a content module when updating a topic
     */
    @Override
    public void updateContent(UpdateContentVO updateContentVO) throws CustomException {
        Content content = contentRepository.findContentById(updateContentVO.getContentId());
        if (isPermission(content)){
            if (isContributorsExist(updateContentVO.getContributors())){
                Content contentSave = ContentConvert.INSTANCE.updateVo2Do(updateContentVO);
                contentSave.setGmtCreate(content.getGmtCreate());
                contentSave.setLastEdit(new Date());
                contentSave.setStatus(content.getStatus());
                contentRepository.save(contentSave);
                return;
            }
            log.debug("the some contributors in {} don't existed in database"
                    ,JSON.toJSONString(updateContentVO.getContributors()));
            throw new CustomException(ErrorEnum.UPDATE_CONTENT_ERROR, "updateContent");
        }
        log.debug("no permission to update content with id {}", content.getId());
        throw new CustomException(ErrorEnum.UPDATE_CONTENT_ERROR, "updateContent");
    }

    @Override
    public void deleteContent(String contentId) throws CustomException {
        Content content = contentRepository.findContentById(contentId);
        if (isPermission(content)) {
            content.setStatus(ContentStatusEnum.STATUS_CONTENT_DELETED.getCode());
            contentRepository.save(content);
        }
        log.debug("no permission to delete content with id {}", contentId);
        throw new CustomException(ErrorEnum.DELETE_CONTENT_ERROR, "deleteContent");
    }

    @Override
    public List<ContentVO> getContentsByTopicIdAndStatus(String topicId, String status){
        List<Content> contentList = contentRepository.findContentsByTopicIdAndStatus(topicId, status);
        return contentList.stream()
                .map(content -> {
                    ContentVO contentVO = ContentConvert.INSTANCE.do2Vo(content);
                    contentVO.setContributors(
                            content.getContributors().stream()
                                    .map(userId -> {
                                        User user = userRepository.findByUserId(userId);
                                        return UserConvert.INSTANCE.Do2Vo(user);
                                    }).collect(Collectors.toList())
                    );
                    return contentVO;
                }).collect(Collectors.toList());
    }

    /**
     * if the user is one of the contributors, the user has permission to modify the content
     * @param content the content needed to be modified in database
     * @return whether has permission
     */
    private Boolean isPermission(Content content){
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        UserVO verify = (UserVO) request.getAttribute("user");
        return content.getContributors().contains(verify.getUserId());
    }

    /**
     * whether all contributors exist in user database
     * @param contributors list of all contributors
     * @return whether all contributors exist in user database
     */
    private Boolean isContributorsExist(List<Long> contributors){
        for (Long userId : contributors){
            if (!userRepository.existsById(userId)){
                return false;
            }
        }
        return true;
    }
}
