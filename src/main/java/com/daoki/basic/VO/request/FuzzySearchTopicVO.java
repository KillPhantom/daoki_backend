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
@ApiModel("fuzzily search topics by key word")
@EqualsAndHashCode(callSuper = false)
public class FuzzySearchTopicVO {

    @ApiModelProperty("page number")
    @NotNull(message = "the page number mustn't be null")
    private int page;

    @ApiModelProperty("page size")
    @NotNull(message = "the page size mustn't be null")
    @Length(min = 5 , max = 50 , message = "the length of title is limited to 5~50")
    private int size;

    @ApiModelProperty("the key word used for searching")
    @NotNull(message = "the key word mustn't be null")
    private String keyword;

}
