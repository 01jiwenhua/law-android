package com.shx.lawwh.libs.http;


import com.unitransdata.mallclient.base.BaseApplication;
import com.unitransdata.mallclient.commons.CommonValues;
import com.unitransdata.mallclient.model.UserInfo;
import com.unitransdata.mallclient.utils.SharedPreferencesUtil;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by admin on 2016/3/21.
 */
public class ZCHeader extends HashMap implements Serializable {

    private static final long serialVersionUID = 5337674673791024951L;

    public ZCHeader(){
        this.put("clientid", SharedPreferencesUtil.getStringValue(BaseApplication.getContext(), CommonValues.PUSH_CID,""));
//        this.put("userId",SharedPreferencesUtil.getIntValue(BaseApplication.getContext(), CommonValues.USERID,0));
        if(UserInfo.getUserInfoInstance()==null) {
            this.put("userId", 780);
        }else{
            this.put("userId", UserInfo.getUserInfoInstance().getUserId());
        }
        if(UserInfo.getUserInfoInstance()==null) {
            this.put("companyId", 780);
        }else{
            this.put("companyId", UserInfo.getCompanyId());
        }
        this.put("token",SharedPreferencesUtil.getStringValue(BaseApplication.getContext(),CommonValues.TOKENCODE,""));
        this.put("device","xiaomi");
        this.put("platform","android");
    }


}
