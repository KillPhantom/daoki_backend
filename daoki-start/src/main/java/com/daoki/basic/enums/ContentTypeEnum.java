package com.daoki.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Alan
 * 2022-02-26
 * Description: the enum of content type
 */
@Getter
@AllArgsConstructor
public enum ContentTypeEnum {

    /**
     * CODE
     */
    TYPE_CONTENT_CODE(1,"CODE"),

    /**
     * HTML_LINK
     */
    TYPE_CONTENT_HTML_LINK(2,"HTML_LINK"),

    /**
     * IMAGE
     */
    TYPE_CONTENT_IMAGE(3,"IMAGE"),

    /**
     * TWITTER_LINK
     */
    TYPE_CONTENT_TWITTER_LINK(4,"TWITTER_LINK"),

    /**
     * RICH_TEXT
     */
    TYPE_CONTENT_RICH_TEXT(5,"RICH_TEXT"),;

    /**
     * enum code
     */
    private final int code;

    /**
     * the description of enum
     */
    private final String message;

    /**
     * get the enum object by enum code
     * @param code enum code
     * @return enum object
     */
    public static ContentTypeEnum getByCode(int code){
        for(ContentTypeEnum contentTypeEnum : ContentTypeEnum.values()){
            if(contentTypeEnum.getCode() == code){
                return contentTypeEnum;
            }
        }
        return null;
    }
}
