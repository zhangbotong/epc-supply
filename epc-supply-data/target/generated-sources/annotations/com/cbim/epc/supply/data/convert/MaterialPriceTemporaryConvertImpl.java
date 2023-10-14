package com.cbim.epc.supply.data.convert;

import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.domain.SupplyCity;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporarySaveVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-27T16:07:26+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
)
public class MaterialPriceTemporaryConvertImpl implements MaterialPriceTemporaryConvert {

    @Override
    public List<MaterialPriceTemporary> toDomainList(List<MaterialPriceTemporarySaveVO> list) {
        if ( list == null ) {
            return null;
        }

        List<MaterialPriceTemporary> list1 = new ArrayList<MaterialPriceTemporary>( list.size() );
        for ( MaterialPriceTemporarySaveVO materialPriceTemporarySaveVO : list ) {
            list1.add( materialPriceTemporarySaveVOToMaterialPriceTemporary( materialPriceTemporarySaveVO ) );
        }

        return list1;
    }

    protected MaterialPriceTemporary materialPriceTemporarySaveVOToMaterialPriceTemporary(MaterialPriceTemporarySaveVO materialPriceTemporarySaveVO) {
        if ( materialPriceTemporarySaveVO == null ) {
            return null;
        }

        MaterialPriceTemporary materialPriceTemporary = new MaterialPriceTemporary();

        materialPriceTemporary.setMaterialId( materialPriceTemporarySaveVO.getMaterialId() );
        materialPriceTemporary.setVendorId( materialPriceTemporarySaveVO.getVendorId() );
        materialPriceTemporary.setMeasureUnitName( materialPriceTemporarySaveVO.getMeasureUnitName() );
        materialPriceTemporary.setMeasureUnitCode( materialPriceTemporarySaveVO.getMeasureUnitCode() );
        materialPriceTemporary.setPriceUnitName( materialPriceTemporarySaveVO.getPriceUnitName() );
        materialPriceTemporary.setPriceUnitCode( materialPriceTemporarySaveVO.getPriceUnitCode() );
        materialPriceTemporary.setTempPrice( materialPriceTemporarySaveVO.getTempPrice() );
        materialPriceTemporary.setTaxRate( materialPriceTemporarySaveVO.getTaxRate() );
        materialPriceTemporary.setProjectName( materialPriceTemporarySaveVO.getProjectName() );
        List<SupplyCity> list = materialPriceTemporarySaveVO.getSupplyCity();
        if ( list != null ) {
            materialPriceTemporary.setSupplyCity( new ArrayList<SupplyCity>( list ) );
        }
        materialPriceTemporary.setShippingFee( materialPriceTemporarySaveVO.getShippingFee() );
        materialPriceTemporary.setInstallationFee( materialPriceTemporarySaveVO.getInstallationFee() );
        materialPriceTemporary.setNotes( materialPriceTemporarySaveVO.getNotes() );
        materialPriceTemporary.setSource( materialPriceTemporarySaveVO.getSource() );
        materialPriceTemporary.setApplicableScopeName( materialPriceTemporarySaveVO.getApplicableScopeName() );
        materialPriceTemporary.setApplicableScopeCode( materialPriceTemporarySaveVO.getApplicableScopeCode() );
        materialPriceTemporary.setCreateDate( materialPriceTemporarySaveVO.getCreateDate() );
        materialPriceTemporary.setUpdateDate( materialPriceTemporarySaveVO.getUpdateDate() );
        materialPriceTemporary.setCreateBy( materialPriceTemporarySaveVO.getCreateBy() );
        materialPriceTemporary.setCreateName( materialPriceTemporarySaveVO.getCreateName() );
        materialPriceTemporary.setUpdateBy( materialPriceTemporarySaveVO.getUpdateBy() );
        materialPriceTemporary.setUpdateName( materialPriceTemporarySaveVO.getUpdateName() );

        return materialPriceTemporary;
    }
}
