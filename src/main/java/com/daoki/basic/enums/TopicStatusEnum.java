package com.daoki.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alan
 * 2022-02-26
 * Description: the enum of topic status
 */
@Getter
@AllArgsConstructor
public enum TopicStatusEnum {

    /**
     * unreleased
     */
    STATUS_TOPIC_UNRELEASED("0","unreleased"),

    /**
     * under-reviewed
     */
    STATUS_TOPIC_UNDERREVIEW("1","under-reviewed"),

    /**
     * released
     */
    STATUS_TOPIC_RELEASED("2", "released"),

    /**
     * failed to pass the review
     */
    STATUS_TOPIC_FAILED("-1","failed to pass the review"),

    /**
     * deleted
     */
    STATUS_TOPIC_DELETED("-2","deleted"),;

    /**
     * enum code
     */
    private final String code;

    /**
     * the description of enum
     */
    private final String message;

    /**
     * get the enum object by enum code
     * @param code enum code
     * @return enum object
     */
    public static TopicStatusEnum getByCode(String code){
        for (TopicStatusEnum topicStatusEnum : TopicStatusEnum.values()){
            if (topicStatusEnum.getCode().equals(code)){
                return topicStatusEnum;
            }
        }
        return null;
    }
}
