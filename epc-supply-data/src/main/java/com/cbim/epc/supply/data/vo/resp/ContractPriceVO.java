package com.cbim.epc.supply.data.vo.resp;


import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ContractPriceVO extends MaterialContractPrice {

    @JsonIgnore
    private String materialCode;

    @JsonIgnore
    private String materialName;

    @JsonIgnore
    private String objectTypeCode;

    @JsonIgnore
    private int publishStatus;

    private List<ContractOrderVO> orderList;

    private int contractStatus;

}
