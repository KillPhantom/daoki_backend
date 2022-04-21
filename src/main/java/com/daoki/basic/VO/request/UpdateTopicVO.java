package com.daoki.basic.VO.request;

import com.daoki.basic.entity.Topic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alan
 * 2022-03-01
 * Utils: Intellij Idea
 * Description: this Object is used for updating the user-defined topic
 */
@Data
@ApiModel(value = "a updated topic",
        description = "All content modules must be contained in the updated topic whether it is updated or not, " +
        "or the content module didn't contained will be deleted in database")
public class UpdateTopicVO {

    @ApiModelProperty(value = "the id in database of this topic")
    @NotNull(message = "the id mustn't be null")
    private String topicId;

    @ApiModelProperty(value = "the name of topic")
    @NotNull(message = "the name mustn't be null")
    @Length(min = 1 , max = 30 , message = "the length of name is limited to 1~30")
    private String name;

    @ApiModelProperty(value = "the content modules of topic")
    @Valid
    private List<UpdateContentVO> content;

    @ApiModelProperty(value = "the id of topics quoted by this topic")
    private List<String> quoteTopics;

}
