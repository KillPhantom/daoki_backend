package com.daoki.basic.VO.response;

import com.daoki.basic.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author Alan
 * Date: Created in 2019-03-18 21:49
 * Utils: Intellij Idea
 * Description: page view object
 */
@Data
@ApiModel("page view object")
public class PageVO<T> {

    @ApiModelProperty(value = "分页数据")
    private List<T> records;

    @ApiModelProperty(value = "总条数")
    private Integer total;

    @ApiModelProperty(value = "总页数")
    private Integer pages;

    @ApiModelProperty(value = "当前页")
    private Integer current;

    @ApiModelProperty(value = "查询数量")
    private Integer size;

    /**
     * 设置当前页和每页显示的数量
     * @param pageForm 分页表单
     * @return 返回分页信息
     */
    @ApiModelProperty(hidden = true)
    public PageVO<T> setCurrentAndSize(PageDTO<?> pageForm){
        BeanUtils.copyProperties(pageForm,this);
        return this;
    }

    /**
     * 设置总记录数
     * @param total 总记录数
     */
    @ApiModelProperty(hidden = true)
    public void setTotal(Integer total) {
        this.total = total;
        this.setPages(this.total % this.size > 0 ? this.total / this.size + 1 : this.total / this.size);
    }
}
