package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SupplyMaterialPriceVO extends SupplyMaterial {

    private List<SupplyMaterialAttribute> attributeList;
}
