package com.cbim.epc.supply.app.service;

import com.cbim.epc.sdk.easyapi.basedto.EntDTO;

import java.util.List;
import java.util.Map;

/**
 * @author Kyrie
 * @date 2023/5/25 14:32
 * 调用一组Service
 */
public interface ExtService {
    List<EntDTO> getEntList();
    Map<String, String> getEntMap();

    long getMaterialTypeIdByCode(String materialTypeCode);
}
