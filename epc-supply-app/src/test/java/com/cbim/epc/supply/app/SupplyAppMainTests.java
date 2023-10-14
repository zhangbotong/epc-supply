package com.cbim.epc.supply.app;

import com.cbim.epc.supply.app.service.AgreementPriceService;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.data.mapper.MaterialAgreementPriceMapper;
import com.cbim.epc.supply.data.mapper.SupplyMaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO 类描述
 *
 * @author xiaozp
 * @since 2023/5/16
 */
@SpringBootTest
public class SupplyAppMainTests {

    @Autowired
    private MaterialAgreementPriceMapper materialAgreementPriceMapper;

    @Autowired
    private SupplyMaterialMapper materialMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private AgreementPriceService agreementPriceService;


//    @Test
//    public void testAddAgreement() {
//        MaterialPriceAgreement agreement = new MaterialPriceAgreement();
//        agreement.setMaterialId(IdUtil.getSnowflakeNextId());
//        agreement.setVendorId(IdUtil.getSnowflakeNextId());
//        agreement.setAgreementId(IdUtil.getSnowflakeNextId());
//        agreement.setMeasureUnitCode("km");
//        agreement.setMeasureUnitName("km");
//        agreement.setPriceUnitCode("RMB");
//        agreement.setPriceUnitName("人民币");
//        agreement.setAgreementPriceFrom(new BigDecimal("5000"));
//        agreement.setAgreementPriceTo(new BigDecimal("10000"));
//        agreement.setTaxRate(new BigDecimal("0.06"));
//
//        List<SupplyCity> supplyCityInfos = new ArrayList<>();
//        SupplyCity supplyCityInfo1 = new SupplyCity();
//        List<SupplyCity.SupplyCity> supplyCities = new ArrayList<>();
//        SupplyCity.SupplyCity supplyCity1 = new SupplyCity.SupplyCity();
//        supplyCity1.setCode("01");
//        supplyCity1.setLabel("北京市");
//        supplyCity1.setValue("北京市");
//        SupplyCity.SupplyCity supplyCity2 = new SupplyCity.SupplyCity();
//        supplyCity2.setCode("02");
//        supplyCity2.setLabel("北京市");
//        supplyCity2.setValue("北京市");
//        supplyCities.add(supplyCity1);
//        supplyCities.add(supplyCity2);
//        supplyCityInfo1.setSupplyCities(supplyCities);
//        supplyCityInfo1.setShippingFee(new BigDecimal("1000"));
//        supplyCityInfo1.setInstallationFee(new BigDecimal("100"));
//        supplyCityInfo1.setOtherFee(BigDecimal.ZERO);
//        supplyCityInfos.add(supplyCityInfo1);
//
//        SupplyCity supplyCityInfo2 = new SupplyCity();
//        List<SupplyCity.SupplyCity> supply2Cities = new ArrayList<>();
//        SupplyCity.SupplyCity supplyCity3 = new SupplyCity.SupplyCity();
//        supplyCity3.setCode("03");
//        supplyCity3.setLabel("天津市");
//        supplyCity3.setValue("天津市");
//        SupplyCity.SupplyCity supplyCity4 = new SupplyCity.SupplyCity();
//        supplyCity4.setCode("04");
//        supplyCity4.setLabel("河北省");
//        supplyCity4.setValue("河北省");
//        supply2Cities.add(supplyCity3);
//        supply2Cities.add(supplyCity4);
//        supplyCityInfo2.setSupplyCities(supply2Cities);
//        supplyCityInfo2.setShippingFee(new BigDecimal("800"));
//        supplyCityInfo2.setInstallationFee(new BigDecimal("100"));
//        supplyCityInfo2.setOtherFee(new BigDecimal("88"));
//        supplyCityInfos.add(supplyCityInfo2);
//
//        agreement.setSupplyCity(supplyCityInfos);
//        agreement.setPublishStatus(PublishStatusEnum.PUBLISHED.getCode());
//        agreement.setHasBeenPublished(HasBeenPublishedEnum.PUBLISHED.getCode());
//        materialPriceAgreementMapper.insert(agreement);
//    }

//    @Test
    public void test01() {
//        MaterialPriceAgreement agreement = materialPriceAgreementService.getById(1659029713931575298L);
//        MaterialPriceAgreement agreement = materialPriceAgreementMapper.selectById(1659029713931575298L);
//        System.out.println(JSON.toJSONString(agreement));
//        SupplyMaterial material = materialMapper.selectById(agreement.getMaterialId());
//        SupplyMaterial material = materialService.getById(1658394618724732929L);
//        System.out.println(JSON.toJSONString(material));
//        AgreementPriceIndex agreementPriceIndexVO = agreementPriceService.selectById(1659029713931575298L);
//        System.out.println("结果：\n" + JSON.toJSONString(agreementPriceIndexVO));
    }
}
