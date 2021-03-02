package com.hjc.aclmanager.service;

import com.google.common.collect.Maps;
import com.hjc.aclmanager.mapper.SysRoleUserMapper;
import com.hjc.aclmanager.mapper.SysUserMapper;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.utils.MyUtil;
import com.hjc.aclmanager.vo.IconVO;
import com.hjc.aclmanager.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liyue
 * Time 2021/1/15 8:50
 */
@Service
public class SysServiceImpl {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    public List<IconVO> iconList(HttpServletRequest req){
        StringBuffer iconDir = new StringBuffer("/").append(MyUtil.getRootPath());
        if (iconDir.indexOf("classes") == -1) {
            iconDir.append("WEB-INF/classes/");
        }
        iconDir.append("static/plug-in/css/icons");
        System.out.println(iconDir);
        File file = new File(iconDir.toString());
        File[] files = file.listFiles();
        List<String> list = Arrays.stream(files).filter(File::isFile).map(File::getName).collect(Collectors.toList());
        return list.stream().map(str -> new IconVO("icon-" + str.substring(0, str.lastIndexOf(".")).replace("_","-"), req.getContextPath() + "/plug-in/css/icons/" + str)).collect(Collectors.toList());
    }


    public JsonData getUser(String roleId) {
        Map<String, Object> dataMap = Maps.newHashMap();
        //左侧数据
        List<TransferVO> allUserList = sysUserMapper.getUser();
        dataMap.put("data", allUserList);
        //右侧数据(查询角色用户列表)
        List<String> haveUserIdList = sysRoleUserMapper.getUserIdByRoleId(roleId);
        dataMap.put("value", haveUserIdList);

        return JsonData.success(dataMap);
    }
}
