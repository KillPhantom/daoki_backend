package com.daoki.basic.controller;

import com.daoki.basic.VO.request.CreateTopicVO;
import com.daoki.basic.VO.request.FuzzySearchTopicVO;
import com.daoki.basic.VO.request.GetAllTopicsVO;
import com.daoki.basic.VO.request.UpdateTopicVO;
import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.anno.PassToken;
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
@Api(tags = "Topic Related API")
@AllArgsConstructor
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private final ITopicService topicService;

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "create a new topic")
    @PostMapping("/create")
    public ResultVO createTopic(
            @Validated
            @ApiParam(name = "createdTopic", value = "a new created topic", required = true)
            @RequestBody
                    CreateTopicVO createTopicVO) {
        String topicId = topicService.createTopic(createTopicVO);
        return ResultVoUtil.success(topicId, "create topic successfully");
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "update topic")
    @PostMapping("/update")
    public ResultVO updateTopic(
            @Validated
            @ApiParam(name = "updatedTopic", value = "a updated topic", required = true)
            @RequestBody
                    UpdateTopicVO updateTopicVO) {
        String topicId = topicService.updateTopic(updateTopicVO);
        return ResultVoUtil.success(topicId, "update topic successfully");
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "delete topic")
    @PostMapping("/delete")
    public ResultVO deleteTopic(
            @Validated
            @ApiParam(name = "id", value = "topic id", required = true)
            @RequestParam String id) {
        topicService.deleteTopic(id);
        return ResultVoUtil.success(null, "delete topic successfully");
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "query fuzzily topic by name")
    @GetMapping("/name")
    @PassToken
    public ResultVO getTopicByName(
            @Validated
            @ApiParam(name = "fuzzySearchedTopic", value = "a object with keyword and page information", required = true)
            @RequestBody
                    FuzzySearchTopicVO fuzzySearchTopicVO) {
        return ResultVoUtil.success(topicService.getTopicByName(fuzzySearchTopicVO), "get topic form successfully");
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "query topic by topic id")
    @GetMapping("/id")
    @PassToken
    public ResultVO getTopicById(
            @NotNull
            @ApiParam(name = "id", value = "topic id", required = true)
            @RequestParam String id) {
        return ResultVoUtil.success(topicService.getTopicById(id), "get topic successfully");
    }

    @CrossOrigin(origins = "*",maxAge = 3600)
    @ApiOperation(value = "query all topics by user id")
    @PostMapping("/get-all-topics")
    public ResultVO getAllTopics(
            @NotNull
            @ApiParam(name = "get all topics by user id", value = "a object with user id and page information", required = true)
            @RequestBody GetAllTopicsVO getAllTopicsVO) {
        return ResultVoUtil.success(topicService.getAllTopics(getAllTopicsVO), "get topic successfully");
    }

}
