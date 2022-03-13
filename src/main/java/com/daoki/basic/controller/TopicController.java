package com.daoki.basic.controller;

import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.request.DeleteTopicVO;
import com.daoki.basic.VO.request.FuzzySearchTopicVO;
import com.daoki.basic.VO.request.UpdateTopicVO;
import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.service.ITopicService;
import com.daoki.basic.utils.ResultVoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private final ITopicService topicService;

    @ApiOperation("create a new topic")
    @PostMapping("/create")
    public ResultVO createTopic(@Validated @RequestBody CreateTopicVO createTopicVO){
        topicService.createTopic(createTopicVO);
        return ResultVoUtil.success(null,"create topic successfully");
    }

    @ApiOperation("update topic")
    @PostMapping("/update")
    public ResultVO updateTopic(@Validated @RequestBody UpdateTopicVO updateTopicVO){
        topicService.updateTopic(updateTopicVO);
        return ResultVoUtil.success(null,"update topic successfully");
    }

    @ApiOperation("delete topic")
    @PostMapping("/delete")
    public ResultVO deleteTopic(@NotNull @RequestBody DeleteTopicVO deleteTopicVO){
        topicService.deleteTopic(deleteTopicVO);
        return ResultVoUtil.success(null,"delete topic successfully");
    }

    @ApiOperation("query fuzzily topic by name")
    @GetMapping("/name")
    public ResultVO getTopicByName(@NotNull @RequestBody FuzzySearchTopicVO fuzzySearchTopicVO){
        return ResultVoUtil.success(topicService.getTopicByName(fuzzySearchTopicVO),"get topic form successfully");
    }

    @ApiOperation("query topic by topic id")
    @GetMapping("/id")
    public ResultVO getTopicById(@NotNull @RequestParam String id){
        return ResultVoUtil.success(topicService.getTopicById(id),"get topic successfully");
    }

}
