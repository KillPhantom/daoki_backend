package com.daoki.basic.controller;

import com.daoki.basic.VO.request.CreateCollaboratorInfoVO;
import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.service.ICollaboratorInfoService;
import com.daoki.basic.utils.ResultVoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Alan
 * 2022-03-14
 * Description: collaborator information related controller layer
 */
@RestController
@Api(tags = "collaborator information")
@AllArgsConstructor
@RequestMapping("/collaboratorInfo")
public class CollaboratorInfoController {

    @Autowired
    private final ICollaboratorInfoService collaboratorInfoService;

    @PostMapping(value = "/create a new collaborator information")
    public ResultVO createCollaboratorInfo(
            @Validated
            @ApiParam(name="createdCollaboratorInfo",value="a created collaborator information",required=true)
            @RequestBody
                    CreateCollaboratorInfoVO createCollaboratorInfoVO){
        collaboratorInfoService.createCollaboratorInfo(createCollaboratorInfoVO);
        return ResultVoUtil.success(null, "create collaborator information successfully");
    }

}
