package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjc.aclmanager.entity.SysUser;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.TransferVO;

import java.util.List;

/**
 * OA用户信息接口
 * Created by liyue
 * Time 2021/1/12 9:26
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findByJobNum(String username);


}
