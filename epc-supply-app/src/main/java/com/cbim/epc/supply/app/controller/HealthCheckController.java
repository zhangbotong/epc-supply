package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.util.SingleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping("/check")
    public SingleResponse<String> check(){
        return SingleResponse.buildSuccess("check success");
    }
}
