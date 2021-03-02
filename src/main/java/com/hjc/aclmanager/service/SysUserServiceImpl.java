package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.hjc.aclmanager.entity.SysUser;
import com.hjc.aclmanager.mapper.SysUserMapper;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by liyue
 * Time 2021/1/12 9:30
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Override
    public SysUser findByJobNum(String username) {
        return this.baseMapper.findByJobNum(username);
    }

}
