package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.MaterialAttributeService;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialAttributeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 物料属性管理
 * @author 123
 *
 */
@RestController
@RequestMapping("/api/material/attribute")
public class SupplyMaterialAttributeController {

    @Autowired
    private MaterialAttributeService materialAttributeService;

    /**
     * 根据主键id获取属性记录详情
     * @param id 主键id
     * @return SupplyMaterialAttributeVO 属性记录详情信息
     */
    @GetMapping("/{id}")
    public SingleResponse<SupplyMaterialAttributeVO> get(@PathVariable(value = "id") Long id){
        return SingleResponse.buildSuccess(materialAttributeService.get(id));
    }


    /**
     * 添加物料属性信息
     * @param supplyMaterialAttribute 物料属性信息
     * @return 创建的记录数
     */
    @PostMapping
    public SingleResponse<Integer> create(@RequestBody SupplyMaterialAttribute supplyMaterialAttribute){
        return SingleResponse.buildSuccess(materialAttributeService.create(supplyMaterialAttribute));
    }

    /**
     * 更新物料属性信息
     * @param supplyMaterialAttribute 物料属性信息
     * @return 更新的记录数
     */
    @PutMapping
    public SingleResponse<Integer> update(@RequestBody SupplyMaterialAttribute supplyMaterialAttribute){
        return SingleResponse.buildSuccess(materialAttributeService.update(supplyMaterialAttribute));
    }


    /**
     * 根据主键id删除对应的属性记录
     * @param id 主键id
     * @return 删除的记录数
     */
    @DeleteMapping("/{id}")
    public SingleResponse<Integer> delete(@PathVariable(value = "id") Long id){
        return SingleResponse.buildSuccess(materialAttributeService.delete(id));
    }

}
