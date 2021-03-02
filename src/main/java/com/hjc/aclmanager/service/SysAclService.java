package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hjc.aclmanager.dto.AclDTO;
import com.hjc.aclmanager.entity.SysAcl;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.SysAclVO;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/14 14:34
 */
public interface SysAclService extends IService<SysAcl> {
    List<SysAclVO> searchAclByModuleId(String aclModuleId);

    JsonData saveByForm(AclDTO dto);

    List<SysAcl> getAclsByRoleIds(List<String> roleIds);
}
