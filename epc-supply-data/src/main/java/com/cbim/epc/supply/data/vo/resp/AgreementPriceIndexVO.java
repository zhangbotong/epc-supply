package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.AgreementPriceIndex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Kyrie
 * @date 2023/5/25 16:32
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementPriceIndexVO extends AgreementPriceIndex {
    /**
     * 适用范围名称
     */
    @Setter
    private String applicableScopeName;
}
