package com.shx.lawwh.base;


import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import java.io.Serializable;

/**
 * 个人用户信息
 * Created by xby on 2014/12/23.
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1471111456506583151L;

    /**
     * 获取用户信息
     * @return
     */
    public static ResponseUserInfo getUserInfoInstance() {
            return (ResponseUserInfo) SharedPreferencesUtil.readObject(BaseApplication.getContext(), CommonValues.USERINFO);
    }


}
