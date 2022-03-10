package com.daoki.basic.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 黄训辉
 * Date: Created in 18/5/20 下午4:49
 * Utils: Intellij Idea
 * Description: 获取当前方法和行号
 */
@Slf4j
public class MethodUtil {

    /**
     * 私有化工具类 防止被实例化
     */
    private MethodUtil() {
    }

    public static String getLineInfo() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getFileName() + " -> " + ste.getLineNumber() + "行";
    }

}
