package com.hjc.aclmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.entity.SysAclModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liyue
 * Time 2021/1/13 16:20
 */
@Mapper
public interface SysAclModuleMapper extends BaseMapper<SysAclModule> {

    int countByNameAndParentId(@Param("systemCode") String systemCode, @Param("name") String name, @Param("id") String id);
}
