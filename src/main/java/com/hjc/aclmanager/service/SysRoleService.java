package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjc.aclmanager.dto.RoleDTO;
import com.hjc.aclmanager.entity.SysRole;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.TransferVO;
import com.hjc.aclmanager.vo.TreeVO;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/15 14:46
 */
public interface SysRoleService extends IService<SysRole> {
    JsonData saveByForm(RoleDTO dto);

    List<SysRole> roleList(String aclModuleId);

    List<TreeVO> roleAndAclTree(String aclModuleId, String roleId);

}
