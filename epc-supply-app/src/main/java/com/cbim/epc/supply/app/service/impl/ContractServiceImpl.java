package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cbim.epc.supply.app.service.ContractService;
import com.cbim.epc.supply.common.enums.ValidEnum;
import com.cbim.epc.supply.data.domain.Contract;
import com.cbim.epc.supply.data.domain.Contractor;
import com.cbim.epc.supply.data.enums.ContractDataTypeEnum;
import com.cbim.epc.supply.data.mapper.ContractMapper;
import com.cbim.epc.supply.data.vo.resp.MaterialPriceContractVO;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同-服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractMapper contractMapper;

    @Override
    public List<MaterialPriceContractVO> getPriceOfContract() {
        List<MaterialPriceContractVO> contractVOList = new ArrayList<>();
        List<Contract> contractList = contractMapper.selectList(Wrappers.lambdaQuery(Contract.class)
                .eq(Contract::getContractClassification, "采购合同")
                .eq(Contract::getDataType, ContractDataTypeEnum.IMPORT.getCode())
                .eq(Contract::getIsValid, ValidEnum.VALID.getCode()));
        contractList.forEach(c->{
            List<Contractor> contractor = c.getContractor();
            List<String> codeList = contractor.stream()
                    .map(o->{
                        o.setInstitutionCode(StrUtil.isBlank(o.getInstitutionCode())?"":o.getInstitutionCode());
                        return o.getInstitutionCode();
                    }).collect(Collectors.toList());
            List<String> nameList = contractor.stream()
                    .map(o->{
                        o.setInstitutionName(StrUtil.isBlank(o.getInstitutionName())?"未知供应商":o.getInstitutionName());
                        return o.getInstitutionName();
                    }).collect(Collectors.toList());
            Joiner joiner = Joiner.on(",");
            String codeStr = joiner.join(codeList);
            String nameStr = joiner.join(nameList);
            MaterialPriceContractVO contractVO = MaterialPriceContractVO.builder()
                    .id(c.getId())
                    .contractName(c.getContractName())
                    .contractCode(c.getContractCode())
                    .createdDateTime(c.getSignDate().atStartOfDay())
                    .projectName(c.getProjectName())
                    .buildSite(c.getConstructionAddress())
                    .applicableScopeCode(c.getApplicableScopeCode())
                    .applicableScopeName(c.getApplicableScopeName())
                    .totalPrice(c.getSignAmount().toString())
                    .vendorName(nameStr)
                    .uscc(codeStr)
                    .build();
            contractVOList.add(contractVO);
        });

        return contractVOList;
    }
}
