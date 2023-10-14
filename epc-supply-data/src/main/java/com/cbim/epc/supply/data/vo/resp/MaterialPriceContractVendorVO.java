package com.cbim.epc.supply.data.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialPriceContractVendorVO {

    /**
     * id
     */
    private Long id;

    /**
     * 供应商名称
     */
    private String name;

    /**
     * 统一社会信用代码
     */
    private String uscc;

    /**
     * 供应商等级
     */
    private String supplierGrade;

}
