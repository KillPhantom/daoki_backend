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

    @ApiModelProperty(value = "page number")
    private Integer pageNo;

    @ApiModelProperty(value = "page size")
    private Integer pageSize;

    @ApiModelProperty(value = "the number of page")
    private Integer totalPage;

    @ApiModelProperty(value = "the number of searched recording")
    private long totalCount;

    @ApiModelProperty(value = "the list of results")
    private List<T> items;

    public PageVO(int pageNo, int pageSize, int totalPage, long totalCount, List<T> items) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.items = items;
    }
}
