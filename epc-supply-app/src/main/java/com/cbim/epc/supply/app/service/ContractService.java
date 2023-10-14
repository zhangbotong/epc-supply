package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.data.vo.resp.MaterialPriceContractVO;

import java.util.List;

/**
 * 合同-服务接口
 *
 * @author xiaozp
 * @since 2023/6/7
 */
public interface ContractService {

    List<MaterialPriceContractVO> getPriceOfContract();
}
