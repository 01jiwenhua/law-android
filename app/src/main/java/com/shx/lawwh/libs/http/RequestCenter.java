package com.shx.lawwh.libs.http;

import android.text.Editable;

import com.shx.lawwh.base.UserInfo;
import com.shx.lawwh.entity.request.RequestRegisterInfo;
import com.shx.lawwh.entity.request.ChemicalsRequest;
import com.shx.lawwh.entity.request.LawRequest;
import com.shx.lawwh.entity.response.ResponseUserInfo;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    public static final String GET_USERINFO="/user/getUserInfo";
    public static final String GET_FAVORITE="/law/getFavoriteList";
    public static final String ADD_FAVORITE="/law/addFavorite";
    public static final String CANCEL_FAVORITE="/law/cancelFavorite";
    public static final String GET_MESSAGE="/user/getMessage";
    public static final String GET_ARCHITECTURE="/distance/getArchitecture";
    public static final String GET_ARCHITECTURE_V2="/distance/getArchitecture/v2";
    public static final String GET_DISTANCE="/distance/getDistance";
    public static final String GET_NEWLAW="/law/getNewLawList";
    public static final String GET_NEWVERSION="/user/getNewVersion";
    public static final String SAVE_ADVICE="/system/saveAdvice";
    public static final String CHANGE_PHONE="/user/changePhone";
    public static final String UPLOAD_AVATAR="/user/uploadAvatar";


    /**
     *
     * 修改手机号
     * @param
     * @param phone
     * @param verifyCode
     * @param callBack
     */
    public static void changePhone(String userId,Editable phone, Editable verifyCode, HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(CHANGE_PHONE);
        request.putParams("phone",phone);
        request.putParams("userId",userId);
        request.putParams("verifyCode",verifyCode);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 上传头像
     *
     * @param userId
     * @param callBack
     */
    public static void uploadAvatar(int userId, File avatar,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(UPLOAD_AVATAR);
        request.putParams("userId",userId);
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("avatar",avatar);
        HttpManager.getInstance().upLoadOneFile(request,fileMap,callBack);
    }

    /**
     *
     * @param
     * @param callBack
     */
    public static void getDistance(int deviceInId,int structureOutId,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_DISTANCE);
        request.putParams("deviceInId",deviceInId);
        request.putParams("structureOutId",structureOutId);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 版本更新
     * @param versionCode
     * @param callBack
     */
    public static void getNewVersion(int versionCode,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_NEWVERSION);
        request.putParams("versionCode",versionCode);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 提交意见反馈
     * @param content
     * @param userId
     * @param callBack
     */
    public static void saveAdvice(String content,String userId,HttpCallBack callBack){

        ZCRequest request=new ZCRequest();
        request.setUrl(SAVE_ADVICE);
        request.putParams("userId",userId);
        request.putParams("content",content);
        HttpManager.getInstance().doPost(request,callBack);
    }
    /**
     *
     * @param
     * @param callBack
     */
    public static void getArchitecture(String name,String parentCode,String standard,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_ARCHITECTURE);
        request.putParams("name",name);
        request.putParams("parentCode",parentCode);
        request.putParams("standard",standard);
        HttpManager.getInstance().doPost(request,callBack);
    }
/**
     *返回带下级列表的请求项
     * @param
     * @param callBack
     */
    public static void getArchitectureV2(String name,String parentCode,String standard,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_ARCHITECTURE_V2);
        request.putParams("name",name);
        request.putParams("parentCode",parentCode);
        request.putParams("standard",standard);
        HttpManager.getInstance().doPost(request,callBack);
    }


    /**
     * 得到通知，1为系统消息 2为工作通知
     * @param
     * @param callBack
     */
    public static void getMessage(int type,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_MESSAGE);
        request.putParams("type",type);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 添加收藏
     * @param
     * @param callBack
     */
    public static void addFavorite(String typeCode,int userId ,int lawId,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(ADD_FAVORITE);
        request.putParams("typeCode",typeCode);
        request.putParams("userId",userId);
        request.putParams("lawId",lawId);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 取消收藏
     * @param
     * @param callBack
     */
    public static void cancelFavorite(String typeCode,int userId ,int lawId,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(CANCEL_FAVORITE);
        request.putParams("typeCode",typeCode);
        request.putParams("userId",userId);
        request.putParams("lawId",lawId);
        HttpManager.getInstance().doPost(request,callBack);
    }

    /**
     * 获取收藏列表
     * @param
     * @param callBack
     */
    public static void getFavoriteList(String typeCode,String userId ,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_FAVORITE);
        request.putParams("typeCode",typeCode);
        request.putParams("userId",userId);
        HttpManager.getInstance().doPost(request,callBack);
    }
    /**
     * 获取最近查询列表
     * @param
     * @param callBack
     */
    public static void getNewLawList(String userId ,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_NEWLAW);
        request.putParams("userId",userId);
        HttpManager.getInstance().doPost(request,callBack);
    }
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
        request.putParams("userId", UserInfo.getUserInfoInstance().getId());
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
        request.putParams("userId",UserInfo.getUserInfoInstance().getId());
        request.putParams("typeCode","wxhxp");
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
     * 注册
     * */
    public static void updateUserInfo(ResponseUserInfo info, HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(REGIST);
        request.putParams("loginName","");
        request.putParams("nickName","");
        request.putParams("realName",info.getReal_name());
        request.putParams("departmentId",info.getDepartment_id());
        request.putParams("regionId",info.getRegion_id());
        request.putParams("email",info.getEmail());
        request.putParams("idNo",info.getId_no());
        request.putParams("jobId",info.getJob_id());
        request.putParams("phone",info.getPhone());
        request.putParams("sex",info.getSex());
        request.putParams("userType","");
        request.putParams("licenseType",info.getLicense_type());
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

    /**
     * 获取用户信息
     * */
    public static void getUserInfo(String userId,HttpCallBack callBack){
        ZCRequest request=new ZCRequest();
        request.setUrl(GET_USERINFO);
        request.putParams("userId",userId);
        HttpManager.getInstance().doPost(request,callBack);
    }


}

