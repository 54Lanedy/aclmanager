package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjc.aclmanager.entity.SysRoleUser;
import com.hjc.aclmanager.utils.JsonData;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/18 16:21
 */
public interface SysRoleUserService extends IService<SysRoleUser> {
    JsonData saveRoleAndUser(String[] userIds, String roleId);
    List<String> getRoleIdsByUserId(String userId, String systemCode);

}
