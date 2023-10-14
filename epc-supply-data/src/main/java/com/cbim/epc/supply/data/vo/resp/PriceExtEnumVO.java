package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.sdk.easyapi.basedto.UnitDTO;
import com.cbim.epc.supply.data.domain.SupplyCity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 集采协议价，获取值集
 * @author Kyrie
 * @date 2023/5/22 17:51
 */
@AllArgsConstructor
@Getter
public class PriceExtEnumVO {
    /**
     * 计量单位/计价单位
     */
    List<UnitDTO> unitList;

    /**
     * 税率
     */
    List<UnitDTO> rateList;

    /**
     * 供货城市
     */
    List<SupplyCity> cityList;
}
