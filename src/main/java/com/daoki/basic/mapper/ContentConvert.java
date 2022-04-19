package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.UpdateContentVO;
import com.daoki.basic.VO.response.ContentVO;
import com.daoki.basic.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Alan
 * 2022-02-27
 * Description: a mapper interface for converting between the content vo and content do
 */
@Mapper
public interface ContentConvert {
    ContentConvert INSTANCE = Mappers.getMapper(ContentConvert.class);

    /**
     * convert the created content vo to content do
     * Note that some properties of content do should be set in service layer
     * @param createContentVO a content vo when creating a new topic
     * @return incomplete content do which will be completed in service layer
     */
    @Mappings({
            @Mapping(target = "topicId", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "lastEdit", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "gmtCreate", ignore = true),
            @Mapping(target = "extra", ignore = true)

    })
    Content createVo2Do(CreateContentVO createContentVO);

    /**
     * convert the updated content vo to content do
     * Note that some properties of content do should be set in service layer
     * @param updateContentVO a content vo when updating a topic
     * @return incomplete content do which will be completed in service layer
     */
    @Mappings({
            @Mapping(target = "id", source = "contentId"),
            @Mapping(target = "lastEdit", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "gmtCreate", ignore = true),
            @Mapping(target = "extra", ignore = true)
    })
    Content updateVo2Do(UpdateContentVO updateContentVO);

    /**
     * convert the content do to content vo
     * @param content content do searched in database
     * @return content vo
     */
    @Mappings({
            @Mapping(target = "contentId", source = "id"),
    })
    ContentVO do2Vo(Content content);
}
