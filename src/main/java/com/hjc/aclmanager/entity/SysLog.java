package com.hjc.aclmanager.entity;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class SysLog {
    private String id;

    private Integer type;

    private String targetId;

    private String oldValue;

    private String newValue;

    private String operator;

    private String operatorTime;

    private String operatorIp;

    private Integer status;
}