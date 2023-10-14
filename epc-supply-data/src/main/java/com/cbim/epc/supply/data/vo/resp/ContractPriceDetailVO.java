package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Kyrie
 * @date 2023/5/8 14:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractPriceDetailVO {

    private SupplyMaterial material;

    private List<MaterialContractPrice> contractPriceList;
}
