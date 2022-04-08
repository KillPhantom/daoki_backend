package com.daoki.basic.dto;

/**
 * @author Alan
 * 2022-02-26
 */
public interface BaseDTO<DoObject, VoObject> {
    /**
     * 根据DTO对象，创建DO对象
     * @return 返回DO对象
     */
    DoObject buildDO();

    /**
     * 根据DTO对象，创建VO对象
     * @return VO对象
     */
    VoObject buildVO();

}
