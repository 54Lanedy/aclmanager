package com.hjc.aclmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.entity.SysRole;
import com.hjc.aclmanager.vo.TreeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/15 14:48
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    int countByName(@Param("name") String name, @Param("id") String id, @Param("aclModuleId") String aclModuleId);

    List<String> getAclIdListByRoleId(@Param("roleId") String roleId);

    List<TreeVO> getAllAclList(@Param("aclModuleId") String aclModuleId);

}
