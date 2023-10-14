package com.cbim.epc.supply.common.base.controller;

import cn.hutool.core.util.ObjectUtil;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.common.config.annoation.NoLog;
import com.cbim.epc.supply.common.domain.CommonLog;
import com.cbim.epc.supply.common.mapper.CommonLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/base/log")
@Slf4j

public class BaseLogController {
    private final CommonLogMapper logMapper;

    @NoLog
    @GetMapping("/get/{id}")
    public ModelAndView getCommonLog(@PathVariable String id) {
        HashMap<String, Object> map = new HashMap<>(2);
        List<String> list = new ArrayList<>();

        CommonLog commonLog = logMapper.selectById(id);
        if (ObjectUtil.isEmpty(commonLog)){
            return new ModelAndView("log",map);
        }
        this.addDetailsHtml(list, commonLog);
        map.put("list",list);
        map.put("logResult",commonLog.getRequestResult());
        return new ModelAndView("log",map);
    }

    private void addDetailsHtml(List<String> list, CommonLog commonLog) {
        String[] infos = commonLog.getResultDetails().split("\n");
        for (String info : infos) {
            if (info.startsWith("com.cbim")){
                list.add("\tat "+info.replaceAll("\\(","<span style=\"color: blue\">(")
                        .replaceAll("\\)",")</span>"));
            }else {
                list.add("\tat "+info.replaceAll("\\(","<span style=\"color: grey\">(")
                        .replaceAll("\\)",")</span>"));
            }
        }
    }

    @PostMapping("/error")
    @ResponseBody
    public SingleResponse fail() {
        return SingleResponse.buildFailure("登录失败");
    }

    @GetMapping("/test")
    public void testLogWarn(){
        int i = 10/0;
    }

}
