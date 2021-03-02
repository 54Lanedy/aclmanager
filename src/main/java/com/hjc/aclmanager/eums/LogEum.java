package com.hjc.aclmanager.eums;

/**
 * Created by liyue
 * Time 2021/1/25 17:37
 */
public enum  LogEum {
    TYPE_3("type", 3, "权限模块"), TYPE_4("type", 4, "权限菜单"), TYPE_5("type", 5, "角色")
    , TYPE_6("type", 6, "角色权限关系"), TYPE_7("type", 7, "角色用户关系");

    private String att;// 属性
    private int key;//
    private String text;

    // 普通方法
    public static String getText(String att, int key) {
        for (LogEum c : LogEum.values()) {
            if (c.getAtt().equals(att.trim())) {
                if (c.getKey() == key) {
                    return c.text;
                }
            }
        }
        return null;
    }

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private LogEum(String att, int key, String text) {
        this.att = att;
        this.key = key;
        this.text = text;
    }
}
