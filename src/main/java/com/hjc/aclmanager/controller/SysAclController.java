package com.hjc.aclmanager.controller;

import com.hjc.aclmanager.dto.AclDTO;
import com.hjc.aclmanager.service.SysAclService;
import com.hjc.aclmanager.service.SysServiceImpl;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.IconVO;
import com.hjc.aclmanager.vo.SysAclVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/14 11:44
 */
@RestController
@RequestMapping("/sys/acl")
public class SysAclController {
    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysServiceImpl sysService;

    @RequestMapping("/list.json")
    public List<SysAclVO> aclList(String aclModuleId){
        return sysAclService.searchAclByModuleId(aclModuleId);
    }

    @RequestMapping("/save.json")
    public JsonData save(AclDTO dto){
        return sysAclService.saveByForm(dto);
    }

    @RequestMapping("/iconList.json")
    public List<IconVO> iconList(HttpServletRequest req){
        return sysService.iconList(req);
    }
}
