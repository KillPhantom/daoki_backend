package com.daoki.basic.service.impl;


import com.alibaba.fastjson.JSON;
import com.daoki.basic.VO.request.CreateCollaboratorInfoVO;
import com.daoki.basic.VO.response.CollaboratorInfoVO;
import com.daoki.basic.entity.CollaboratorInfo;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.mapper.CollaboratorInfoConvert;
import com.daoki.basic.repository.CollaboratorInfoRepository;
import com.daoki.basic.service.ICollaboratorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Alan
 * 2022-03-06
 * Description: the implement of the collaborator information related service layer interface
 */
@Slf4j
@Service
public class CollaboratorInfoServiceImpl implements ICollaboratorInfoService {

    @Autowired
    private CollaboratorInfoRepository collaboratorInfoRepository;

    @Override
    public void createCollaboratorInfo(CreateCollaboratorInfoVO createCollaboratorInfoVO) {
        log.info("<creating a new collaborator information>");

        String emailRegex
                = "[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})";
        if (!Pattern.compile(emailRegex).matcher(createCollaboratorInfoVO.getEmail()).matches()){
            log.error("<Fail to create a new collaborator information: " +
                            "the inputted email {} of collaborator has a wrong format>",
                    createCollaboratorInfoVO.getEmail());
            throw new CustomException(ErrorEnum.FORMAT_ERROR_EMAIL, "createCollaboratorInfo");
        }

        CollaboratorInfo collaboratorInfoSave = CollaboratorInfoConvert.INSTANCE.createVo2Do(createCollaboratorInfoVO);
        collaboratorInfoSave.setGmtCreate(new Date());
        CollaboratorInfo collaboratorInfo = collaboratorInfoRepository.save(collaboratorInfoSave);
        log.info("<successfully create a new collaborator information with id {}>", collaboratorInfo.getId());
    }

    @Override
    public CollaboratorInfoVO getCollaboratorInfo(String id) {
        log.info("<getting the collaborator information with id {}>", id);
        CollaboratorInfo collaboratorInfo = collaboratorInfoRepository.findCollaboratorInfoById(id);
        if (Objects.isNull(collaboratorInfo)){
            log.error("<Fail to get the collaborator information with id {}: " +
                            "the collaboration information with id {} doesn't exist in database>",
                    id, id);
            throw new CustomException(ErrorEnum.GET_COLLABORATORINFO_ERROR,"getCollaboratorInfo");
        }
        CollaboratorInfoVO collaboratorInfoVO = CollaboratorInfoConvert.INSTANCE.do2Vo(collaboratorInfo);
        log.info("<successfully get the collaborator information with id {}>", id);
        return collaboratorInfoVO;
    }
}
