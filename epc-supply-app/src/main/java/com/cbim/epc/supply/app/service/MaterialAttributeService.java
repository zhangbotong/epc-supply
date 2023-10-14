package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.common.base.service.BaseService;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialAttributeVO;

import java.util.List;

/**
 * 物料属性管理-服务接口
 * @author 123
 */
public interface MaterialAttributeService extends BaseService<SupplyMaterialAttribute> {

    /**
     * 根据主键id获取属性记录详情
     * @param id 主键id
     * @return SupplyMaterialAttributeVO 属性详情信息
     */
    SupplyMaterialAttributeVO get(Long id);

    /**
     * 添加物料属性信息
     * @param supplyMaterialAttribute 物料属性信息
     * @return 创建的记录数
     */
    int create(SupplyMaterialAttribute supplyMaterialAttribute);

    /**
     * 更新物料属性信息
     * @param supplyMaterialAttribute 物料属性信息
     * @return 更新的记录数
     */
    int update(SupplyMaterialAttribute supplyMaterialAttribute);

    /**
     * 根据主键id删除对应的属性记录
     * @param id 主键id
     * @return 删除的记录数
     */
    int delete(Long id);


    /**
     * 根据物料id删除对应的属性记录
     * @param materialId 物料id
     * @return 删除的记录数
     */
    int deleteByMaterialId(Long materialId);


    /**
     * 根据物料id获取属性列表信息
     * @param materialId 解析规则id
     * @return SupplyMaterialAttributeVO 族信息列表
     */
    List<SupplyMaterialAttribute> selectByMaterialId(Long materialId);



}
