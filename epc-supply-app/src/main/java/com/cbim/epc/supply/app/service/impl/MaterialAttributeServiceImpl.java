package com.cbim.epc.supply.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cbim.epc.supply.app.service.MaterialAttributeService;
import com.cbim.epc.supply.common.base.exception.EpcSupplyException;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.convert.SupplyMaterialAttributeConvert;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import com.cbim.epc.supply.data.mapper.SupplyMaterialAttributeMapper;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialAttributeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物料属性管理-服务实现
 */
@Service
public class MaterialAttributeServiceImpl extends BaseServiceImpl<SupplyMaterialAttributeMapper, SupplyMaterialAttribute> implements MaterialAttributeService {

    @Autowired
    private SupplyMaterialAttributeMapper materialAttributeMapper;

    @Override
    public SupplyMaterialAttributeVO get(Long id) {
        return SupplyMaterialAttributeConvert.INSTANCE.toVo(materialAttributeMapper.selectById(id));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(SupplyMaterialAttribute supplyMaterialAttribute) {
        return materialAttributeMapper.insert(supplyMaterialAttribute);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(SupplyMaterialAttribute supplyMaterialAttribute) {
        return materialAttributeMapper.updateById(supplyMaterialAttribute);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        try {
            return materialAttributeMapper.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EpcSupplyException("当前属性已被其它应用引用,无法删除");
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByMaterialId(Long id) {
        LambdaQueryWrapper<SupplyMaterialAttribute> eq = Wrappers.<SupplyMaterialAttribute>lambdaQuery()
                .eq(SupplyMaterialAttribute::getMaterialId, id);
        try {
            return materialAttributeMapper.delete(eq);
        } catch (Exception e) {
            throw new EpcSupplyException("当前属性已被其它应用引用,无法删除");
        }
    }

    @Override
    public List<SupplyMaterialAttribute> selectByMaterialId(Long materialId) {
        return materialAttributeMapper.selectList(SupplyMaterialAttribute::getMaterialId, materialId);
    }


}
