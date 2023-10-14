package com.cbim.epc.supply.data.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractPriceAndOrderVO {

    private String materialCode;

    private String materialName;

    private String objectTypeCode;

    private Long materialId;

    private int materialPublishStatus;

    private List<ContractPriceVO> contractPriceList;

}
