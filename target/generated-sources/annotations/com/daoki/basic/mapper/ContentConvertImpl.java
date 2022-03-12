package com.daoki.basic.mapper;

import com.daoki.basic.VO.request.CreateContentVO;
import com.daoki.basic.VO.request.UpdateContentVO;
import com.daoki.basic.VO.response.ContentVO;
import com.daoki.basic.entity.Content;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-12T22:57:14+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
public class ContentConvertImpl implements ContentConvert {

    @Override
    public Content createVo2Do(CreateContentVO createContentVO) {
        if ( createContentVO == null ) {
            return null;
        }

        Content content = new Content();

        content.setBody( createContentVO.getBody() );
        content.setTitle( createContentVO.getTitle() );
        if ( createContentVO.getType() != null ) {
            content.setType( createContentVO.getType() );
        }
        List<String> list = createContentVO.getContributors();
        if ( list != null ) {
            content.setContributors( new ArrayList<String>( list ) );
        }
        else {
            content.setContributors( null );
        }
        if ( createContentVO.getPosition() != null ) {
            content.setPosition( createContentVO.getPosition() );
        }

        return content;
    }

    @Override
    public Content updateVo2Do(UpdateContentVO updateContentVO) {
        if ( updateContentVO == null ) {
            return null;
        }

        Content content = new Content();

        content.setTopicId( updateContentVO.getTopicId() );
        content.setBody( updateContentVO.getBody() );
        content.setTitle( updateContentVO.getTitle() );
        if ( updateContentVO.getType() != null ) {
            content.setType( updateContentVO.getType() );
        }
        List<String> list = updateContentVO.getContributors();
        if ( list != null ) {
            content.setContributors( new ArrayList<String>( list ) );
        }
        else {
            content.setContributors( null );
        }
        if ( updateContentVO.getPosition() != null ) {
            content.setPosition( updateContentVO.getPosition() );
        }

        return content;
    }

    @Override
    public ContentVO do2Vo(Content content) {
        if ( content == null ) {
            return null;
        }

        ContentVO contentVO = new ContentVO();

        contentVO.setContentId( content.getId() );
        contentVO.setBody( content.getBody() );
        contentVO.setTitle( content.getTitle() );
        contentVO.setType( content.getType() );
        List<String> list = content.getContributors();
        if ( list != null ) {
            contentVO.setContributors( new ArrayList<String>( list ) );
        }
        else {
            contentVO.setContributors( null );
        }
        contentVO.setLastEdit( content.getLastEdit() );
        contentVO.setPosition( content.getPosition() );

        return contentVO;
    }
}
