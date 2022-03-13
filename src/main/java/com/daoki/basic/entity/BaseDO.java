package com.daoki.basic.entity;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: the parent class of all entity classes
 * @author Alan
 */
@Data
public abstract class BaseDO {
    /**
     * the creating time of entity object
     */
    Date gmtCreate;

    /**
     * extra parameter which may be useful later
     */
    Map<String, Object> extra = new HashMap<>();

}
