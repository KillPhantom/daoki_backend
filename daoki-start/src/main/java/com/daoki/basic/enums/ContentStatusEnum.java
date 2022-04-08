package com.daoki.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alan
 * 2022-02-26
 * Description: the enum of content status
 */
@Getter
@AllArgsConstructor
public enum ContentStatusEnum {


    /**
     * unreleased
     */
    STATUS_CONTENT_UNRELEASED("0","unreleased"),

    /**
     * under-reviewed
     */
    STATUS_CONTENT_UNDERREVIEW("1","under-reviewed"),

    /**
     * released
     */
    STATUS_CONTENT_RELEASED("2", "released"),

    /**
     * failed to pass the review
     */
    STATUS_CONTENT_FAILED("-1","failed to pass the review"),

    /**
     * deleted
     */
    STATUS_CONTENT_DELETED("-2","deleted"),;

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
    public static ContentStatusEnum getByCode(String code){
        for (ContentStatusEnum contentStatusEnum : ContentStatusEnum.values()){
            if (contentStatusEnum.getCode().equals(code)){
                return contentStatusEnum;
            }
        }
        return null;
    }
}
