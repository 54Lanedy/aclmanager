package com.hjc.aclmanager.vo;

import com.hjc.aclmanager.eums.AclEum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/14 15:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysAclVO  {
    private String id;

    private String code;

    private String name;
    private String text;

    private String aclModuleId;
    private String aclModuleName;

    private String parentId;

    private String url;
    private String iconCls;

    private Integer type;
    private String typeStr;

    private Integer status;
    private String statusStr;

    private Integer seq;

    private String remark;

    private List<SysAclVO> children;

    public String getTypeStr() {
        typeStr = AclEum.getText("type", this.type);
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getStatusStr() {
        statusStr = AclEum.getText("status", this.status);
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getText() {
        text = this.name;
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
