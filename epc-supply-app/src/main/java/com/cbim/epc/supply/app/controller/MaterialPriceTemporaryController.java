package com.cbim.epc.supply.app.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.supply.app.service.MaterialPriceTemporaryService;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporaryJoin;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporaryQueryParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 临时报价
 */
@RestController
@Slf4j
@RequestMapping("/api/temporary/price")
@RequiredArgsConstructor
public class MaterialPriceTemporaryController {

    private final MaterialPriceTemporaryService materialPriceTemporaryService;

    /**
     * 台账/搜索
     */
    @PostMapping("/search")
    public PageResponse<MaterialPriceTemporaryJoin> search(@RequestBody MaterialPriceTemporaryQueryParam queryParam) {
        Page<MaterialPriceTemporaryJoin> page = materialPriceTemporaryService.search(queryParam);
        return PageResponse.buildSuccess(page);
    }
}
