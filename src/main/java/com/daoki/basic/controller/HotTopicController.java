package com.daoki.basic.controller;

import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.service.IHotTopicService;
import com.daoki.basic.utils.ResultVoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Alan
 * 2022-03-08
 * Description: hot topic related controller layer
 */
@RestController
@Api(tags = "hot topic")
@AllArgsConstructor
@RequestMapping("/hotTopic")
public class HotTopicController {

    @Autowired
    private final IHotTopicService hotTopicService;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "get the form of hot topics")
    @GetMapping("/HotTopics")
    public ResultVO getHotTopics(){
        return ResultVoUtil.success(hotTopicService.getHotTopics(),"get the form of hot topics successfully");
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "get a hot topic by topic id")
    @GetMapping("/HotTopic")
    public ResultVO getHotTopic(
            @NotNull
            @ApiParam(name="id",value="topic id",required=true)
            @RequestParam
                    String topicId){
        return ResultVoUtil.success(hotTopicService.getHotTopic(topicId),"get hot topic successfully");
    }

}
