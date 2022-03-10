package com.daoki.basic.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alan
 * Date: Created in 18/8/29 上午9:54
 * Utils: Intellij Idea
 * Description: the enum of error
 */
@Getter
@AllArgsConstructor
public enum ErrorEnum {

    /**
     * The request method is not supported
     */
    REQ_METHOD_NOT_SUPPORT("S-01","The request method is not supported"),

    /**
     * time-out
     */
    TIME_OUT("S-02", "time-out"),

    /**
     * unknown exception
     */
    UNKNOWN_EXCEPTION("S-03", "unknown exception"),

    /**
     * parameter with wrong format
     */
    FORMAT_ERROR("B-01", "parameter with wrong format"),

    /**
     * failed to create a new topic
     */
    CREATE_TOPIC_ERROR("B-T-01", "failed to create a new topic"),

    /**
     * failed to update topic: the topic dont exist
     */
    UPDATE_TOPIC_ERROR_NONEXIST("B-T-02-01", "failed to update topic: the topic dont exist"),

    /**
     * failed to update topic: the topic id in content is mismatched
     */
    UPDATE_TOPIC_ERROR_MISMATCH("B-T-02-02", "failed to update topic: the topic id in content is mismatched"),

    /**
     * failed to delete topic: the topic dont exist
     */
    DELETE_TOPIC_ERROR("B-T-03", "failed to delete topic: the topic dont exist"),

    /**
     * failed to find the topic: the topic dont exist
     */
    GET_TOPIC_ERROR("B-T-04", "failed to find the topic: the topic dont exist"),

    /**
     * failed to create a new content
     */
    CREATE_CONTENT_ERROR("B-C-01", "failed to create a new content"),

    /**
     * failed to update content
     */
    UPDATE_CONTENT_ERROR("B-C-02", "failed to update content"),

    /**
     * failed to delete content: the content dont exist
     */
    DELETE_CONTENT_ERROR("B-C-03", "failed to delete content: the content dont exist"),

    /**
     * failed to find content
     */
    GET_CONTENT_ERROR("B-C-04", "failed to find content"),

    /**
     * parameter with mismatch type
     */
    ARGUMENT_TYPE_MISMATCH("B-06", "parameter with mismatch type"),;

    /**
     * enum code
     */
    private final String code;

    /**
     * the description of enum
     */
    private final String msg;

    /**
     * get the enum object by enum code
     * @param code enum code
     * @return enum object
     */
    public static ErrorEnum getByCode(String code){
        for (ErrorEnum errorEnum : ErrorEnum.values()) {
            if(code.equals(errorEnum.getCode())){
                return errorEnum;
            }
        }
        return null;
    }

}

