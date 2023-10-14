package com.cbim.epc.supply.data.vo.req;

import com.cbim.epc.supply.common.mybatis.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Validated
public class MaterialPriceQueryParam extends PageParam {

    /**
     * 物料名称或编码
     */
    private String materialNameOrCode;

    /**
     * 物料分类编码
     */
    @NotBlank(message = "物料分类编码不能为空")
    private String objectTypeCode;

}
