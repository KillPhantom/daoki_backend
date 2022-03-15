package com.daoki.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author Alan
 * 2022-03-14
 * Description: the information of collaborator
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Document("collaborator_information")
public class CollaboratorInfo extends BaseDO implements Serializable {

    /**
     * primary key
     */
    @Id
    private String id;

    /**
     * the email of collaborator
     */
    private String email;

    /**
     * what can the collaborator do for this project
     */
    private String description;


}
