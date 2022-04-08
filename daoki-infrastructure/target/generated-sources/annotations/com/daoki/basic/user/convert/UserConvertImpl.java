package com.daoki.basic.user.convert;

import com.daoki.basic.user.model.UserDO;
import com.daoki.basic.user.po.UserPO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-09T00:32:43+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class UserConvertImpl implements UserConvert {

    @Override
    public UserDO userPO2UserDO(UserPO userPO) {
        if ( userPO == null ) {
            return null;
        }

        UserDO userDO = new UserDO();

        if ( userPO.getU_id() != null ) {
            userDO.setUserId( String.valueOf( userPO.getU_id() ) );
        }
        userDO.setUserName( userPO.getUserName() );
        userDO.setWalletHash( userPO.getWalletHash() );
        userDO.setNonce( userPO.getNonce() );

        return userDO;
    }

    @Override
    public UserPO userDO2UserPO(UserDO userDO) {
        if ( userDO == null ) {
            return null;
        }

        UserPO userPO = new UserPO();

        if ( userDO.getUserId() != null ) {
            userPO.setU_id( Long.parseLong( userDO.getUserId() ) );
        }
        userPO.setUserName( userDO.getUserName() );
        userPO.setWalletHash( userDO.getWalletHash() );
        userPO.setNonce( userDO.getNonce() );

        return userPO;
    }
}
