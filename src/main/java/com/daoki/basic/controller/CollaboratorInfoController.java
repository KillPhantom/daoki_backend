package com.daoki.basic.controller;

import com.daoki.basic.VO.request.CreateCollaboratorInfoVO;
import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.service.ICollaboratorInfoService;
import com.daoki.basic.utils.ResultVoUtil;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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

    @PostMapping("/create")
    public ResultVO createCollaboratorInfo(@Validated @RequestBody CreateCollaboratorInfoVO createCollaboratorInfoVO){
        collaboratorInfoService.createCollaboratorInfo(createCollaboratorInfoVO);
        return ResultVoUtil.success(null, "create collaborator information successfully");
    }

}
