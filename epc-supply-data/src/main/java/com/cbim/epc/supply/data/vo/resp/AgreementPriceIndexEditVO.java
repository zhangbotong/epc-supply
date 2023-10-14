package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.SupplyMaterial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 集采协议价编辑页VO
 * @author Kyrie
 * @date 2023/5/17 15:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgreementPriceIndexEditVO {
    SupplyMaterial material;
    List<AgreementPriceIndexVO> agreementPriceIndexList;
}
