package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.util.MultiResponse;
import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.ContractService;
import com.cbim.epc.supply.app.service.MaterialPriceContractService;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import com.cbim.epc.supply.data.vo.req.MaterialPriceQueryParam;
import com.cbim.epc.supply.data.vo.resp.ContractPriceAndOrderVO;
import com.cbim.epc.supply.data.vo.resp.ContractPriceDetailVO;
import com.cbim.epc.supply.data.vo.resp.MaterialPriceContractVO;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialPriceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 价格管理
 */
@RestController
@Slf4j
@RequestMapping("/api/contract/price")
@RequiredArgsConstructor
@Validated
public class ContractPriceController {

    private final MaterialPriceContractService priceContractService;

    private final MaterialService materialService;

    private final ContractService contractService;

    @PostMapping("/search")
    public PageResponse<ContractPriceAndOrderVO> searchContractPrice(@RequestBody @Validated MaterialPriceQueryParam queryParam) {
        return PageResponse.buildSuccess(priceContractService.getContractPricePageList(queryParam));
    }

    @GetMapping("/contract-list")
    public MultiResponse<MaterialPriceContractVO> getContractList(@RequestParam String id, @RequestParam String code) {
        List<MaterialPriceContractVO> priceOfContractList= contractService.getPriceOfContract();
        return MultiResponse.buildSuccess(priceOfContractList);
    }

    @GetMapping("/{materialId}")
    public SingleResponse<ContractPriceDetailVO> getPriceDetail(@PathVariable String materialId) {
        ContractPriceDetailVO priceContract = priceContractService.selectById(materialId);
        return SingleResponse.buildSuccess(priceContract);
    }

    @GetMapping("/material-list")
    public MultiResponse<SupplyMaterialPriceVO> getMaterialByCode(@RequestParam @NotBlank String objTypeCode,
                                                                  @RequestParam @NotNull Integer subDate,
                                                                  @RequestParam(required = false) String applicableCode) {
        List<SupplyMaterialPriceVO> supplyMaterialList = materialService.getMaterialByCode(objTypeCode, subDate, applicableCode);
        return MultiResponse.buildSuccess(supplyMaterialList);
    }

    @PostMapping("/saveOrUpdate")
    public SingleResponse<Long> saveOrUpdatePrice(@RequestBody @Valid List<MaterialContractPrice> contractPriceList) {
        return SingleResponse.buildSuccess(priceContractService.saveOrUpdatePrice(contractPriceList));
    }

    @DeleteMapping
    public SingleResponse<Object> deleteById(@RequestBody List<Long> materialIdList) {
        priceContractService.removeByMaterialIdList(materialIdList);
        return SingleResponse.buildSuccess("删除成功");
    }
}
