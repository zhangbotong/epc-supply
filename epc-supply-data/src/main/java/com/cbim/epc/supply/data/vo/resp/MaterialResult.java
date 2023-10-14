package com.cbim.epc.supply.data.vo.resp;


import com.cbim.epc.supply.data.domain.GroupInfo;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialResult extends SupplyMaterial {

    /**
     * 物料属性
     */
    List<GroupInfo> groupList;

    /**
     * 主图
     */
    private String mainPic;

    /**
     * 是否显示操作按钮 0：不显示 1：显示
     */
    private String showBtn;

    /**
     * 集采协议价格值类型：fixed-固定值；range-范围值 <br/>
     * ① fixed：即固定值时，取 agreementPriceMax 中的内容 <br/>
     * ② range：即范围值时，取 [agreementPriceMin, agreementPriceMax]
     */
    private String agreementPriceValueType;

    /**
     * 集采协议价的最小值
     */
    private BigDecimal agreementPriceMin;

    /**
     * 集采协议价的最大值
     */
    private BigDecimal agreementPriceMax;

    /**
     * 历史合同价的单价平均值
     */
    private BigDecimal contractPriceAverage ;

    /**
     * 市场价/临时价平均值
     */
    private BigDecimal marketPriceAverage;

    /**
     * 物料的供应商
     */
    private List<SupplyVendorVO> supplyVendors;

}
