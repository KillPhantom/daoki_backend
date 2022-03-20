package com.daoki.basic.controller;

import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.request.DeleteTopicVO;
import com.daoki.basic.VO.request.FuzzySearchTopicVO;
import com.daoki.basic.VO.request.UpdateTopicVO;
import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.service.ITopicService;
import com.daoki.basic.utils.ResultVoUtil;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Alan
 * 2022-03-08
 * Description: topic related controller layer
 */
@RestController
@Api(tags = "topic")
@AllArgsConstructor
@RequestMapping("/Topic Related API")
public class TopicController {
    @Autowired
    private final ITopicService topicService;

    @ApiOperation(value = "create a new topic")
    @PostMapping("/create")
    public ResultVO createTopic(
            @Validated
            @ApiParam(name="createdTopic",value="a new created topic",required=true)
            @RequestBody
                    CreateTopicVO createTopicVO){
        topicService.createTopic(createTopicVO);
        return ResultVoUtil.success(null,"create topic successfully");
    }

    @ApiOperation(value = "update topic")
    @PostMapping("/update")
    public ResultVO updateTopic(
            @Validated
            @ApiParam(name="updatedTopic",value="a updated topic",required=true)
            @RequestBody
                    UpdateTopicVO updateTopicVO){
        topicService.updateTopic(updateTopicVO);
        return ResultVoUtil.success(null,"update topic successfully");
    }

    @ApiOperation(value = "delete topic")
    @PostMapping("/delete")
    public ResultVO deleteTopic(
            @Validated
            @ApiParam(name="deletedTopic",value="a object with topic id and operator",required=true)
            @RequestBody
                    DeleteTopicVO deleteTopicVO){
        topicService.deleteTopic(deleteTopicVO);
        return ResultVoUtil.success(null,"delete topic successfully");
    }

    @ApiOperation(value = "query fuzzily topic by name")
    @GetMapping("/name")
    public ResultVO getTopicByName(
            @Validated
            @ApiParam(name="fuzzySearchedTopic",value="a object with keyword and page information",required=true)
            @RequestBody
                    FuzzySearchTopicVO fuzzySearchTopicVO){
        return ResultVoUtil.success(topicService.getTopicByName(fuzzySearchTopicVO),"get topic form successfully");
    }

    @ApiOperation(value = "query topic by topic id")
    @GetMapping("/id")
    public ResultVO getTopicById(
            @NotNull
            @ApiParam(name="id",value="topic id",required=true)
            @RequestParam
                    String id){
        return ResultVoUtil.success(topicService.getTopicById(id),"get topic successfully");
    }

}
