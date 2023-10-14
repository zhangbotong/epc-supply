package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.data.domain.SupplyCity;

import java.util.List;

/**
 * 字典服务接口
 *
 * @author xiaozp
 * @since 2023/6/1
 */
public interface DictService {

    /**
     * 获取供货城市
     *
     * @return /
     */
    List<SupplyCity> getSupplyCity();
}
