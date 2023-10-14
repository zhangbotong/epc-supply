package com.cbim.epc.supply.data.vo.resp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cbim.epc.supply.data.domain.FileInfo;
import com.cbim.epc.supply.data.handler.FileInfoListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMaterialVo {

    /**
     * 物料id
     */
    private Long id;

    /**
     * 物料的名称
     */
    private String materialName;

    /**
     * 物料的编码
     */
    private String materialCode;

    /**
     * 物料分类编码
     */
    private String objectTypeCode;

    /**
     * 默认供应商名称
     */
    private String  defaultVendorName;

    /**
     * 默认供应商编码
     */
    private String defaultVendorCode;

    /**
     * 默认供应商id
     */
    private String defaultVendorId;

    /**
     * 默认集采价格最小值
     */
    private String defaultVendorPriceMin;

    /**
     * 默认集采价格最大值
     */
    private String defaultVendorPriceMax;

    /**
     * 主图
     */
    private String thumbnailUrl;
}
