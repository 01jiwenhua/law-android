package com.shx.lawwh.common;

/**
 * Created by xuan on 2017/12/25.
 */

public enum ChemicalsEnum {
    LHTZ("理化特征", 0), WXXMS("危险性描述", 2), JJCS("急救措施", 3), XFCS("消防措施", 4), XLYJCL("泄漏应急处理", 5), CZSZ("操作设置", 6), JCKZ("接触控制", 7), WDXHFYHUOX("稳定性和反应活性", 8)
    , DLXZL("毒理学资料", 9), YSXX("运输信息", 10), CLFA("处理方案", 4);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ChemicalsEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (ChemicalsEnum c : ChemicalsEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
