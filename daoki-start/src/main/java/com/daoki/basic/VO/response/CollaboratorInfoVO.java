package com.daoki.basic.VO.response;

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
public class CollaboratorInfoVO {

    @ApiModelProperty(value = "the id of collaborator information in database")
    private String id;

    @ApiModelProperty(value = "the email of collaborator")
    private String email;

    @ApiModelProperty(value = "what can the collaborator do for the project")
    @NotNull(message = "the description mustn't be null")
    private String description;

}
