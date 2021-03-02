package com.hjc.aclmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hjc.aclmanager.dto.PageParDTO;
import com.hjc.aclmanager.dto.SearchLogDTO;
import com.hjc.aclmanager.entity.SysLog;
import com.hjc.aclmanager.vo.SysLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyue
 * Time 2021/1/25 10:43
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
    int countBySearchDto(@Param("dto") SearchLogDTO dto);

    List<SysLogVO> getPageListBySearchDto(@Param("dto") SearchLogDTO dto, @Param("page") PageParDTO page);
}
