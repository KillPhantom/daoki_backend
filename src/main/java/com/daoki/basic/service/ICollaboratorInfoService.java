package com.daoki.basic.service;

import com.daoki.basic.VO.request.CreateCollaboratorInfoVO;
import com.daoki.basic.VO.response.CollaboratorInfoVO;

/**
 * @author Alan
 * 2022-03-14
 * Description: the interface of collaborator information related service layer
 */
public interface ICollaboratorInfoService {

    /**
     * create collaborator information
     * @param createCollaboratorInfoVO a collaborator information vo
     */
    void createCollaboratorInfo(CreateCollaboratorInfoVO createCollaboratorInfoVO);

    /**
     * update content
     * @param id the id of collaborator information in database
     * @return a collaborator information vo
     */
    CollaboratorInfoVO getCollaboratorInfo(String id);
}
