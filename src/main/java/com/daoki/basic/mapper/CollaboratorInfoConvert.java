package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.CreateCollaboratorInfoVO;
import com.daoki.basic.VO.response.CollaboratorInfoVO;
import com.daoki.basic.VO.response.ContentVO;
import com.daoki.basic.entity.CollaboratorInfo;
import com.daoki.basic.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Alan
 * 2022-03-14
 * Description: a mapper interface for converting between the collaborator information vo and do
 */
@Mapper
public interface CollaboratorInfoConvert {

    CollaboratorInfoConvert INSTANCE = Mappers.getMapper(CollaboratorInfoConvert.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "gmtCreate", ignore = true),
            @Mapping(target = "extra", ignore = true)
    })
    CollaboratorInfo createVo2Do(CreateCollaboratorInfoVO createCollaboratorInfoVO);

    CollaboratorInfoVO do2Vo(CollaboratorInfo collaboratorInfo);

}
