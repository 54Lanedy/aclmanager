package com.hjc.aclmanager.entity;

import lombok.*;

import java.util.Date;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SysAcl {
    private String id;

    private String code;

    private String name;

    private String aclModuleId;
    private String parentId;

    private String url;
    private String iconCls;

    private Integer type;

    private Integer status;

    private Integer seq;

    private String remark;

    private String operator;

    private String operatorTime;

    private String operatorIp;
    private String addUser;
    private String addDate;

}