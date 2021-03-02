package com.hjc.aclmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.entity.SysAcl;
import com.hjc.aclmanager.vo.SysAclVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/14 14:36
 */
@Mapper
public interface SysAclMapper extends BaseMapper<SysAcl> {

    List<SysAclVO> searchAclByModuleId(@Param("aclModuleId") String aclModuleId);

    int countByNameAndAclModuleId(@Param("aclModuleId") String aclModuleId, @Param("name") String name, @Param("id") String id, @Param("parentId") String parentId);

    List<SysAcl> getAclsByRoleIds(@Param("roleIds") List<String> roleIds);
}
