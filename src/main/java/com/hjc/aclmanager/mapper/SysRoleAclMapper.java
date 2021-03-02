package com.hjc.aclmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.entity.SysRoleAcl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/16 11:43
 */
@Mapper
public interface SysRoleAclMapper extends BaseMapper<SysRoleAcl> {
    void batchInsert(@Param("roleAclList") List<SysRoleAcl> roleAclList);

    List<String> getAclIdListByRoleIdList(@Param("userRoleIdList") ArrayList<String> userRoleIdList);
}
