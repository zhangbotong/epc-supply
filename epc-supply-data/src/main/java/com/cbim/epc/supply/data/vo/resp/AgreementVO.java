package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.Agreement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Kyrie
 * @date 2023/5/19 15:48
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AgreementVO extends Agreement {
    /**
     * 适用范围名称
     */
    @Setter
    private String applicableScopeName;
}
