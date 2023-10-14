package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.domain.Vendor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 集采协议价格新增时，根据【物料分类】->【物料】;【物料分类】->【集采协议】->【供应商】
 * @author Kyrie
 * @date 2023/5/19 13:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialAndVendorVO {
    List<SupplyMaterial> materialList;
    List<Vendor> vendorList;
}
