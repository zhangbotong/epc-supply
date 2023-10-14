package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.AgreementPriceService;
import com.cbim.epc.supply.app.service.AgreementService;
import com.cbim.epc.supply.data.domain.AgreementPrice;
import com.cbim.epc.supply.data.vo.req.MaterialPriceQueryParam;
import com.cbim.epc.supply.data.vo.resp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 集采协议价
 * @author Kyrie
 * @date 2023/5/17 11:22
 */
@RestController
@Slf4j
@RequestMapping("/api/agreement/price")
@RequiredArgsConstructor
@Validated
public class AgreementPriceController {
    private final AgreementPriceService agreementPriceService;
    private final AgreementService agreementService;

    /**
     * 新增或更新
     */
    @PostMapping("/")
    public SingleResponse<Object> saveOrUpdatePrice(@Valid @RequestBody List<AgreementPrice> priceAgreementList) {
        return SingleResponse.buildSuccess(agreementPriceService.saveOrUpdatePrice(priceAgreementList));
    }

    /**
     * 根据物料 ids 批量删除价格
     * @param materialIdList 物料 id 列表
     */
    @PostMapping("/delete-by-material")
    public SingleResponse<Integer> deleteById(@RequestBody List<Long> materialIdList) {
        return SingleResponse.buildSuccess(agreementPriceService.removeByMaterialIdList(materialIdList));
    }

    /**
     * 台账/搜索
     */
    @PostMapping("/search")
    public PageResponse<AgreementPriceIndexVO> search(@Valid @RequestBody MaterialPriceQueryParam priceQueryParam) {
        return agreementPriceService.search(priceQueryParam);
    }

    /**
     * 根据物料类型获取物料和供应商
     * @param materialTypeCode 物料类型编码
     */
    @GetMapping("/material-type/material-vendor")
    public SingleResponse<MaterialAndVendorVO> getMaterialAndVendorByMaterialType(@RequestParam String materialTypeCode) {
        return SingleResponse.buildSuccess(agreementPriceService.getMaterialAndVendorByMaterialType(materialTypeCode));
    }

    /**
     * 根据物料类型和供应商获取集采协议
     * @param materialTypeId 物料类型 id
     * @param vendorId 供应商 id
     */
    @GetMapping("/material-type/{materialTypeId}/vendor/{vendorId}/agreement")
    public SingleResponse<List<AgreementVO>> getAgreementListBy(@PathVariable(value = "materialTypeId") Long materialTypeId,
                                                                @PathVariable(value = "vendorId") Long vendorId) {
        return SingleResponse.buildSuccess(agreementService.getValidAgreementListByMaterialTypeIdAndVendorId(materialTypeId, vendorId));
    }

    /**
     * 根据物料id获取集采协议价
     * 应用场景：点击编辑按钮，获取该物料下的所有集采协议价
     * @param materialId 物料 id
     */
    @GetMapping("/material/{materialId}")
    public SingleResponse<AgreementPriceIndexEditVO> getIndexByMaterialId(@PathVariable(value = "materialId") Long materialId) {
        return SingleResponse.buildSuccess(agreementPriceService.getIndexByMaterialId(materialId));
    }

    /**
     * 获取值集数据
     */
    @GetMapping("/value-set")
    public SingleResponse<PriceExtEnumVO> getValueSet() {
        return SingleResponse.buildSuccess(agreementPriceService.getPriceExtEnum());
    }
}
