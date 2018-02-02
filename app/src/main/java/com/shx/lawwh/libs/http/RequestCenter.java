package com.shx.lawwh.libs.http;

import com.shx.lawwh.entity.request.RequestRegisterInfo;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.request.LawRequest;

/**
 * Created by 邵鸿轩 on 2016/12/6.
 */

public class RequestCenter {
    public static final String GET_LAWLIST="/law/list";
    public static final String GET_KNOWNLIST="/chemicals/getKnownList";
    public static final String GET_CHEMICALSDETAILS="/chemicals/getChemicalsDetails";
    public static final String GET_UNKNOWPARAMS="/chemicals/getUnknowParams";
    public static final String GET_UNKNOWPARAMS_DETAILS="/chemicals/getUnknowParamsDetails";
    public static final String GET_COMPANY_LIST="/user/getCompanyList";
    public static final String GET_DEPARTMENT_LIST="/user/getDepartmentList";
    public static final String GET_JOB_LIST="/user/getJobList";

    public static final String CHECK_REGIST="/user/checkRegist";
    public static final String GET_VERIFYCODE="/user/getVerifyCode";
    public static final String REGIST="/user/regist";
    public static final String LOGIN="/user/login";
    public static final String GET_LEVELLIST="/law/getLevelList";
    /**
     * 获取法律法规列表
     * @param lawRequest
     * @param callBack
     */
    public static void getLawList(LawRequest lawRequest,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_LAWLIST);

        request.putParams("name",lawRequest.getName());
        request.putParams("level",lawRequest.getLevel());
        request.putParams("typeName",lawRequest.getTypeName());
        request.putParams("typeCode",lawRequest.getTypeCode());
        request.putParams("issoNo",lawRequest.getIssue_no());
        request.putParams("page",lawRequest.getPage());
        request.putParams("pageSize",lawRequest.getPageSize());
        request.putParams("description",lawRequest.getDescription());
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 获取已知物质
     * @param chemicalsRequest
     * @param callBack
     */
    public static void getKnownlist(ChemicalsRequest chemicalsRequest,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_KNOWNLIST);
        request.putParams("name",chemicalsRequest.getName());
        request.putParams("status",chemicalsRequest.getStatus());
        request.putParams("color",chemicalsRequest.getColor());
        request.putParams("smell",chemicalsRequest.getSmell());
        request.putParams("taste",chemicalsRequest.getTaste());
        request.putParams("specific_air",chemicalsRequest.getSpecific_air());
        request.putParams("specific_water",chemicalsRequest.getSpecific_water());
        request.putParams("ph",chemicalsRequest.getPh());
        request.putParams("transparency",chemicalsRequest.getTransparency());
        request.putParams("nervous",chemicalsRequest.getNervous());
        request.putParams("eye",chemicalsRequest.getEye());
        request.putParams("ear",chemicalsRequest.getEar());
        request.putParams("mouth_throat",chemicalsRequest.getMouth_throat());
        request.putParams("cardiovascular",chemicalsRequest.getCardiovascular());
        request.putParams("respiratory",chemicalsRequest.getRespiratory());
        request.putParams("gastro_urinary",chemicalsRequest.getGastro_urinary());
        request.putParams("skin",chemicalsRequest.getSkin());

        request.putParams("page",chemicalsRequest.getPage());
        request.putParams("pageSize",chemicalsRequest.getPageSize());
        HttpManager.getInstance().doPost(request,callBack);

    }
    public static void getChemicalsdetails(String id,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_CHEMICALSDETAILS);
        request.putParams("id",id);
        HttpManager.getInstance().doPost(request,callBack);

    }
    public static void getUnknowparams(HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_UNKNOWPARAMS);
        HttpManager.getInstance().doPost(request,callBack);
    }

    public static void getUnknowparamsDetails(String code,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_UNKNOWPARAMS_DETAILS);
        request.putParams("code",code);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 得到公司列表
     * */
    public static void getCompanyList(HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_COMPANY_LIST);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 得到部门列表
     * */
    public static void getDepartmentList(String companyId,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_DEPARTMENT_LIST);
        request.putParams("companyId",companyId);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 得到部门列表
     * */
    public static void getJobList(HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_JOB_LIST);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 验证手机注册
     * */
    public static void checkRegist(String phone,String verifyCode ,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(CHECK_REGIST);
        request.putParams("phone",phone);
        request.putParams("verifyCode",verifyCode);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 得到手机验证码
     * */
    public static void getVerifyCode(String phone,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_VERIFYCODE);
        request.putParams("phone",phone);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 注册
     * */
    public static void regist(RequestRegisterInfo info, HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(REGIST);
        request.putParams("loginName",info.getLoginName());
        request.putParams("nickName",info.getNickName());
        request.putParams("realName",info.getRealName());
        request.putParams("departmentId",info.getDepartmentId());
        request.putParams("regionId",info.getRegionId());
        request.putParams("email",info.getEmail());
        request.putParams("idNo",info.getIdNo());
        request.putParams("jobId",info.getJobId());
        request.putParams("phone",info.getPhone());
        request.putParams("sex",info.getSex());
        request.putParams("userType",info.getUserType());
        request.putParams("licenseType",info.getLicenseType());
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 登录接口
     * */
    public static void login(String phone,String verifyCode,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(LOGIN);
        request.putParams("phone",phone);
        request.putParams("verifyCode",verifyCode);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 得到法律法规，标准规范，政策文件的列表
     * */
    public static void getLevelList(String typeCode,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_LEVELLIST);
        request.putParams("typeCode",typeCode);
        HttpManager.getInstance().doPost(request,callBack);
    }


}

