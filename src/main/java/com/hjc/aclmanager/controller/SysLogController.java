package com.hjc.aclmanager.controller;

import com.hjc.aclmanager.dto.PageParDTO;
import com.hjc.aclmanager.dto.SearchLogDTO;
import com.hjc.aclmanager.service.SysLogService;
import com.hjc.aclmanager.utils.JsonData;
import com.hjc.aclmanager.vo.PageJsonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liyue
 * Time 2021/1/25 15:25
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/log.page")
    public ModelAndView main(){
        return new ModelAndView("sys/log");
    }

    @RequestMapping("/search.json")
    public PageJsonVO search(PageParDTO page, SearchLogDTO dto){
        return sysLogService.searchPageList(page, dto);
    }

    @RequestMapping("/recover.json")
    public JsonData recover(String id) {
        return sysLogService.recover(id);
    }
}
