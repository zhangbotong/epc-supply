package com.cbim.epc.supply.app.controller.rpc;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cbim.epc.base.util.MultiResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.sdk.easyapi.EasyApiUtil;
import com.cbim.epc.sdk.easyapi.basedto.UnitDTO;
import com.cbim.epc.sdk.easyapi.commondto.BaseReq;
import com.cbim.epc.sdk.easyapi.metadto.ObjTypeTreeDTO;
import com.cbim.epc.supply.app.service.CommonService;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.app.service.PriceService;
import com.cbim.epc.supply.common.base.controller.BaseRpcController;
import com.cbim.epc.supply.data.domain.ObjectAttr;
import com.cbim.epc.supply.data.vo.resp.ObjectTypeTreeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * 元数据controller
 */
@RestController
@Slf4j
@RequestMapping("/api/meta")
@RequiredArgsConstructor
public class MetaObjectRpcController extends BaseRpcController {

    private final EasyApiUtil apiUtil;

    private final MaterialService materialService;

    private final PriceService priceService;

    @Autowired
    private CommonService commonService;

    /**
     * 对象属性
     *
     * @param objectTypeCode 对象类型code
     * @param entCode        适用范围code
     * @return
     */
    @GetMapping("/getObjectAttr")
    public SingleResponse<ObjectAttr> getObjectAttr(@RequestParam("objectTypeCode") String objectTypeCode, @RequestParam("entCode") String entCode) {
        ObjectAttr objectAttr = commonService.getObjectAttr(objectTypeCode, entCode);
        return SingleResponse.buildSuccess(objectAttr);
    }

    @GetMapping("/tree")
    public MultiResponse<ObjectTypeTreeVO> selectMaterialPage(@RequestParam("objCode") String objCode) {
        List<ObjTypeTreeDTO> objectTypes = apiUtil.getObjectTypes(objCode);
        List<ObjectTypeTreeVO> typeTreeVOS = getTypeTreeVOS(objectTypes);
        return MultiResponse.buildSuccess(typeTreeVOS);
    }

    @GetMapping("/tree/price")
    public MultiResponse<ObjectTypeTreeVO> getPriceTree(@RequestParam("objCode") String objCode) throws ExecutionException, InterruptedException, TimeoutException {
        List<ObjTypeTreeDTO> objectTypes = apiUtil.getObjectTypes(objCode);
        List<ObjectTypeTreeVO> typeTreeVOS = getPriceTypeTreeVOS(objectTypes);
        return MultiResponse.buildSuccess(typeTreeVOS);
    }

    private List<ObjectTypeTreeVO> getTypeTreeVOS(List<ObjTypeTreeDTO> objectTypes) {
        if (CollectionUtil.isNotEmpty(objectTypes)) {
            Map<String, Integer> objectCount = materialService.getObjectCount();
            List<ObjectTypeTreeVO> typeTreeVOS = converterToVO(objectTypes);
            buildCount(typeTreeVOS, objectCount);
            return typeTreeVOS;
        }
        return new ArrayList<>();
    }

    private List<ObjectTypeTreeVO> getPriceTypeTreeVOS(List<ObjTypeTreeDTO> objectTypes) throws ExecutionException, InterruptedException, TimeoutException {
        if (CollectionUtil.isEmpty(objectTypes)) return new ArrayList<>();
        Map<String, Integer> objectCount = priceService.getPriceCountByMaterialTypeCode();
        List<ObjectTypeTreeVO> typeTreeVOS = converterToVO(objectTypes);
        buildCount(typeTreeVOS, objectCount);
        return typeTreeVOS;
    }

    private static List<ObjectTypeTreeVO> converterToVO(List<ObjTypeTreeDTO> objectTypes) {
        List<ObjectTypeTreeVO> typeTreeVOS;
        String jsonString = JSONObject.toJSONString(objectTypes);
        typeTreeVOS = JSON.parseObject(jsonString, new TypeReference<List<ObjectTypeTreeVO>>() {
        });
        return typeTreeVOS;
    }

    private void buildCount(List<ObjectTypeTreeVO> objectTypes, Map<String, Integer> objectCount) {
        if (CollectionUtil.isEmpty(objectTypes))
            return;
        objectTypes.forEach(obj -> {
            buildCount(obj.getChildren(), objectCount);
            Integer countOrDefault = objectCount.getOrDefault(obj.getCode(), 0);
            obj.setSupplyCount(countOrDefault);
        });
    }


    /**
     * 获取计量单位
     *
     * @return UnitDTO
     */
    @GetMapping({"/getUnit"})
    public MultiResponse<UnitDTO> getUnit() {
        List<UnitDTO> unit = apiUtil.getUnit(BaseReq.UnitType.unit);
        return MultiResponse.buildSuccess(unit);
    }
}
