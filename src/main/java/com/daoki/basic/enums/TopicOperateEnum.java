package com.daoki.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alan
 * 2022-03-04
 * Description: the enum of topic operation type
 */
@AllArgsConstructor
@Getter
public enum TopicOperateEnum {

    /**
     * create
     */
    OPERATE_CREATE("0","create"),

    /**
     * update
     */
    OPERATE_UPDATE("1", "update"),

    /**
     * delete
     */
    OPERATE_DELETE("2", "delete"),;

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
    public TopicOperateEnum getByCode(String code){
        for(TopicOperateEnum topicOperateEnum : TopicOperateEnum.values()){
            if(topicOperateEnum.getCode().equals(code)){
                return topicOperateEnum;
            }
        }
        return null;
    }
}
