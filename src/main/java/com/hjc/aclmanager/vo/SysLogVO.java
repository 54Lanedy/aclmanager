package com.hjc.aclmanager.vo;

import com.hjc.aclmanager.eums.LogEum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogVO {
    private String id;

    private Integer type;
    private String typeStr;

    private String targetId;

    private String oldValue;

    private String newValue;

    private String operator;

    private String operatorTime;

    private String operatorIp;

    private Integer status;

    public String getTypeStr() {
        typeStr = LogEum.getText("type", this.type);
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}