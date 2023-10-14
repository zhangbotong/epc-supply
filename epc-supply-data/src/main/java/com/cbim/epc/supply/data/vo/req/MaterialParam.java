package com.cbim.epc.supply.data.vo.req;

import com.cbim.epc.supply.data.domain.GroupInfo;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MaterialParam extends SupplyMaterial {

    /**
     * 物料属性
     */
    List<GroupInfo> groupList;

    public List<SupplyMaterialAttribute> getAttrList() {
        List<SupplyMaterialAttribute> attributeList = new ArrayList<>();
        this.getGroupList().stream().forEach(v->{
            attributeList.addAll(v.getAttrList());
        });
        return attributeList;
    }
}
