package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporaryJoin;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporaryQueryParam;
import com.cbim.epc.supply.data.vo.resp.TemporaryPriceAvgVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialPriceTemporaryMapper extends BaseMapperX<MaterialPriceTemporary> {

    List<MaterialPriceTemporaryJoin> queryByPage(@Param("param") MaterialPriceTemporaryQueryParam queryParam);

    Integer queryCount(@Param("param") MaterialPriceTemporaryQueryParam queryParam);

    TemporaryPriceAvgVO queryAvgPrice(@Param("materialId") long materialId);
}
