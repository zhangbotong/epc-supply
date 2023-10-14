package com.cbim.epc.supply.common.utils;

import com.cbim.epc.base.exception.EpcException;
import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.base.usercontext.UserDTO;

import java.util.Optional;

public class LoginUserUtil {

    public static UserDTO getLoginUser(){
        UserDTO user = UserContext.getUser();
        return Optional.ofNullable(user).orElseThrow(() -> new EpcException("请登录"));
    }
}
