package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbim.epc.supply.common.mybatis.dataobject.BaseDO;
import com.cbim.epc.supply.data.handler.FileInfoListTypeHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * <p>
 * 供应资源管理-物料管理主表
 * </p>
 *
 * @author xiaozp
 * @since 2023-04-04
 */
@TableName(value = "epc_supply_material", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SupplyMaterial extends BaseDO {

    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 对象version
     */
    private String objVersion;

    /**
     * 物料version
     */
    private String version;

    /**
     * 供应商id
     */
    private Long vendorId;

    /**
     * 父级名称
     */
    private String parentName;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 物料分类名称
     */
    private String objectTypeName;

    /**
     * 物料分类编码
     */
    private String objectTypeCode;

    /**
     * 对象id
     */
    private Long objectId;

    /**
     * 适用范围名称
     */
    private String applicableScopeName;

    /**
     * 适用范围编码
     */
    private String applicableScopeCode;

    /**
     * 计量单位名称
     */
    private String measureUnitName;

    /**
     * 计量单位编码
     */
    private String measureUnitCode;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料全名称
     */
    private String materialFullName;

    /**
     * 物料编码
     */
    private String materialCode;

    /**
     * 缩略图
     */
    @TableField(typeHandler = FileInfoListTypeHandler.class)
    private List<FileInfo> thumbnail;

    /**
     * 上传后的文件信息
     */
    @TableField(typeHandler = FileInfoListTypeHandler.class)
    private List<FileInfo> fileInfo;

    /**
     * 物料来源：1-当期集采；2-过期集采；3-市场寻源；4-项目采购
     */
    private String materialSource;

    /**
     * 发布状态：0-草稿；1-已发布
     */
    private Integer publishStatus;

    /**
     * 是否变更：0-未变更；1-已变更
     */
    private Integer hasChanges;

    /**
     * 0-没有发布过；1-发布过
     */
    private Integer hasBeenPublished;

    /**
     * 被引用次数
     */
    private Long referenceCount;

    /**
     * 子表数据：
     * 0-集采协议和历史合同都不存在；
     * 1-存在集采协议；（用于新增集采价时对已有价格对物料置灰）
     * 2-存在历史合同；
     * 3-集采协议和历史合同都存在
     */
    private int subData;

    /**
     * 属性和值拼接
     */
    private String attributeValues;
}
