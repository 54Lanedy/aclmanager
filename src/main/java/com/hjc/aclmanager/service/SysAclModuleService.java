package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjc.aclmanager.dto.AclModuleDTO;
import com.hjc.aclmanager.entity.SysAclModule;
import com.hjc.aclmanager.utils.JsonData;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/13 16:18
 */
public interface SysAclModuleService extends IService<SysAclModule> {

    List<SysAclModule> aclModuleList();

    JsonData saveByForm(AclModuleDTO dto);
}
