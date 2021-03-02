package com.hjc.aclmanager.controller;

import com.google.common.collect.Maps;
import com.hjc.aclmanager.entity.SysAcl;
import com.hjc.aclmanager.entity.SysUser;
import com.hjc.aclmanager.service.SysAclService;
import com.hjc.aclmanager.service.SysRoleService;
import com.hjc.aclmanager.service.SysRoleUserService;
import com.hjc.aclmanager.service.SysUserService;
import com.hjc.aclmanager.utils.Md5Util;
import com.hjc.aclmanager.utils.MyUtil;
import com.hjc.aclmanager.vo.SysAclVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liyue
 * Time 2021/1/12 9:17
 */
@RestController
public class LoginController {

    @Value("${server.servlet.context-path}")
    public String RootPath;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    /**
     * 登出
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        String path = RootPath + "/login.page";
        response.sendRedirect(path);
    }

    /**
     * 登陆
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/signin.page")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,String username, String password) throws IOException, ServletException {
        HashMap<String, Object> map = Maps.newHashMap();
        SysUser sysUser = sysUserService.findByJobNum(username);
        String errorMsg = "";
        String ret = request.getParameter("ret");

        if (StringUtils.isBlank(username)) {
            errorMsg="用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (sysUser==null) {
            errorMsg = "查询不到指定的用户";
        } else if (!sysUser.getPwd().equals(Md5Util.md5(password))) {
            errorMsg = "用户名或密码错误";
        }else {
            // login success
            HttpSession session = request.getSession();
            session.setAttribute("USER_SESSION",sysUser);
            map.put("realName", sysUser.getRealName());
            // 读取用户角色下的菜单资源
            List<String> roleIds = sysRoleUserService.getRoleIdsByUserId(sysUser.getId(),"ACLMANAGER");
            if (CollectionUtils.isEmpty(roleIds)) {
                map.put("error", "您没有访问该系统的权限，请联系管理员");
                return new ModelAndView("login", map);
            }
            List<SysAcl> acls =  sysAclService.getAclsByRoleIds(roleIds);
            List<SysAclVO> aclVOs = acls.stream().map(acl -> {
                SysAclVO vo = new SysAclVO();
                BeanUtils.copyProperties(acl, vo);
                return vo;
            }).sorted(Comparator.comparing(SysAclVO::getSeq)).collect(Collectors.toList());
            //父节点
            List<SysAclVO> menus = aclVOs.stream().filter(o -> o.getParentId().equals("ROOT")).collect(Collectors.toList());
            //遍历父节点下的子节点
            menus.forEach(menu -> {
                List<SysAclVO> links = aclVOs.stream().filter(o -> o.getParentId().equals(menu.getId()))
                        .sorted(Comparator.comparing(SysAclVO::getSeq)).collect(Collectors.toList());
                menu.setChildren(links);
            });
            session.setAttribute("acls", menus);
            //----
            return new ModelAndView("main", map);
        }
        //login fail
        map.put("error", errorMsg);
        return new ModelAndView("login", map);
    }

    @RequestMapping("/login.page")
    public ModelAndView login(){
        Map<String, Object> map = Maps.newHashMap();
        map.put("error", "");
        return new ModelAndView("login", map);
    }

}
