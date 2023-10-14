package com.cbim.epc.supply.data.convert;

import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialAttributeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 123
 */
@Mapper
public interface SupplyMaterialAttributeConvert extends BaseConvert<SupplyMaterialAttributeVO, SupplyMaterialAttribute>{

    SupplyMaterialAttributeConvert INSTANCE = Mappers.getMapper(SupplyMaterialAttributeConvert.class);
    @Override
    SupplyMaterialAttributeVO toVo(SupplyMaterialAttribute entity);


    @Override
    List<SupplyMaterialAttributeVO> toVoList(List<SupplyMaterialAttribute> entities);
}
