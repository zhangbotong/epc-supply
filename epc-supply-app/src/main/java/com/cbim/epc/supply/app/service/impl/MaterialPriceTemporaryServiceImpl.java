package com.cbim.epc.supply.app.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.supply.app.service.MaterialPriceTemporaryService;
import com.cbim.epc.supply.app.service.SupplyVendorService;
import com.cbim.epc.supply.common.base.service.impl.BaseServiceImpl;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporaryJoin;
import com.cbim.epc.supply.data.domain.SupplyCity;
import com.cbim.epc.supply.data.mapper.MaterialPriceTemporaryMapper;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporaryQueryParam;
import com.cbim.epc.supply.data.vo.resp.TemporaryPriceAvgVO;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 临时报价-服务实现
 *
 * @author xiaozp
 * @since 2023/05/24
 */
@Slf4j
@Service
public class MaterialPriceTemporaryServiceImpl extends BaseServiceImpl<MaterialPriceTemporaryMapper, MaterialPriceTemporary> implements MaterialPriceTemporaryService {

    @Autowired
    private MaterialPriceTemporaryMapper materialPriceTemporaryMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplyVendorService supplyVendorService;

    @Override
    public Page<MaterialPriceTemporaryJoin> search(MaterialPriceTemporaryQueryParam queryParam) {
        Page<MaterialPriceTemporaryJoin> temporaryExtPage = new Page<>();
        temporaryExtPage.setSize(queryParam.getPageSize());
        temporaryExtPage.setCurrent(queryParam.getPageNum());
        int count = materialPriceTemporaryMapper.queryCount(queryParam);
        temporaryExtPage.setTotal(count);
        if (count <= 0) {
            temporaryExtPage.setRecords(Collections.emptyList());
        } else {
            int pageNum = queryParam.getPageNum();
            int pageSize = queryParam.getPageSize();
            int start = (pageNum - 1) * pageSize;
            queryParam.setStart(start);
            List<MaterialPriceTemporaryJoin> resultList = materialPriceTemporaryMapper.queryByPage(queryParam);
            resultList.forEach(e -> {
                try {
                    String supplyCityStr = e.getSupplyCityStr();
                    List<SupplyCity> supplyCities = objectMapper.readValue(supplyCityStr, new TypeReference<List<SupplyCity>>() {
                    });
                    e.setSupplyCity(supplyCities);
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            });
            temporaryExtPage.setRecords(resultList);
        }
        return temporaryExtPage;
    }

    @Override
    public TemporaryPriceAvgVO getAvgPrice(long materialId) {
        TemporaryPriceAvgVO result = materialPriceTemporaryMapper.queryAvgPrice(materialId);
        result.setMaterialId(materialId);
        int count = result.getCount();
        if (count == 0) {
            result.setAvgTempPrice(BigDecimal.ZERO);
        } else {
            BigDecimal totalTempPrice = result.getTotalTempPrice();
            BigDecimal div = NumberUtil.div(totalTempPrice, count, 2, RoundingMode.HALF_UP);
            result.setAvgTempPrice(div);
        }
        return result;
    }
    @Override
    public List<SupplyVendorVO> getTemporaryPriceVendor(Long materialId) {
        List<SupplyVendorVO> supplyVendors = null;
        Wrapper<MaterialPriceTemporary> wrapper = Wrappers.<MaterialPriceTemporary>lambdaQuery()
                .select(MaterialPriceTemporary::getVendorId)
                .eq(MaterialPriceTemporary::getMaterialId, materialId);
        List<MaterialPriceTemporary> temporaryPrices = materialPriceTemporaryMapper.selectList(wrapper);
        if (CollectionUtil.isNotEmpty(temporaryPrices)){
            Set<Long> vendorIds = temporaryPrices
                    .stream()
                    .map(MaterialPriceTemporary::getVendorId)
                    .collect(Collectors.toSet());
            supplyVendors = supplyVendorService.selectList(vendorIds);
        }

        return supplyVendors;
    }



}
