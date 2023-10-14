package com.cbim.epc.supply.app.service;

import com.cbim.epc.supply.data.vo.resp.MaterialCategoryPriceCountVO;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Kyrie
 * @date 2023/7/12 17:11
 */
public interface PriceService {
    /**
     * 查询物料类型下有价格的物料数量，3张价格表的合集，并且根据 materialId 去重
     *  1、查询集采价格表物料id list
     *  2、查询历史合同价表物料id list；(或物料表的 subdata 字段)
     *  3、查询临时报价物料id list
     *  4、合并去重得到有价格的 物料id list
     *  5、从物料表根据 materialTypeCode，统计集合中的物料id数量
     * @return Map<String, Integer>，k = materialTypeCode, v = count
     */
    Map<String, Integer> getPriceCountByMaterialTypeCode() throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * 通过物料分类编码，查询三种不同类型（集采、历史合同、临时）价格下的物料计数
     *
     * @param materialCategoryCode 物料分类编码，例如：MS-JSCL.2.2.2
     * @return /
     */
    MaterialCategoryPriceCountVO getMaterialCategoryPriceCount(String materialCategoryCode) throws Exception;
}
