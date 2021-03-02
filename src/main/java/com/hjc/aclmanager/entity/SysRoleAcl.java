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
public class SysRoleAcl {
    private String id;

    private String roleId;

    private String aclId;

    private String operator;

    private String operatorTime;

    private String operatorIp;

}