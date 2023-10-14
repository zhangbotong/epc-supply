package com.cbim.epc.supply.data.convert;

import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author 123
 */
@Mapper
public interface SupplyMaterialConvert extends BaseConvert<SupplyMaterialVO, SupplyMaterial>{

    SupplyMaterialConvert INSTANCE = Mappers.getMapper(SupplyMaterialConvert.class);
    @Override
    SupplyMaterialVO toVo(SupplyMaterial entity);

}
