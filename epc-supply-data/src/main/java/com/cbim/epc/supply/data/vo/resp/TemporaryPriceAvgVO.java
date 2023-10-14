package com.cbim.epc.supply.data.vo.resp;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 平均临时报价VO
 *
 * @author xiaozp
 * @since 2023/5/30
 */
@Data
@ToString
public class TemporaryPriceAvgVO {

    /**
     * 物料id
     */
    private Long materialId;

    /**
     * 当前物料的临时报价总和
     */
    private BigDecimal totalTempPrice;

    /**
     * 当前物料的临时报价次数
     */
    private Integer count;

    /**
     * 当前物料的临时报价的平均价 = totalTempPrice / count
     */
    private BigDecimal avgTempPrice;
}
