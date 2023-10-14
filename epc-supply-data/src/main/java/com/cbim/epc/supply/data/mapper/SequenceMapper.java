package com.cbim.epc.supply.data.mapper;

import com.cbim.epc.supply.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 生成流水号
 */
@Repository
public interface SequenceMapper extends BaseMapperX {

    Integer initial(@Param("businessType") String businessType,@Param("ruleType") String ruleType,@Param("rule") String rule);

}
