package com.daoki.basic.VO.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zll
 * 2022-03-14
 * Description: the information of the collaborator
 */
@Data
@ApiModel(value = "the collaborator information")
@EqualsAndHashCode(callSuper = false)
public class CreateCollaboratorInfoVO {

    @ApiModelProperty(value = "the email of collaborator")
    @NotNull(message = "the email mustn't be null")
    private String email;

    @ApiModelProperty(value = "what can the collaborator do for the project")
    @NotNull(message = "the description mustn't be null")
    private String description;

}
