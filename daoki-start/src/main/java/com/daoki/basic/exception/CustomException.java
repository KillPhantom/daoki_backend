package com.daoki.basic.exception;


import com.daoki.basic.enums.ErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Alan
 * Date: Created in 2018-12-18 10:25
 * Utils: Intellij Idea
 * Description: custom-defined exception
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException {

    /**
     * status code
     */
    private final String code;

    /**
     * the name of method where the exception happens
     */
    private final String method;


    /**
     * user-defined exception
     * @param resultEnum the enum object of error
     * @param method method
     */
    public CustomException(ErrorEnum resultEnum, String method) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.method = method;
    }
}
