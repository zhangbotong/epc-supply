package com.cbim.epc.supply.common.base.controller;

import com.cbim.epc.base.util.MultiResponse;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.EntDTO;
import com.cbim.epc.sdk.easyapi.commondto.IdNameDTO;
import com.cbim.epc.sdk.easyapi.metadto.AttrDTO;
import com.cbim.epc.sdk.easyapi.metadto.ObjCatalogDTO;
import com.cbim.epc.sdk.easyapi.metadto.ObjectDTO;
import com.cbim.epc.sdk.easyapi.metadto.ObjectGroupDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BaseRpcController {

    @Resource
    public EasyApiUtil apiUtil;

    /**
     * 对象列表（左侧对象树）
     *
     * @return /
     */
    @GetMapping("/get/objects")
    public MultiResponse<ObjCatalogDTO> getObjects() {
        List<ObjCatalogDTO> objectCatalog = apiUtil.getObjectCatalog();
        return MultiResponse.buildSuccess(objectCatalog);
    }

    /**
     * 对象属性
     *
     * @param objectId /
     * @return /
     */
    @GetMapping("/get/objectsDetail")
    public MultiResponse<ObjectGroupDTO> getObjectsDetail(@RequestParam("objectId") String objectId) {
        List<ObjectGroupDTO> objectDetail = apiUtil.getObjectDetail(objectId);
        return MultiResponse.buildSuccess(objectDetail);
    }

    /**
     * 对象类型关联企业
     *
     * @param objTypeCode /
     * @return /
     */
    @GetMapping("/get/objectsTypeDetail")
    public MultiResponse<ObjectDTO> getObjectsByType(@RequestParam("objTypeCode") String objTypeCode) {
        List<ObjectDTO> objectsByType = apiUtil.getObjectsByType(objTypeCode);
        return MultiResponse.buildSuccess(objectsByType);
    }

    /**
     * 企业列表
     *
     * @return /
     */
    @GetMapping("/get/epcEnts")
    public MultiResponse<EntDTO> getObjectsByType() {
        List<EntDTO> epcEnts = apiUtil.getEpcEnts();
        return MultiResponse.buildSuccess(epcEnts);
    }

    /**
     * 获取所有配置字典属性列表
     *
     * @return /
     */
    @GetMapping("/get/allAttr")
    public MultiResponse<IdNameDTO> getAllAttr() {
        List<IdNameDTO> allAttr = apiUtil.getAllAttr();
        return MultiResponse.buildSuccess(allAttr);
    }

    /**
     * 获取1个或多个配置字典属性的详情
     *
     * @param ids /
     * @return {@link MultiResponse}<{@link AttrDTO}>
     */
    @GetMapping("/get/attrsDetail")
    public MultiResponse<AttrDTO> getAttrsDetail(@RequestParam("ids") List<String> ids) {
      //  List<AttrDTO> attrsDetails = apiUtil.getAttrsDetail(ids);
        return MultiResponse.buildSuccess();
    }


}
