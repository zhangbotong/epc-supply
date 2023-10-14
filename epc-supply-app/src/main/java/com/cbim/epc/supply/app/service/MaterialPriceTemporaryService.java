package com.cbim.epc.supply.app.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cbim.epc.supply.common.base.service.BaseService;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporary;
import com.cbim.epc.supply.data.domain.MaterialPriceTemporaryJoin;
import com.cbim.epc.supply.data.vo.req.MaterialPriceTemporaryQueryParam;
import com.cbim.epc.supply.data.vo.resp.SupplyVendorVO;

import java.util.List;
import com.cbim.epc.supply.data.vo.resp.TemporaryPriceAvgVO;

/**
 * 临时报价-服务接口
 *
 * @author xiaozp
 * @since 2023/05/23
 */
public interface MaterialPriceTemporaryService extends BaseService<MaterialPriceTemporary> {

    /**
     * 临时报价-台账查询
     *
     * @param queryParam 请求入参
     * @return 分页结果
     */
    Page<MaterialPriceTemporaryJoin> search(MaterialPriceTemporaryQueryParam queryParam);


    /**
     * 获取临时报价的平均价信息
     *
     * @param materialId 物料id
     * @return /
     */
    TemporaryPriceAvgVO getAvgPrice(long materialId);
    /**
     * 获取临时价的供应商
     * @param materialId 物料id
     * @return List<SupplyVendorVO> 供应商列表
     */
    List<SupplyVendorVO> getTemporaryPriceVendor(Long materialId);

}
