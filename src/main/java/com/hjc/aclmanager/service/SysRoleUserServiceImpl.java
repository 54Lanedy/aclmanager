package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hjc.aclmanager.config.RequestHolder;
import com.hjc.aclmanager.entity.SysLog;
import com.hjc.aclmanager.entity.SysRoleUser;
import com.hjc.aclmanager.mapper.SysLogMapper;
import com.hjc.aclmanager.mapper.SysRoleUserMapper;
import com.hjc.aclmanager.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liyue
 * Time 2021/1/18 16:22
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public JsonData saveRoleAndUser(String[] userIds, String roleId) {
        if (StringUtils.isNotEmpty(roleId)) {
            List<String> newUserIdList = (userIds != null && userIds.length > 0) ? Arrays.asList(userIds) : Lists.newArrayList();
            //检查重复start
            //旧的角色与用户数据
            List<String> oldUserIdList = this.baseMapper.getUserIdByRoleId(roleId);
            if (newUserIdList.size() == oldUserIdList.size()) {
                //长度相等后去重
                Set<String> originUserIdSet = Sets.newHashSet(oldUserIdList);
                Set<String> userIdSet = Sets.newHashSet(newUserIdList);
                //两个set相减，检查是否为null,若为null则视为没有任何修改
                originUserIdSet.removeAll(userIdSet);
                if (CollectionUtils.isEmpty(originUserIdSet)) {
                    return JsonData.fail("角色用户无改动");
                }
            }
            //检查重复end

            String realName = RequestHolder.getCurrentUser().getRealName();
            String nowDateStr = MyUtil.DATE_F2.format(new Date());

            List<SysRoleUser> roleUserList = Lists.newArrayList();
            newUserIdList.forEach(userId -> {
                SysRoleUser roleUser = new SysRoleUser();
                roleUser.setId(MyUtil.getUUID());
                roleUser.setRoleId(roleId);
                roleUser.setUserId(userId);
                roleUser.setOperator(realName);
                roleUser.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                roleUser.setOperatorTime(nowDateStr);
                roleUserList.add(roleUser);
            });
            //批量保存前先删除旧关联
            QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            this.baseMapper.delete(queryWrapper);
            if (CollectionUtils.isNotEmpty(roleUserList)) {
                this.baseMapper.batchInsert(roleUserList);
            }

            this.saveRoleUserLog(roleId, oldUserIdList, newUserIdList);
            return JsonData.success("操作成功");
        }
        return JsonData.fail("操作失败，已选用户为空");
    }


    @Override
    public List<String> getRoleIdsByUserId(String userId, String systemCode) {
        return this.baseMapper.getRoleIdsByUserId(userId,systemCode);
    }

    private void saveRoleUserLog(String roleId, List<String> before, List<String> after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ROLE_USER);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getRealName());
        sysLog.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
        sysLog.setStatus(1);
        sysLogMapper.insert(sysLog);
    }
}
