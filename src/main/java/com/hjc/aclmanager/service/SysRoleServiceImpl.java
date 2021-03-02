package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.hjc.aclmanager.config.RequestHolder;
import com.hjc.aclmanager.dto.RoleDTO;
import com.hjc.aclmanager.entity.SysRole;
import com.hjc.aclmanager.exception.ParamException;
import com.hjc.aclmanager.mapper.SysAclMapper;
import com.hjc.aclmanager.mapper.SysRoleMapper;
import com.hjc.aclmanager.utils.BeanValidator;
import com.hjc.aclmanager.utils.IpUtil;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.utils.MyUtil;
import com.hjc.aclmanager.vo.TransferVO;
import com.hjc.aclmanager.vo.TreeVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liyue
 * Time 2021/1/15 14:47
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysLogService sysLogService;

    @Override
    public List<SysRole> roleList(String aclModuleId) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.eq("acl_module_id", aclModuleId).orderByAsc("add_date");
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    @Transactional
    public JsonData saveByForm(RoleDTO dto) {
        BeanValidator.check(dto);
        if (checkExist(dto.getName(),dto.getId(),dto.getAclModuleId())) {
            return JsonData.fail("同一层级下存在相同名称的权限模块");
        }
        String nowDateStr = MyUtil.DATE_F2.format(new Date());
        String realName = RequestHolder.getCurrentUser().getRealName();
        SysRole before = null;
        int insert = 0;
        SysRole role = SysRole.builder().name(dto.getName()).status(dto.getStatus()).type(dto.getType())
                .remark(dto.getRemark()).aclModuleId(dto.getAclModuleId()).code(dto.getCode()).build();
        if (StringUtils.isEmpty(dto.getId())) {
            role.setAddDate(nowDateStr);
            role.setAddUser(realName);
            insert = this.baseMapper.insert(role);
        } else {
            before = this.baseMapper.selectById(dto.getId());
            Preconditions.checkNotNull(before,"待更新的权限系统不存在");

            role.setId(dto.getId());
            role.setOperator(realName);
            role.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            role.setOperatorTime(nowDateStr);
            role.setAddDate(before.getAddDate());
            role.setAddUser(before.getAddUser());
            insert = this.baseMapper.updateById(role);
        }
        if (insert == 0) {
            return JsonData.fail("操作失败");
        }
        sysLogService.saveRoleLog(before, role);
        return JsonData.success("操作成功");
    }

    private boolean checkExist(String name,String id,String aclModuleId){
        return this.baseMapper.countByName(name, id ,aclModuleId) > 0;
    }

    @Override
    public List<TreeVO> roleAndAclTree(String aclModuleId, String roleId) {
        //全部权限菜单
        List<TreeVO> allAclList = this.baseMapper.getAllAclList(aclModuleId);
        List<String> aclIdList = this.baseMapper.getAclIdListByRoleId(roleId);
        List<TreeVO> rootList = allAclList.stream().filter(o -> o.getParentId().equals("ROOT")).collect(Collectors.toList());
        rootList.forEach(root -> {
            List<TreeVO> childrenList = allAclList.stream().filter(o -> o.getParentId().equals(root.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(childrenList)) {
                childrenList.forEach(ch -> {
                    if (aclIdList.contains(ch.getId())) {
                        ch.setChecked(true);
                    }
                });
                this.handleRoleAndAclChildrenNode(allAclList, aclIdList, childrenList);
                root.setChildren(childrenList);
            }
        });
        return rootList;
    }

    //角色与权限树的递归方法
    private void handleRoleAndAclChildrenNode(List<TreeVO> allAclList,List<String> aclIdList, List<TreeVO> childrenList){
        childrenList.forEach(ch -> {
            List<TreeVO> nodeList = allAclList.stream().filter(o -> o.getParentId().equals(ch.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(nodeList)) {
                nodeList.forEach(node -> {
                    if (aclIdList.contains(node.getId())) {
                        node.setChecked(true);
                    }
                });
                this.handleRoleAndAclChildrenNode(allAclList, aclIdList, nodeList);
                ch.setChildren(nodeList);
            }
        });
    }

}
