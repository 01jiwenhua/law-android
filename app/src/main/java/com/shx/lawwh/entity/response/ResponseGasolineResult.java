package com.shx.lawwh.entity.response;

import java.io.Serializable;

/**
 * Created by adm on 2018/2/8.
 */

public class ResponseGasolineResult  implements Serializable{


    private static final long serialVersionUID = 872626360233922721L;
    /**
     * deviceInId : 13
     * distance : 18
     * id : 372
     * instruction : 汽油设备与站外建（构）筑物的安全间距（m）
     * noteId : 1
     * standard : GB 50156-2012 汽油加油加气站设计与施工规范
     * structureOutId : 54
     * tableNo : 表4.0.4
     */

    private int deviceInId;
    private String distance;
    private int id;
    private String instruction;
    private int noteId;
    private String standard;
    private int structureOutId;
    private String tableNo;
    private String noteContent;

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public int getDeviceInId() {
        return deviceInId;
    }

    public void setDeviceInId(int deviceInId) {
        this.deviceInId = deviceInId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public int getStructureOutId() {
        return structureOutId;
    }

    public void setStructureOutId(int structureOutId) {
        this.structureOutId = structureOutId;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }
}
