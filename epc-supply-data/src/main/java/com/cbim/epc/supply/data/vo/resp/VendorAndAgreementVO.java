package com.cbim.epc.supply.data.vo.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 供应商以及集采协议价格相关信息
 *
 * @author xiaozp
 * @since 2023/7/7
 */
@Data
public class VendorAndAgreementVO {

    /**
     * 物料id
     */
    private Long materialId;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 集采协议id
     */
    private Long agreementId;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 集采协议价格起
     */
    private BigDecimal agreementPriceFrom;

    /**
     * 集采协议价格止
     */
    private BigDecimal agreementPriceTo;

    /**
     * 集采协议期起
     */
    private String agreementPeriodFrom;

    /**
     * 集采协议期止
     */
    private String agreementPeriodTo;

    /**
     * 最近更新时间
     */
    @JsonIgnore
    private LocalDateTime updateDate;

}
