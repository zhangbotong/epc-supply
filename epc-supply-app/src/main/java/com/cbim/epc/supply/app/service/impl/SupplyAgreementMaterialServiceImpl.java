package com.cbim.epc.supply.app.service.impl;

import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.domain.SupplyAgreementMaterial;
import com.cbim.epc.supply.app.service.SupplyAgreementMaterialService;
import com.cbim.epc.supply.data.mapper.SupplyAgreementMaterialMapper;
import org.springframework.stereotype.Service;

/**
* @author kyrie
* @description 针对表【view_agreement_state_release(供应资源管理-集采协议-物料分类 关联关系表)】的数据库操作Service实现
* @createDate 2023-05-19 14:35:29
*/
@Service
public class SupplyAgreementMaterialServiceImpl extends BaseServiceImpl<SupplyAgreementMaterialMapper, SupplyAgreementMaterial>
    implements SupplyAgreementMaterialService{

}




