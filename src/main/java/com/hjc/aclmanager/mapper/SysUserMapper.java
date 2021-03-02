package com.hjc.aclmanager.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.entity.SysUser;
import com.hjc.aclmanager.vo.TransferVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/12 9:32
 */
@Mapper
@DS("db2")
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 通过工号查找用户
     * @param username
     * @return
     */
    SysUser findByJobNum(@Param("username") String username);

    List<TransferVO> getUser();

}
