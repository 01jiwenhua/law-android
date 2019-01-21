package com.shx.lawwh.entity.response;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by zhou on 2018/2/1.
 */

public class ResponseUserInfo implements Serializable {


    private static final long serialVersionUID = 5558410932748370767L;
    /**
     * company_id : 1
     * departmentName : 安全监督管理一处
     * department_id : 1
     * email : shx@163.com
     * id : 1
     * id_no : 131124199005182216
     * jobNmae : 处长
     * job_id : 1
     * license_type : 1
     * name : 北京市安全生产监督管理局
     * name_en : bjaj
     * nick_name : 轩
     * phone : 13341195865
     * real_name : 邵鸿轩
     * region_id : 1
     * sex : 1
     */

    private int company_id;
    private String departmentName;
    private int department_id;
    private String email;
    private int id;
    private String id_no;
    private String jobNmae;
    private int job_id;
    private int license_type;
    private String name;
    private String name_en;
    private String nick_name;
    private String phone;
    private String real_name;
    private int region_id;
    private int sex = -1;
    private String head_icon;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHead_icon() {
        if (TextUtils.isEmpty(head_icon)) {
            return head_icon;
        } else {
            head_icon= head_icon.replace("\\", "/");
        }
        return head_icon;
    }

    public void setHead_icon(String head_icon) {
        this.head_icon = head_icon;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getJobNmae() {
        return jobNmae;
    }

    public void setJobNmae(String jobNmae) {
        this.jobNmae = jobNmae;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getLicense_type() {
        return license_type;
    }

    public void setLicense_type(int license_type) {
        this.license_type = license_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
