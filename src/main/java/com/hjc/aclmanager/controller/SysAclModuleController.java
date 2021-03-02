package com.hjc.aclmanager.controller;

import com.hjc.aclmanager.dto.AclModuleDTO;
import com.hjc.aclmanager.entity.SysAclModule;
import com.hjc.aclmanager.service.SysAclModuleService;
import com.hjc.aclmanager.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 权限菜单管理
 * Created by liyue
 * Time 2021/1/13 11:13
 */
@RestController
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService sysAclModuleService;

    @RequestMapping("/aclModule.page")
    public ModelAndView aclManagerMain(){
        return new ModelAndView("sys/aclModule");
    }

    @RequestMapping("/list.json")
    public List<SysAclModule> aclModuleList(){
        return sysAclModuleService.aclModuleList();
    }

    @RequestMapping("/save.json")
    public JsonData saveAclModule(AclModuleDTO dto) {
        return sysAclModuleService.saveByForm(dto);
    }
}
