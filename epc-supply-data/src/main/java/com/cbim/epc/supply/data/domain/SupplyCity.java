package com.cbim.epc.supply.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 供货城市及费用相关信息
 *
 * @author xiaozp
 * @since 2023/5/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SupplyCity {
        private String code;
        private String label;
        private String value;
}
