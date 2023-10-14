package com.cbim.epc.supply.app.openapi;

import com.cbim.epc.base.util.MultiResponse;
import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.AgreementPriceService;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.data.vo.req.MaterialQueryParam;
import com.cbim.epc.supply.data.vo.req.ProductQueryMaterialParam;
import com.cbim.epc.supply.data.vo.resp.MaterialResult;
import com.cbim.epc.supply.data.vo.resp.ProductMaterialVo;
import com.cbim.epc.supply.data.vo.resp.VendorAndAgreementVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物料管理openapi
 */
@RestController
@Slf4j
@RequestMapping("/openapi")
@RequiredArgsConstructor
public class OutOpenApi {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private AgreementPriceService agreementPriceService;

    /**
     * 提供产品的物料列表接口
     */
    @RequestMapping("/material/getByObjType")
    public SingleResponse<List<ProductMaterialVo>> selectByObjectTypeCodeAndEntCode(@RequestBody ProductQueryMaterialParam param) {
        return SingleResponse.buildSuccess(materialService.selectByObjectTypeCodeAndEntCode(param));
    }

    /**
     * 根据物料id查询供应商以及集采协议相关数据
     *
     * @param materialId 物料id
     * @return /
     */
    @GetMapping("/vendorAndAgreementInfos/{materialId}")
    public MultiResponse<VendorAndAgreementVO> getVendorAndAgreementInfosByMaterialId(@PathVariable("materialId") Long materialId) {
        return MultiResponse.buildSuccess(agreementPriceService.getVendorAndAgreementInfosByMaterialId(materialId));
    }

    /**
     * 提供给清单查询物料的接口
     */
    @PostMapping("/material/list")
    public PageResponse<MaterialResult> queryMaterialList(@RequestBody MaterialQueryParam param) {
        return materialService.selectMaterialPage(param);
    }

}
