package com.daoki.basic.VO.request;

import com.daoki.basic.entity.Topic;
import com.daoki.basic.repository.TopicRepository;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alan
 * 2022-02-26
 * Utils: Intellij Idea
 * Description: this Object is used for creating the user-defined topic
 */
@Data
@ApiModel(value = "a new created topic")
@EqualsAndHashCode(callSuper = false)
public class CreateTopicVO {

    @ApiModelProperty(value = "the name of topic")
    @NotNull(message = "the name mustn't be null")
    @Length(min = 1 , max = 30 , message = "the length of name is limited to 1~30")
    private String name;

    @ApiModelProperty(value = "the content modules of topic")
    private List<CreateContentVO> content;

    @ApiModelProperty(value = "the contributor of topic")
    @NotNull(message = "the contributor of topic mustn't be null")
    private Long contributor;

    @ApiModelProperty(value = "the id of topics quoted by this topic")
    private List<String> quoteTopics;

}
