package com.hjc.aclmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.entity.SysRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/16 11:43
 */
@Mapper
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    List<String> getUserIdByRoleId(@Param("roleId") String roleId);

    void batchInsert(@Param("roleUserList") List<SysRoleUser> roleUserList);

    List<String> getRoleIdsByUserId(@Param("userId") String userId, @Param("systemCode") String systemCode);

}
