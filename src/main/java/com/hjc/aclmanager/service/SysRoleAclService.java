package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjc.aclmanager.entity.SysRoleAcl;
import com.hjc.aclmanager.utils.JsonData;

/**
 * Created by liyue
 * Time 2021/1/16 11:43
 */
public interface SysRoleAclService extends IService<SysRoleAcl> {
    JsonData saveRoleAndAcl(String[] aclIds, String roleId);

}
