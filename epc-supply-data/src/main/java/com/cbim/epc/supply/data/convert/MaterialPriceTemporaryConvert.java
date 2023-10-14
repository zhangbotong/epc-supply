package com.cbim.epc.supply.data.convert;

import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporarySaveVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 临时报价 Convert
 *
 * @author xiaozp
 * @since 2023/5/30
 */
@Mapper
public interface MaterialPriceTemporaryConvert {

    MaterialPriceTemporaryConvert INSTANCE = Mappers.getMapper(MaterialPriceTemporaryConvert.class);

    List<MaterialPriceTemporary> toDomainList(List<MaterialPriceTemporarySaveVO> list);
}
