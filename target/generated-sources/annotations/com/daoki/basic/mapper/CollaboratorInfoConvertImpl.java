package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.CreateCollaboratorInfoVO;
import com.daoki.basic.VO.response.CollaboratorInfoVO;
import com.daoki.basic.entity.CollaboratorInfo;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-10T15:12:12+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class CollaboratorInfoConvertImpl implements CollaboratorInfoConvert {

    @Override
    public CollaboratorInfo createVo2Do(CreateCollaboratorInfoVO createCollaboratorInfoVO) {
        if ( createCollaboratorInfoVO == null ) {
            return null;
        }

        CollaboratorInfo collaboratorInfo = new CollaboratorInfo();

        collaboratorInfo.setEmail( createCollaboratorInfoVO.getEmail() );
        collaboratorInfo.setDescription( createCollaboratorInfoVO.getDescription() );

        return collaboratorInfo;
    }

    @Override
    public CollaboratorInfoVO do2Vo(CollaboratorInfo collaboratorInfo) {
        if ( collaboratorInfo == null ) {
            return null;
        }

        CollaboratorInfoVO collaboratorInfoVO = new CollaboratorInfoVO();

        collaboratorInfoVO.setId( collaboratorInfo.getId() );
        collaboratorInfoVO.setEmail( collaboratorInfo.getEmail() );
        collaboratorInfoVO.setDescription( collaboratorInfo.getDescription() );

        return collaboratorInfoVO;
    }
}
