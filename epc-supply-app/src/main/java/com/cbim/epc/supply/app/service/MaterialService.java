package com.cbim.epc.supply.app.service;

import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.common.base.service.BaseService;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.vo.req.MaterialParam;
import com.cbim.epc.supply.data.vo.req.MaterialQueryParam;
import com.cbim.epc.supply.data.vo.req.ProductQueryMaterialParam;
import com.cbim.epc.supply.data.vo.resp.MaterialResult;
import com.cbim.epc.supply.data.vo.resp.ProductMaterialVo;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialPriceVO;

import java.util.List;
import java.util.Map;

/**
 * 物料管理-服务接口
 *
 * @author xiaozp
 * @since 2023/4/4
 */
public interface MaterialService extends BaseService<SupplyMaterial> {

    PageResponse<MaterialResult> selectMaterialPage(MaterialQueryParam queryParam);

    SingleResponse saveOrUpdate(MaterialParam entity);

    /**
     * 根据id获取物料管理详情
     * @param id 主键id
     * @param operationType 操作类型 edit编辑 copy复制 detail详情
     * @return SupplyMaterialVO 物料管理详情信息
     */
    MaterialResult selectById(Long id,String operationType);

    /**
     * 根据id删除对应的物料管理记录
     * @param id 主键id
     * @return 删除的记录数
     */
    int deleteById(Long id);

    /**
     * 下架物料管理
     * @param id 物料管理主键id
     * @param isOff 是否下架
     * @return 下架的记录数量
     */
    int off(Long id, Integer isOff);

    SingleResponse toEdit(Long id);

    Map<String, Integer> getObjectCount();

    List<SupplyMaterialPriceVO> getMaterialByCode(String objTypeCode, Integer subDate, String applicableCode);

    /**
     * 根据物料类型编码获取物料，subData 字段集采价格未在表里维护，调用该方法时查询处理
     */
    List<SupplyMaterial> listByMaterialTypeCode(String materialTypeCode);

    List<ProductMaterialVo> selectByObjectTypeCodeAndEntCode(ProductQueryMaterialParam param);
}
