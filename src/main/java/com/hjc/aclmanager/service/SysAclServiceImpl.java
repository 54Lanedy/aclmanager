package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.hjc.aclmanager.config.RequestHolder;
import com.hjc.aclmanager.dto.AclDTO;
import com.hjc.aclmanager.entity.SysAcl;
import com.hjc.aclmanager.mapper.SysAclMapper;
import com.hjc.aclmanager.utils.BeanValidator;
import com.hjc.aclmanager.utils.IpUtil;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.utils.MyUtil;
import com.hjc.aclmanager.vo.SysAclVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liyue
 * Time 2021/1/14 14:35
 */
@Service
public class SysAclServiceImpl extends ServiceImpl<SysAclMapper, SysAcl> implements SysAclService {
    @Autowired
    private SysLogService sysLogService;

    @Override
    public List<SysAclVO> searchAclByModuleId(String aclModuleId) {
        List<SysAclVO> list = this.baseMapper.searchAclByModuleId(aclModuleId);
        List<SysAclVO> rootList = list.stream().filter(o -> o.getParentId().equals("ROOT")).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(rootList)) {
            rootList.sort(Comparator.comparing(SysAclVO::getSeq));
            rootList.forEach(root -> {
                List<SysAclVO> children = list.stream().filter(o -> StringUtils.isNotEmpty(o.getParentId()) && o.getParentId().equals(root.getId())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(children)) {
                    this.handleAclChildrenTree(list, children);
                    children.sort(Comparator.comparing(SysAclVO::getSeq));
                    root.setChildren(children);
                }
            });
        }
        return rootList;
    }

    //权限菜单树形子菜单递归方法
    private void handleAclChildrenTree(List<SysAclVO> list,List<SysAclVO> children){
        children.forEach(root -> {
            List<SysAclVO> nodes = list.stream().filter(o -> StringUtils.isNotEmpty(o.getParentId()) && o.getParentId().equals(root.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(nodes)) {
                this.handleAclChildrenTree(list, nodes);
                nodes.sort(Comparator.comparing(SysAclVO::getSeq));
                root.setChildren(nodes);
            }

        });
    }

    @Override
    @Transactional
    public JsonData saveByForm(AclDTO dto) {
        BeanValidator.check(dto);
        if (checkExist(dto.getAclModuleId(), dto.getName(), dto.getId(), dto.getParentId())) {
            return JsonData.fail("当前权限模块下面存在相同名称的权限点");
        }
        String nowDateStr = MyUtil.DATE_F2.format(new Date());
        String realName = RequestHolder.getCurrentUser().getRealName();
        SysAcl before = null;
        int insert = 0;
        SysAcl acl = SysAcl.builder().name(dto.getName()).aclModuleId(dto.getAclModuleId()).url(dto.getUrl())
                .type(dto.getType()).status(dto.getStatus()).seq(dto.getSeq()).remark(dto.getRemark()).iconCls(dto.getIconCls())
                .build();
        acl.setCode(generateCode(dto.getUrl()));
        if (StringUtils.isNotEmpty(dto.getParentId())) {
            acl.setParentId(dto.getParentId());
        }
        if (StringUtils.isEmpty(dto.getId())) {
            acl.setAddDate(nowDateStr);
            acl.setAddUser(realName);
            insert = this.baseMapper.insert(acl);
        } else {
            before = this.baseMapper.selectById(dto.getId());
            Preconditions.checkNotNull(before, "待更新的权限菜单不存在");

            acl.setId(dto.getId());
            acl.setOperator(RequestHolder.getCurrentUser().getRealName());
            acl.setOperatorTime(nowDateStr);
            acl.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            acl.setAddUser(before.getAddUser());
            acl.setAddDate(before.getAddDate());
            insert = this.baseMapper.updateById(acl);
        }
        if (insert == 0) {
            return JsonData.fail("操作失败");
        }
        sysLogService.saveAclLog(before, acl);
        return JsonData.success("操作成功");
    }

    @Override
    public List<SysAcl> getAclsByRoleIds(List<String> roleIds) {
        return this.baseMapper.getAclsByRoleIds(roleIds);
    }

    private boolean checkExist(String aclModuleId, String name, String id, String parentId) {
        return this.baseMapper.countByNameAndAclModuleId(aclModuleId, name, id, parentId) > 0;
    }

    private String generateCode(String url) {
        return url.replaceFirst("/", "").replaceAll("/", ":") + ":*";
    }


}
