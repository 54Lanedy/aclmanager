package com.hjc.aclmanager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by liyue
 * Time 2021/1/12 9:27
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId
    private String id;
    /**
     * 姓名
     */
    private String realName;
    //工号
    private String jobNum;
    /**
     * 密码
     */
    private String pwd;
}
