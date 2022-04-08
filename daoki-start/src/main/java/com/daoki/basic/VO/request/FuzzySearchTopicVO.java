package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Alan
 * 2022-03-09
 * Utils: Intellij Idea
 * Description: a vo for fuzzily searching topics by key word
 */
@Data
@ApiModel(value = "a object with keyword and page information for fuzzily searching")
@EqualsAndHashCode(callSuper = false)
public class FuzzySearchTopicVO {

    @ApiModelProperty(value = "page number")
    @NotNull(message = "the page number mustn't be null")
    private Integer page;

    @ApiModelProperty(value = "page size")
    @NotNull(message = "the page size mustn't be null")
    private Integer size;

    @ApiModelProperty(value = "the key word used for searching")
    @NotNull(message = "the key word mustn't be null")
    private String keyword;

}
