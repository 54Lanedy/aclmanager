package com.hjc.aclmanager.eums;

/**
 * Created by liyue
 * Time 2021/1/14 16:57
 */
public enum AclEum {
    STATUS_0("status", 0, "冻结"), STATUS_1("status", 1, "正常")
    , TYPE_0("type", 0, "显示菜单"), TYPE_1("type", 1, "链接菜单"), TYPE_2("type", 2, "按钮"), TYPE_99("type", 99, "其他");
    
    private String att;// 属性
    private int key;//
    private String text;

    // 普通方法
    public static String getText(String att, int key) {
        for (AclEum c : AclEum.values()) {
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

    private AclEum(String att, int key, String text) {
        this.att = att;
        this.key = key;
        this.text = text;
    }
}
