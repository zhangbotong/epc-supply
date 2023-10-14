package com.cbim.epc.supply.data.vo.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaterialPriceContractVO {

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String contractName;

    /**
     * 编码
     */
    private String contractCode;

    @JsonFormat(locale = "zh",pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private LocalDateTime createdDateTime;

    private String projectName;

    private String buildSite;

    private String applicableScopeCode;

    private String applicableScopeName;

    private String totalPrice;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 统一社会信用代码
     */
    private String uscc;

}
