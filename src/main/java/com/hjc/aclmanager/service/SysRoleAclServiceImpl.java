package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hjc.aclmanager.config.RequestHolder;
import com.hjc.aclmanager.entity.SysLog;
import com.hjc.aclmanager.entity.SysRoleAcl;
import com.hjc.aclmanager.mapper.SysLogMapper;
import com.hjc.aclmanager.mapper.SysRoleAclMapper;
import com.hjc.aclmanager.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by liyue
 * Time 2021/1/16 11:43
 */
@Service
public class SysRoleAclServiceImpl extends ServiceImpl<SysRoleAclMapper, SysRoleAcl> implements SysRoleAclService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    @Transactional
    public JsonData saveRoleAndAcl(String[] aclIds, String roleId) {
        if (StringUtils.isNotEmpty(roleId)) {
            List<String> aclIdList = (aclIds != null && aclIds.length > 0) ? Arrays.asList(aclIds) : Lists.newArrayList();
            //检查重复start
            //先获取当前角色的所有旧的权限
            List<String> originAclIdList = this.baseMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
            //检查修改前后的权限是否相同，长度相等才有可能相同
            if (originAclIdList.size() == aclIdList.size()) {
                //长度相等后去重
                Set<String> originAclIdSet = Sets.newHashSet(originAclIdList);
                Set<String> aclIdSet = Sets.newHashSet(aclIdList);
                //两个set相减，检查是否为null,若为null则视为没有任何修改
                originAclIdSet.removeAll(aclIdSet);
                if (CollectionUtils.isEmpty(originAclIdSet)) {
                    return JsonData.fail("角色权限无改动");
                }
            }
            //检查重复end
            String realName = RequestHolder.getCurrentUser().getRealName();
            String nowDateStr = MyUtil.DATE_F2.format(new Date());

            //遍历权限节点id
            List<SysRoleAcl> roleAclList = Lists.newArrayList();
            aclIdList.forEach(aclId -> {
                SysRoleAcl roleAcl = new SysRoleAcl();
                roleAcl.setId(MyUtil.getUUID());
                roleAcl.setAclId(aclId);
                roleAcl.setRoleId(roleId);
                roleAcl.setOperator(realName);
                roleAcl.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                roleAcl.setOperatorTime(nowDateStr);
                roleAclList.add(roleAcl);
            });
            //批量保存前先删除旧关联
            QueryWrapper<SysRoleAcl> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            this.baseMapper.delete(queryWrapper);
            if (CollectionUtils.isNotEmpty(roleAclList)) {
                this.baseMapper.batchInsert(roleAclList);
            }
            //保存操作日志
            this.saveRoleAclLog(roleId, originAclIdList, aclIdList);
            return JsonData.success("操作成功");
        }
        return JsonData.fail("操作失败，已选权限为空");
    }

    private void saveRoleAclLog(String roleId, List<String> before, List<String> after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
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
