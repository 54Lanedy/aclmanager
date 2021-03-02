package com.hjc.aclmanager.controller;

import com.hjc.aclmanager.dto.AclModuleDTO;
import com.hjc.aclmanager.dto.RoleDTO;
import com.hjc.aclmanager.entity.SysRole;
import com.hjc.aclmanager.entity.SysUser;
import com.hjc.aclmanager.service.*;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.TransferVO;
import com.hjc.aclmanager.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/14 17:35
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysServiceImpl sysService;
    @Autowired
    private SysRoleAclService sysRoleAclService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @RequestMapping("/role.page")
    public ModelAndView rolePage() {
        return new ModelAndView("sys/role");
    }

    @RequestMapping("/list.json")
    public List<SysRole> roleList(String aclModuleId){
        return sysRoleService.roleList(aclModuleId);
    }

    @RequestMapping("/save.json")
    public JsonData saveAclModule(RoleDTO dto) {
        return sysRoleService.saveByForm(dto);
    }

    @RequestMapping("/roleAndAclTree.json")
    public List<TreeVO> roleAndAclTree(String aclModuleId,String roleId){
        return sysRoleService.roleAndAclTree(aclModuleId,roleId);
    }

    @RequestMapping("/saveRoleAndAcl.json")
    public JsonData saveRoleAndAcl(@RequestParam(value="aclIds[]", required=false) String[] aclIds, String roleId){
        return sysRoleAclService.saveRoleAndAcl(aclIds, roleId);
    }

    @RequestMapping("/getUserFormatTransfer.json")
    public JsonData getUser(String roleId){
        return sysService.getUser(roleId);
    }

    @RequestMapping("/saveRoleAndUser.json")
    public JsonData saveRoleAndUser(@RequestParam(value="userIds[]", required=false) String[] userIds, String roleId){
        return sysRoleUserService.saveRoleAndUser(userIds, roleId);
    }

}
