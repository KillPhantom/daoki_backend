package com.daoki.basic.utils;


import com.daoki.basic.VO.response.ResultVO;
import com.daoki.basic.enums.ErrorEnum;

/**
 * @author Alan
 * Date: Created in 18/8/20 上午11:05
 * Utils: Intellij Idea
 * Description: 返回数据工具类
 */
public class ResultVoUtil {

    /**
     * 私有化工具类 防止被实例化
     * j
     */
    private ResultVoUtil() {}

    /**
     * 成功
     * @param object 需要返回的数据
     * @return data
     */
    public static ResultVO success(Object object, String message) {
        ResultVO result = new ResultVO();
        result.setCode("0");
        result.setMessage(message);
        result.setData(object);
        return result;
    }

    /**
     * 成功
     * @return 返回空
     */
    public static ResultVO success(String message) {
        return success(null,message);
    }

    /**
     * 错误
     * @param resultEnum 错误枚举类
     * @return 错误信息
     */
    public static ResultVO error(ErrorEnum resultEnum) {
        ResultVO result = new ResultVO();
        result.setCode(resultEnum.getCode());
        result.setMessage(resultEnum.getMsg());
        return result;
    }

    /**
     * 错误
     * @param code 状态码
     * @param msg 消息
     * @return ResultBean
     */
    public static ResultVO error(String code, String msg) {
        ResultVO result = new ResultVO();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    /**
     * 错误
     * @param msg 错误信息
     * @return ResultBean
     */
    public static ResultVO error(String msg) {
        return error("-1", msg);
    }

}
