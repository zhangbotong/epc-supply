package com.cbim.epc.supply.app.service.impl;

import com.cbim.epc.supply.app.service.VendorService;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.domain.Vendor;
import com.cbim.epc.supply.data.mapper.VendorMapper;
import org.springframework.stereotype.Service;

/**
* @author kyrie
* @description 针对表【view_supply_vendor】的数据库操作Service实现
* @createDate 2023-06-05 10:01:54
*/
@Service
public class VendorServiceImpl extends BaseServiceImpl<VendorMapper, Vendor>
    implements VendorService{
}




