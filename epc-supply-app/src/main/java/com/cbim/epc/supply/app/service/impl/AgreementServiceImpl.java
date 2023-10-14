package com.cbim.epc.supply.app.service.impl;

import com.cbim.epc.supply.app.service.AgreementService;
import com.cbim.epc.supply.app.service.ExtService;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.domain.Agreement;
import com.cbim.epc.supply.data.mapper.AgreementMapper;
import com.cbim.epc.supply.data.vo.resp.AgreementVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author kyrie
* @description 针对表【view_agreement_state_release(供应资源管理-集采协议主表)】的数据库操作Service实现
* @createDate 2023-05-19 14:10:40
*/
@Service
@RequiredArgsConstructor
public class AgreementServiceImpl extends BaseServiceImpl<AgreementMapper, Agreement>
    implements AgreementService {

    private final AgreementMapper agreementMapper;
    private final ExtService extService;

    @Override
    public List<Long> getValidAgreementVendorIdListByMaterialTypeCode(String materialTypeCode) {
        long materialTypeId = extService.getMaterialTypeIdByCode(materialTypeCode);
        List<Agreement> agreementList = this.getValidByMaterialTypeId(materialTypeId);
        List<Long> vendorIdList = agreementList.stream().map(Agreement::getVendorId).collect(Collectors.toList());
        return vendorIdList == null ? new ArrayList<>() : vendorIdList;
    }

    public List<AgreementVO> getValidAgreementListByMaterialTypeIdAndVendorId(Long materialTypeId, Long vendorId){
        List<Agreement> agreementList = this.getValidByMaterialTypeId(materialTypeId);
        agreementList = agreementList.stream().filter(agreement -> agreement.getVendorId().equals(vendorId)).collect(Collectors.toList());
        return this.convertToAgreementVOList(agreementList);
    }

    /**
     * 根据物料类型id联表查询有效集采协议（state = 'release'(calStatus = 1)）
     */
    private List<Agreement> getValidByMaterialTypeId(Long materialTypeId) {
        List<Agreement> agreementList = agreementMapper.selectValidByMaterialTypeId(materialTypeId);
        return agreementList == null ? new ArrayList<>() : agreementList;
    }

    private List<AgreementVO> convertToAgreementVOList(List<Agreement> agreementList) {
        List<AgreementVO> agreementVOList = new ArrayList<>();
        Map<String, String> entMap = extService.getEntMap();
        for (Agreement agreement : agreementList) {
            AgreementVO agreementVO = this.convertToAgreementVO(agreement, entMap);
            agreementVOList.add(agreementVO);
        }
        return agreementVOList;
    }

    private AgreementVO convertToAgreementVO(Agreement agreement, Map<String, String> entMap) {
        AgreementVO agreementVO = new AgreementVO();
        BeanUtils.copyProperties(agreement, agreementVO);
        String entCode = agreementVO.getApplicableScopeCode();
        String entName = entMap.getOrDefault(entCode, "");
        agreementVO.setApplicableScopeName(entName);
        return agreementVO;
    }
}




