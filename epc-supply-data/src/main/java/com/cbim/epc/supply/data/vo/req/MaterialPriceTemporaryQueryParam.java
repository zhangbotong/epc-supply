package com.cbim.epc.supply.data.vo.req;

import com.cbim.epc.supply.common.mybatis.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialPriceTemporaryQueryParam extends PageParam {

    /**
     * 物料分类编码
     */
    private String objectTypeCode;

    /**
     * 物料名称或物料编码
     */
    private String criteria;

    /**
     * 前端不用关注，后端计算
     */
    @JsonIgnore
    private Integer start;

}
