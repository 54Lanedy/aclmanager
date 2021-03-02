package com.hjc.aclmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRole {
    private String id;
    private String aclModuleId;

    private String name;
    private String code;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private String operatorTime;

    private String operatorIp;
    private String addUser;
    private String addDate;
}