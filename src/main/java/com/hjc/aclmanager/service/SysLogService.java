package com.hjc.aclmanager.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Preconditions;
import com.hjc.aclmanager.config.RequestHolder;
import com.hjc.aclmanager.dto.PageParDTO;
import com.hjc.aclmanager.dto.SearchLogDTO;
import com.hjc.aclmanager.entity.*;
import com.hjc.aclmanager.exception.ParamException;
import com.hjc.aclmanager.mapper.*;
import com.hjc.aclmanager.utils.*;
import com.hjc.aclmanager.vo.PageJsonVO;
import com.hjc.aclmanager.vo.SysLogVO;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 操作日志服务层
 * Created by liyue
 * Time 2021/1/25 10:37
 */
@Service
public class SysLogService extends ServiceImpl<SysLogMapper, SysLog> {
    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;
    @Autowired
    private SysAclMapper sysAclMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleAclService sysRoleAclService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Transactional
    public JsonData recover(String id) {
        SysLog sysLog = this.baseMapper.selectById(id);
        Preconditions.checkNotNull(sysLog, "待还原的记录不存在");
        switch (sysLog.getType()) {
            case LogType.TYPE_ACL_MODULE:
                SysAclModule beforeAclModule = sysAclModuleMapper.selectById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAclModule, "待还原的权限模块已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAclModule afterAclModule = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAclModule>() {
                });
                afterAclModule.setOperator(RequestHolder.getCurrentUser().getRealName());
                afterAclModule.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAclModule.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
                sysAclModuleMapper.updateById(afterAclModule);
                saveAclModuleLog(beforeAclModule, afterAclModule);
                break;
            case LogType.TYPE_ACL:
                SysAcl beforeAcl = sysAclMapper.selectById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeAcl, "待还原的权限点已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysAcl afterAcl = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysAcl>() {
                });
                afterAcl.setOperator(RequestHolder.getCurrentUser().getRealName());
                afterAcl.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterAcl.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
                sysAclMapper.updateById(afterAcl);
                saveAclLog(beforeAcl, afterAcl);
                break;
            case LogType.TYPE_ROLE:
                SysRole beforeRole = sysRoleMapper.selectById(sysLog.getTargetId());
                Preconditions.checkNotNull(beforeRole, "待还原的角色已经不存在了");
                if (StringUtils.isBlank(sysLog.getNewValue()) || StringUtils.isBlank(sysLog.getOldValue())) {
                    throw new ParamException("新增和删除操作不做还原");
                }
                SysRole afterRole = JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<SysRole>() {
                });
                afterRole.setOperator(RequestHolder.getCurrentUser().getRealName());
                afterRole.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
                afterRole.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
                sysRoleMapper.updateById(afterRole);
                saveRoleLog(beforeRole, afterRole);
                break;
            case LogType.TYPE_ROLE_ACL:
                SysRole aclRole = sysRoleMapper.selectById(sysLog.getTargetId());
                Preconditions.checkNotNull(aclRole, "角色已经不存在了");
                sysRoleAclService.saveRoleAndAcl(
                        JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<String[]>() {})
                        ,sysLog.getTargetId());
                break;
            case LogType.TYPE_ROLE_USER:
                SysRole userRole = sysRoleMapper.selectById(sysLog.getTargetId());
                Preconditions.checkNotNull(userRole, "角色已经不存在了");
                sysRoleUserService.saveRoleAndUser(
                        JsonMapper.string2Obj(sysLog.getOldValue(), new TypeReference<String[]>() {})
                        , sysLog.getTargetId());
                break;
            default:
                ;
        }
        return JsonData.success("操作成功");
    }

    public PageJsonVO searchPageList(PageParDTO page, SearchLogDTO dto) {
        BeanValidator.check(dto);
        if (StringUtils.isNotBlank(dto.getBeforeSeg())) {
            dto.setBeforeSeg("%" + dto.getBeforeSeg() + "%");
        }
        if (StringUtils.isNotBlank(dto.getAfterSeg())) {
            dto.setAfterSeg("%" + dto.getAfterSeg() + "%");
        }
        if (StringUtils.isNotBlank(dto.getOperator())) {
            dto.setOperator("%" + dto.getOperator() + "%");
        }

        int count = this.baseMapper.countBySearchDto(dto);
        if (count > 0) {
            List<SysLogVO> logList = this.baseMapper.getPageListBySearchDto(dto, page);
            return PageJsonVO.<SysLogVO>builder().total(count).rows(logList).build();
        }
        return PageJsonVO.<SysLogVO>builder().build();
    }

    public void saveAclModuleLog(SysAclModule before, SysAclModule after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ACL_MODULE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getRealName());
        sysLog.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
        sysLog.setStatus(1);
        this.baseMapper.insert(sysLog);
    }

    public void saveAclLog(SysAcl before, SysAcl after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ACL);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getRealName());
        sysLog.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
        sysLog.setStatus(1);
        this.baseMapper.insert(sysLog);
    }

    public void saveRoleLog(SysRole before, SysRole after) {
        SysLog sysLog = new SysLog();
        sysLog.setType(LogType.TYPE_ROLE);
        sysLog.setTargetId(after == null ? before.getId() : after.getId());
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getRealName());
        sysLog.setOperatorIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperatorTime(MyUtil.DATE_F2.format(new Date()));
        sysLog.setStatus(1);
        this.baseMapper.insert(sysLog);
    }
}
