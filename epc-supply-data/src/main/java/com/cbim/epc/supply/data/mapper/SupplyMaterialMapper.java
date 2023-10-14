package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.vo.req.MaterialQueryParam;
import com.cbim.epc.supply.data.vo.resp.ProductMaterialVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 供应资源管理-物料管理主表 Mapper 接口
 * </p>
 *
 * @author xiaozp
 * @since 2023-04-04
 */
@Repository
public interface SupplyMaterialMapper extends BaseMapperX<SupplyMaterial> {

    Integer queryCount(MaterialQueryParam queryParam);

    List<SupplyMaterial> queryMaterialList(MaterialQueryParam queryParam);

    List<ProductMaterialVo> selectByObjectTypeCodeAndEntCode(@Param("objectCodes") List<String> objectCodes, @Param("entCode") String entCode);
}
