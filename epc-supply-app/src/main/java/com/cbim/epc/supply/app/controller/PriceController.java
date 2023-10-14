package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.PriceService;
import com.cbim.epc.supply.data.vo.resp.MaterialCategoryPriceCountVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 价格公共Controller
 *
 * @author xiaozp
 * @since 2023/7/17
 */
@RestController
@Slf4j
@RequestMapping("/api/price")
@RequiredArgsConstructor
@Validated
public class PriceController {

    private final PriceService priceService;

    /**
     * 通过物料分类编码，查询三种不同类型（集采、历史合同、临时）价格下的物料计数
     *
     * @param materialCategoryCode 物料分类编码，例如：MS-JSCL.2.2.2
     * @return /
     */
    @GetMapping("/count/{materialCategoryCode}")
    public SingleResponse<MaterialCategoryPriceCountVO> getMaterialCategoryPriceCount(@PathVariable("materialCategoryCode") String materialCategoryCode) throws Exception {
        return SingleResponse.buildSuccess(priceService.getMaterialCategoryPriceCount(materialCategoryCode));
    }
}
