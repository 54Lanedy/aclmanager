package com.hjc.aclmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * log 查询sql的参数封装类
 */
@Getter
@Setter
@ToString
public class SearchLogDTO {

    private Integer type; // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime;//yyyy-MM-dd HH:mm:ss

    private String toTime; //yyyy-MM-dd HH:mm:ss
}
