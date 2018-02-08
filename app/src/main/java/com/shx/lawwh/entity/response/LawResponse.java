package com.shx.lawwh.entity.response;

import java.io.Serializable;

/**
 * Created by xuan on 2017/12/24.
 */

public class LawResponse implements Serializable{

    /**
     * content :
     * description : 为了加强安全生产工作，防止和减少事故保障人民群众 第一条为了加强安全生产工作，防止和减少事故保障人民群众 生命和财产安全，促进经济社会持续健康发展制定本法。
     * effectiveTime : 1417363200000
     * filePath : 中华人民共和国安全生产法
     * id : 1
     * issueNo : 主席令第13号修订
     * lawName : 中华人民共和国安全生产法
     * levleCode : gjfl
     * publishOrg : 全国人民代表大会常务委员会
     * publishTime : 1409414400000
     * typeCode : flfg
     */

    private String content;
    private String description;
    private long effectiveTime;
    private String filePath;
    private int id;
    private String issueNo;
    private String lawName;
    private String levleCode;
    private String publishOrg;
    private long publishTime;
    private String typeCode;
    private int is_favorite;
    private String fileFrom;

    public String getFileFrom() {
        return fileFrom;
    }

    public void setFileFrom(String fileFrom) {
        this.fileFrom = fileFrom;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(String issueNo) {
        this.issueNo = issueNo;
    }

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public String getLevleCode() {
        return levleCode;
    }

    public void setLevleCode(String levleCode) {
        this.levleCode = levleCode;
    }

    public String getPublishOrg() {
        return publishOrg;
    }

    public void setPublishOrg(String publishOrg) {
        this.publishOrg = publishOrg;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
