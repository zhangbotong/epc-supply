package com.cbim.epc.supply.app;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cbim.epc.supply.app.service.MaterialAttributeService;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.common.enums.ValueInputTypeEnum;
import com.cbim.epc.supply.data.domain.NewAttributeValue;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * TODO 类描述
 *
 * @author xiaozp
 * @since 2023/5/16
 */
//@SpringBootTest
public class MaterialTest {

    @Autowired
    private MaterialAttributeService materialAttributeService;

    @Autowired
    private MaterialService materialService;


    /**
     * 刷新 已发布得属性和值拼接字段
     */
   // @Test
    public void flushData() {
        List<SupplyMaterial> list = materialService.getBaseMapper().selectByMap(MapUtil.builder("publish_status", (Object) 1).build());
        list.stream().forEach(v -> {
            List<SupplyMaterialAttribute> attributeList = materialAttributeService.getBaseMapper().selectByMap(MapUtil.builder("material_id", (Object) v.getId()).build());
            String valueSplice = handleAttrValueSplice(attributeList);
            UpdateWrapper<SupplyMaterial> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", v.getId());
            SupplyMaterial material = new SupplyMaterial();
            material.setAttributeValues(valueSplice);
            materialService.getBaseMapper().update(material, wrapper);
        });

    }


    private String handleAttrValueSplice(List<SupplyMaterialAttribute> attributeList) {
        final String[] attrAndValue = {""};
        attributeList.stream().forEach(v -> {
            if (ValueInputTypeEnum.INPUT_TYPE.getCode().equals(v.getValueInputType())) {
                NewAttributeValue attributeValue = v.getAttributeValue();
                if (attributeValue != null && ObjectUtil.isNotEmpty(attributeValue.getInputValue())) {
                    attrAndValue[0] = attrAndValue[0] + "," + v.getAttributeName() + ":" + attributeValue.getInputValue();
                }
            } else if (ValueInputTypeEnum.ENUM_TYPE.getCode().equals(v.getValueInputType())) {
                NewAttributeValue attributeValue = v.getAttributeValue();
                if (attributeValue != null && v.getChecks() != null && v.getChecks().size() > 0) {
                    Map<String, NewAttributeValue.EnumValue> enumValueMap = attributeValue.getEnumValues();
                    final String[] value = {""};
                    v.getChecks().stream().forEach(a -> {
                        NewAttributeValue.EnumValue enumValue = enumValueMap.get(a);
                        if (enumValue != null) {
                            value[0] = value[0] + "|" + enumValue.getValue();
                        }
                    });
                    if (ObjectUtil.isNotEmpty(value[0])) {
                        attrAndValue[0] = attrAndValue[0] + "," + v.getAttributeName() + ":" + value[0].substring(1);
                    }
                }
            } else if (ValueInputTypeEnum.RANGE_TYPE.getCode().equals(v.getValueInputType())) {
                NewAttributeValue attributeValue = v.getAttributeValue();
                if (attributeValue != null && v.getChecks() != null && v.getChecks().size() > 0) {
                    Map<String, NewAttributeValue.RangeValue> enumValueMap = attributeValue.getRangeValue();
                    final String[] value = {""};
                    v.getChecks().stream().forEach(a -> {
                        NewAttributeValue.RangeValue enumValue = enumValueMap.get(a);
                        if (enumValue != null) {
                            value[0] = value[0] + "|" + enumValue.getMin() + "~" + enumValue.getMax();
                        }
                    });
                    if (ObjectUtil.isNotEmpty(value[0])) {
                        attrAndValue[0] = attrAndValue[0] + "," + v.getAttributeName() + ":" + value[0].substring(1);
                    }
                }
            }
        });
        return attrAndValue[0].substring(1);
    }
}
