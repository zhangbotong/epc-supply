package com.cbim.epc.supply.data.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物料分类下三种不同类型价格下计数
 *
 * @author xiaozp
 * @since 2023/7/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialCategoryPriceCountVO {

    /**
     * 集采协议价计数
     */
    private Integer agreementCount = 0;

    /**
     * 历史合同价计数
     */
    private Integer historyContractCount = 0;

    /**
     * 临时报价计数
     */
    private Integer temporaryCount = 0;
}
