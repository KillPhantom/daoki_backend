package com.daoki.basic.aop;

import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.enums.ErrorEnum;
import com.daoki.basic.exception.CustomException;
import com.daoki.basic.utils.ResultVoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;


/**
 * @author 黄训辉
 * Date: Created in 2018/3/22 11:02
 * Utils: Intellij Idea
 * Description: 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandling {

    /**
     * 自定义异常
     */
    @ExceptionHandler(value = CustomException.class)
    public ResultVO processException(CustomException e) {
        log.error("位置:{} -> 错误信息:{}", e.getMethod() ,e.getLocalizedMessage());
        return ResultVoUtil.error(Objects.requireNonNull(ErrorEnum.getByCode(e.getCode())));
    }

    /**
     * 拦截表单参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public ResultVO bindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResultVoUtil.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /**
     * 拦截JSON参数校验
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return ResultVoUtil.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /**
     * 参数格式错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultVO methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("错误信息{}", e.getLocalizedMessage());
        return ResultVoUtil.error(ErrorEnum.ARGUMENT_TYPE_MISMATCH);
    }

    /**
     * 参数格式错误
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVO httpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error("错误信息:{}", e.getLocalizedMessage());
        return ResultVoUtil.error(ErrorEnum.FORMAT_ERROR);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVO httpReqMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.error("错误信息:{}", e.getLocalizedMessage());
        return ResultVoUtil.error(ErrorEnum.REQ_METHOD_NOT_SUPPORT);
    }

    /**
     * 通用异常
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public ResultVO exception(Exception e) {
        e.printStackTrace();
        return ResultVoUtil.error(ErrorEnum.UNKNOWN_EXCEPTION);
    }
}
