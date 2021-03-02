package com.hjc.aclmanager.vo;

import lombok.Data;

/**
 * 穿梭框数据VO
 * Created by liyue
 * Time 2021/1/18 14:38
 */
@Data
public class TransferVO {
    /**
     * 用户ID
     */
    private String value;
    /**
     * 姓名
     */
    private String title;

    //是否可选中
    private boolean disabled;
}
