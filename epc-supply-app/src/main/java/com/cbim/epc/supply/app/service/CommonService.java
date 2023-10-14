package com.cbim.epc.supply.app.service;


import com.cbim.epc.supply.data.domain.ObjectAttr;


public interface CommonService {
    ObjectAttr getObjectAttr(String objectTypeCode, String entCode);
}
