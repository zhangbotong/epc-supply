package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.data.domain.Agreement;
import com.cbim.epc.supply.common.base.service.BaseService;
import com.cbim.epc.supply.data.vo.resp.AgreementVO;

import java.util.List;

/**
* @author kyrie
* @description 针对表【view_agreement_state_release(供应资源管理-集采协议主表)】的数据库操作Service
* @createDate 2023-05-19 14:10:40
*/
public interface AgreementService extends BaseService<Agreement> {
    /**
     * 根据物料类型 id 查询有效集采（state = 'release'(calStatus = 1)）里的供应商（有效集采保证了供应商一定有效）
     */
    List<Long> getValidAgreementVendorIdListByMaterialTypeCode(String materialTypeCode);

    /**
     * 根据物料类型和供应商获取有效集采协议（state = 'release'(calStatus = 1)）
     * 应用场景：点击编辑或新增集采协议价时
     */
    List<AgreementVO> getValidAgreementListByMaterialTypeIdAndVendorId(Long materialTypeId, Long vendorId);
}
