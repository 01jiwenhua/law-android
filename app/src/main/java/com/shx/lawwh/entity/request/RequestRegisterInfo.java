package com.shx.lawwh.entity.request;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.shx.lawwh.BR;

import static android.R.string.no;

/**
 * Created by zhou on 2018/1/31.
 * 注册时完善个人信息
 */

public class RequestRegisterInfo extends BaseObservable {

    private String loginName;
    private String loginPassword;
    private String nickName;
    private String realName;
    private String departmentId;
    private String regionId;
    private String email;
    private String idNo;
    private String jobId;
    private String phone;
    private String sex;
    private String userType;
    private String licenseType;



    @Bindable
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
        notifyPropertyChanged(BR.loginName);
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
      this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
        notifyPropertyChanged(BR.idNo);
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }
}
