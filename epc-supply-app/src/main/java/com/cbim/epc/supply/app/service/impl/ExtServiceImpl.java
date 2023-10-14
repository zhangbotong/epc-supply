package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.EntDTO;
import com.cbim.epc.supply.app.service.ExtService;
import com.cbim.epc.supply.common.base.exception.EpcSupplyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kyrie
 * @date 2023/5/25 14:33
 * 调用一组Service
 */
@Service
@RequiredArgsConstructor
public class ExtServiceImpl implements ExtService {

    private final EasyApiUtil easyApiUtil;

    @Override
    public List<EntDTO> getEntList() {
        List<EntDTO> ents;
        try {
            ents = easyApiUtil.getEpcEnts();
        } catch (Exception e){
            throw new EpcSupplyException(String.format("Call external api error.method = easyApiUtil.getEpcEnts(), detail: %s", e));
        }
        return ents == null ? new ArrayList<>() : ents;
    }

    @Override
    public Map<String, String> getEntMap() {
        List<EntDTO> entList = getEntList();
        return entList.stream().collect(Collectors.toMap(EntDTO::getEntCode, EntDTO::getEntName));
    }


    @Override
    public long getMaterialTypeIdByCode(String materialTypeCode) {
        long materialTypeId = 0;
        String materilalTypeIdStr;
        try {
            materilalTypeIdStr = easyApiUtil.getObjectTypeId(materialTypeCode);
        } catch (Exception e){
            throw new EpcSupplyException(String.format("Call external api error.method = easyApiUtil.getObjectTypeId(), detail: %s", e));
        }
        if (StrUtil.isEmpty(materilalTypeIdStr)){
            return materialTypeId;
        }
        try {
            materialTypeId = Long.parseLong(materilalTypeIdStr);
        } catch (Exception e){
            throw new EpcSupplyException(String.format("Convert materialTypeId from string to long error, materialTypeIdStr = %s, detail: %s", materilalTypeIdStr, e));
        }
        return materialTypeId;
    }
}
