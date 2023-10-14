package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.MaterialPriceTemporaryService;
import com.cbim.epc.supply.data.convert.MaterialPriceTemporaryConvert;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporarySaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/openapi/temporary/price")
@RequiredArgsConstructor
public class MaterialPriceTemporaryOpenController {

    private final MaterialPriceTemporaryService materialPriceTemporaryService;

    @PostMapping("/save")
    public SingleResponse<?> save(@RequestBody List<MaterialPriceTemporarySaveVO> dataList) {
        List<MaterialPriceTemporary> domainList = MaterialPriceTemporaryConvert.INSTANCE.toDomainList(dataList);
        boolean success = materialPriceTemporaryService.saveBatch(domainList);
        if (success) {
            return SingleResponse.buildSuccess(null, "新增成功");
        } else {
            return SingleResponse.buildFailure("新增失败");
        }
    }
}
