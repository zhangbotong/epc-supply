package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cbim.epc.supply.app.service.AgreementPriceService;
import com.cbim.epc.supply.app.service.MaterialPriceTemporaryService;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.app.service.PriceService;
import com.cbim.epc.supply.common.constant.ObjectCodeCountConstant;
import com.cbim.epc.supply.common.enums.ValidEnum;
import com.cbim.epc.supply.data.domain.AgreementPrice;
import com.cbim.epc.supply.data.domain.MaterialContractPrice;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.mapper.MaterialAgreementPriceMapper;
import com.cbim.epc.supply.data.mapper.MaterialPriceContractMapper;
import com.cbim.epc.supply.data.mapper.MaterialPriceTemporaryMapper;
import com.cbim.epc.supply.data.vo.resp.MaterialCategoryPriceCountVO;
import com.github.yulichang.toolkit.JoinWrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kyrie
 * @date 2023/7/12 17:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {
    private final AgreementPriceService agreementPriceService;
    private final MaterialPriceContractMapper contractPriceMapper;
    private final MaterialPriceTemporaryMapper temporaryPriceMapper;
    private final MaterialPriceTemporaryService temporaryPriceService;
    private final MaterialService materialService;
    private final MaterialAgreementPriceMapper materialAgreementPriceMapper;

    @Resource(name = "epcSupplyThreadPool")
    private ThreadPoolTaskExecutor threadPool;

    @Override
    public Map<String, Integer> getPriceCountByMaterialTypeCode() throws ExecutionException, InterruptedException, TimeoutException {
        Map<String,Integer> map = new HashMap<>();
        Future<List<Long>> agreementPriceFuture = threadPool.submit(() -> {
            List<AgreementPrice> agreementPriceList = agreementPriceService.list(new LambdaQueryWrapper<AgreementPrice>().select(AgreementPrice::getMaterialId));
            return agreementPriceList.stream().map(AgreementPrice::getMaterialId).collect(Collectors.toList());
        });
        Future<List<Long>> contractPriceFuture = threadPool.submit(() -> {
            List<MaterialContractPrice> contractPriceList = contractPriceMapper.selectList(Wrappers.lambdaQuery(MaterialContractPrice.class).select(MaterialContractPrice::getMaterialId));
            contractPriceList = contractPriceList == null ? new ArrayList<>() : contractPriceList;
            return contractPriceList.stream().map(MaterialContractPrice::getMaterialId).collect(Collectors.toList());
        });
        Future<List<Long>> saleInvoiceFuture = threadPool.submit(() -> {
            List<MaterialPriceTemporary> temporaryPriceList = temporaryPriceService.list(Wrappers.lambdaQuery(MaterialPriceTemporary.class).select(MaterialPriceTemporary::getMaterialId));
            return temporaryPriceList.stream().map(MaterialPriceTemporary::getMaterialId).collect(Collectors.toList());
        });
        List<Long> materialListByAgreementPrice = agreementPriceFuture.get(120, TimeUnit.SECONDS);
        List<Long> materialListByContractPrice = contractPriceFuture.get(120, TimeUnit.SECONDS);
        List<Long> materialListByTemporaryPrice = saleInvoiceFuture.get(120, TimeUnit.SECONDS);
        List<Long> materialIdList = Stream
                .of(materialListByAgreementPrice, materialListByContractPrice, materialListByTemporaryPrice)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        List<Map<String, Object>> mapList = materialService.getBaseMapper().selectMaps(new QueryWrapper<SupplyMaterial>()
                .select(ObjectCodeCountConstant.OBJECT_TYPE_CODE + ",count(1) as " + ObjectCodeCountConstant.OBJECT_TYPE_COUNT)
                .lambda()
                .in(SupplyMaterial::getId, materialIdList)
                .groupBy(SupplyMaterial::getObjectTypeCode)
        );
        if (CollectionUtil.isEmpty(mapList)) return map;
        mapList.forEach(m -> {
            map.put(m.get(ObjectCodeCountConstant.OBJECT_TYPE_CODE).toString(),
                    Integer.valueOf(m.get(ObjectCodeCountConstant.OBJECT_TYPE_COUNT).toString())
            );
        });
        return map;
    }

    @Override
    public MaterialCategoryPriceCountVO getMaterialCategoryPriceCount(String materialCategoryCode) throws Exception {
        MaterialCategoryPriceCountVO result = new MaterialCategoryPriceCountVO();
        Future<Integer> agreementCountFuture = threadPool.submit(() -> {
            log.info(Thread.currentThread().getName() + "处理集采协议价：{}", materialCategoryCode);
            return materialAgreementPriceMapper.getMaterialCategoryAgreementPriceCount(materialCategoryCode);
        });
        Future<Integer> historyContractCountFuture = threadPool.submit(() -> {
            // TODO: 历史合同
            log.info(Thread.currentThread().getName() + "处理历史合同价：{}", materialCategoryCode);
            return this.getContractPriceCount(materialCategoryCode);
        });
        Future<Integer> temporaryCountFuture = threadPool.submit(() -> {
            // TODO: 临时报价
            log.info(Thread.currentThread().getName() + "处理临时报价：{}", materialCategoryCode);
            return this.getTemporaryPriceCount(materialCategoryCode);
        });
        Integer agreementCount = agreementCountFuture.get(60, TimeUnit.SECONDS);
        Integer historyContractCount = historyContractCountFuture.get(60, TimeUnit.SECONDS);
        Integer temporaryCount = temporaryCountFuture.get(60, TimeUnit.SECONDS);
        if (Objects.nonNull(agreementCount)) {
            result.setAgreementCount(agreementCount);
        }
        if (Objects.nonNull(historyContractCount)) {
            result.setHistoryContractCount(historyContractCount);
        }
        if (Objects.nonNull(temporaryCount)) {
            result.setTemporaryCount(temporaryCount);
        }
        return result;
    }

    private Integer getContractPriceCount(String materialCategoryCode) {
        MPJLambdaWrapper<MaterialContractPrice> wrapper = JoinWrappers.lambda(MaterialContractPrice.class)
                .select("distinct material_id")
                .innerJoin(SupplyMaterial.class, SupplyMaterial::getId, MaterialContractPrice::getMaterialId)
                .eq(MaterialContractPrice::getIsValid, ValidEnum.VALID.getCode())
                .likeRight(SupplyMaterial::getObjectTypeCode, materialCategoryCode);
        return Math.toIntExact(contractPriceMapper.selectJoinCount(wrapper));
    }

    private Integer getTemporaryPriceCount(String materialCategoryCode) {
        MPJLambdaWrapper<MaterialPriceTemporary> wrapper = JoinWrappers.lambda(MaterialPriceTemporary.class)
                .select("distinct material_id")
                .innerJoin(SupplyMaterial.class, SupplyMaterial::getId, MaterialPriceTemporary::getMaterialId)
                .likeRight(SupplyMaterial::getObjectTypeCode, materialCategoryCode);
        return Math.toIntExact(temporaryPriceMapper.selectJoinCount(wrapper));
    }
}
