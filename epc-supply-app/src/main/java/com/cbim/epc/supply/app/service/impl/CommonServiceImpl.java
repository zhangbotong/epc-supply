package com.cbim.epc.supply.app.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.metadto.InputTypeEnum;
import com.cbim.epc.sdk.easyapi.metadto.ObjectDetailDTO;
import com.cbim.epc.supply.app.service.CommonService;
import com.cbim.epc.supply.data.domain.*;
import com.cbim.epc.supply.data.enums.PublishTypeEnum;
import com.cbim.epc.supply.data.enums.SelectTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private EasyApiUtil apiUtil;

    @Override
    public ObjectAttr getObjectAttr(String objectTypeCode, String entCode) {
        ObjectAttr objectAttr = new ObjectAttr();
        List<MetaGroupInfo> groupList = new ArrayList<>();
        ObjectDetailDTO objDetail = apiUtil.getObjectDetailByTypeCode(Objects.toString(objectTypeCode), entCode);
        if(objDetail == null || objDetail.getGroups() == null ||  objDetail.getGroups().size() == 0 ){
             objDetail = apiUtil.getObjectDetailByTypeCode(Objects.toString(objectTypeCode), "00");
        }
        if (objDetail != null && PublishTypeEnum.RELEASE.getCode().equals(objDetail.getType())) {
            objDetail.getGroups().stream().forEach(v -> {
                MetaGroupInfo groupInfo = new MetaGroupInfo();
                groupInfo.setGroupCode(Long.valueOf(v.getId()));
                groupInfo.setGroupName(v.getName());
                List<SupplyMaterialAttribute> resultList = new ArrayList<>();
                v.getAttrList().stream().forEach(f -> {
                    SupplyMaterialAttribute bean = new SupplyMaterialAttribute();
                    bean.setGroupCode(Long.valueOf(v.getId()));
                    bean.setGroupName(v.getName());
                    bean.setAttributeCode(Long.valueOf(f.getId()));
                    bean.setAttributeName(f.getName());
                    bean.setValueType(f.getType().getCode());
                    bean.setValueInputType(f.getInputMethod().getCode());
                    bean.setNumericType(f.getValueType().getCode());
                    bean.setUnitCode(f.getUnit());//计量单位
                    bean.setUnitName(f.getUnit());//计量单位
                    bean.setIsRequired(Boolean.TRUE.compareTo(f.getRequired()) == 0 ? StringPool.YES : StringPool.NO);
                    bean.setSort(f.getNameSort());
                    bean.setSelectType(Boolean.TRUE.compareTo(f.getChecked()) == 0 ? SelectTypeEnum.CHECK.getCode() : SelectTypeEnum.RADIO.getCode());
                    bean.setDecimalPlaces(Integer.valueOf(f.getDecimalPlaces()));
                    NewAttributeValue attributeValue = new NewAttributeValue();
                    Map<String, NewAttributeValue.EnumValue> enumValues = new HashMap<>();
                    Map<String, NewAttributeValue.RangeValue> rangeValue = new HashMap<>();
                    f.getEnumValues().stream().forEach(e -> {
                        if (InputTypeEnum.enum_type.equals(f.getInputMethod())) {
                            NewAttributeValue.EnumValue value = new NewAttributeValue.EnumValue();
                            value.setValue(e.getName());
                            enumValues.put(e.getId(), value);
                        } else if (InputTypeEnum.range_type.equals(f.getInputMethod())) {
                            NewAttributeValue.RangeValue value = new NewAttributeValue.RangeValue();
                            value.setMax(e.getAfterName());
                            value.setMin(e.getBeforeName());
                            rangeValue.put(e.getId(), value);
                        }
                    });
                    attributeValue.setInputValue(f.getDefaultValue());
                    attributeValue.setUsedValue(f.getDefaultValue());
                    attributeValue.setEnumValues(enumValues);
                    attributeValue.setRangeValue(rangeValue);
                    bean.setAttributeValue(attributeValue);
                    resultList.add(bean);
                });
                groupInfo.setAttrList(resultList);
                groupList.add(groupInfo);
            });
            objectAttr.setObjVersion(objDetail.getVersion());
            objectAttr.setGroupList(groupList);
            objectAttr.setObjectId(objDetail.getId());
        }

        return objectAttr;
    }
}
