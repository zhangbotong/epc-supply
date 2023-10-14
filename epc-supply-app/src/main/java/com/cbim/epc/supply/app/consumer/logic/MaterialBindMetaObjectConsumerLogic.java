package com.cbim.epc.supply.app.consumer.logic;

import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cbim.epc.sdk.mq.content.SimpleChangeDTO;
import com.cbim.epc.sdk.mq.logic.SimpleChangeConsumerLogic;
import com.cbim.epc.supply.common.enums.ChangesEnum;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.mapper.SupplyMaterialMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 物料 消费 元数据对象变更的处理逻辑
 *
 * @author xiaozp
 * @since 2023/5/13
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MaterialBindMetaObjectConsumerLogic implements SimpleChangeConsumerLogic {

    private final SupplyMaterialMapper supplyMaterialMapper;

    @Override
    public void handle(List<SimpleChangeDTO> dataList) {
        log.info("----------------materialBindMetaObjectConsumerLogic消费逻辑调用----------------");
        if (IterUtil.isNotEmpty(dataList)) {
            for (SimpleChangeDTO simpleChangeDTO : dataList) {
                // 对象id
                String objId = simpleChangeDTO.getId();
                // 对象版本
                String objVersion = simpleChangeDTO.getVersion();
                LambdaQueryWrapper<SupplyMaterial> queryWrapper = Wrappers.lambdaQuery(SupplyMaterial.class);
                queryWrapper.eq(SupplyMaterial::getObjectId, NumberUtil.parseLong(objId));
                List<SupplyMaterial> materialList = supplyMaterialMapper.selectList(queryWrapper);
                if (IterUtil.isEmpty(materialList)) {
                    continue;
                }
                if (StrUtil.isNotBlank(objVersion)) {
                    // 剔除物料中对象version 与 MQ中对象version 相同的数据
                    materialList.removeIf(material ->
                            StrUtil.isNotBlank(material.getObjVersion())
                                    && StrUtil.equals(objVersion, material.getObjVersion())
                    );
                    if (IterUtil.isEmpty(materialList)) {
                        continue;
                    }
                }
                // 剩余的就是需要处理的数据了
                materialList.forEach(material -> {
                    material.setHasChanges(ChangesEnum.CHANGED.getCode());
                });
                supplyMaterialMapper.updateBatch(materialList, 1000);
            }
        }
    }
}
