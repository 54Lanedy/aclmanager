package com.hjc.aclmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysAclModule {
    private String id;

    private String name;

    private String systemCode;

    private Integer seq;

    private Integer status;

    private String remark;

    private String operator;

    private String operatorTime;

    private String operatorIp;
    private String addUser;
    private String addDate;

}