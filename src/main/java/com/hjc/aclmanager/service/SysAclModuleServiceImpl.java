package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.hjc.aclmanager.config.RequestHolder;
import com.hjc.aclmanager.dto.AclModuleDTO;
import com.hjc.aclmanager.entity.SysAclModule;
import com.hjc.aclmanager.exception.ParamException;
import com.hjc.aclmanager.mapper.SysAclModuleMapper;
import com.hjc.aclmanager.utils.BeanValidator;
import com.hjc.aclmanager.utils.IpUtil;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.utils.MyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/13 16:20
 */
@Service
public class SysAclModuleServiceImpl extends ServiceImpl<SysAclModuleMapper, SysAclModule> implements SysAclModuleService {

    @Autowired
    private SysLogService sysLogService;

    @Override
    public List<SysAclModule> aclModuleList() {
        List<SysAclModule> list = this.baseMapper.selectList(null);
        list.sort(Comparator.comparing(SysAclModule::getSeq));
        return list;
    }


    @Override
    @Transactional
    public JsonData saveByForm(AclModuleDTO dto) {
        BeanValidator.check(dto);
        if (checkExist(dto.getSystemCode(),dto.getName(),dto.getId())) {
            return JsonData.fail("同一层级下存在相同名称的权限模块");
        }
        String nowDateStr = MyUtil.DATE_F2.format(new Date());
        String realName = RequestHolder.getCurrentUser().getRealName();
        SysAclModule before = null;
        int insert = 0;
        SysAclModule aclModule = SysAclModule.builder().name(dto.getName()).seq(dto.getSeq())
                .status(dto.getStatus()).remark(dto.getRemark()).systemCode(dto.getSystemCode()).build();
        if (StringUtils.isEmpty(dto.getId())) {
            aclModule.setAddDate(nowDateStr);
            aclModule.setAddUser(realName);
            insert = this.baseMapper.insert(aclModule);
        } else {
            before = this.baseMapper.selectById(dto.getId());
            Preconditions.checkNotNull(before,"待更新的权限系统不存在");
            aclModule.setId(dto.getId());
            aclModule.setOperator(realName);
            aclModule.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
            aclModule.setOperatorTime(nowDateStr);
            aclModule.setAddUser(before.getAddUser());
            aclModule.setAddDate(before.getAddDate());
            insert = this.baseMapper.updateById(aclModule);
        }
        if (insert == 0) {
            return JsonData.fail("操作失败");
        }
        sysLogService.saveAclModuleLog(before, aclModule);
        return JsonData.success("操作成功");
    }

    /**
     * 检查权限是否重复
     */
    private boolean checkExist(String systemCode,String aclModuleName,String id){
        return this.baseMapper.countByNameAndParentId(systemCode, aclModuleName, id) > 0;
    }

}
