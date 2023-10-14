package com.cbim.epc.supply.app;

import com.cbim.epc.supply.app.service.AgreementPriceService;
import com.cbim.epc.supply.data.vo.resp.AgreementPriceIndexEditVO;
import com.cbim.epc.supply.data.vo.resp.MaterialAndVendorVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO 类描述
 *
 * @author xiaozp
 * @since 2023/5/16
 */
@SpringBootTest
public class AgreementPriceTest {
    @Autowired
    private AgreementPriceService agreementPriceService;

    @Test
    public void test01() {
        String materialTypeCode = "MS-20230512.2";
        long materialTypeId = 1656948233856053248L;
        MaterialAndVendorVO materialAndVendorByMaterialType = agreementPriceService.getMaterialAndVendorByMaterialType(materialTypeCode);
        System.out.println(materialAndVendorByMaterialType);

//        long vendorId = 1L;
//        List<AgreementVO> agreementList = agreementPriceService.getAgreementByMaterialTypeIdAndVendorId(materialTypeId, vendorId);
//        System.out.println(agreementList);

        long materialId = 1658394618724732929L;
        AgreementPriceIndexEditVO indexByMaterialId = agreementPriceService.getIndexByMaterialId(materialId);
        System.out.println(indexByMaterialId);
    }
}
