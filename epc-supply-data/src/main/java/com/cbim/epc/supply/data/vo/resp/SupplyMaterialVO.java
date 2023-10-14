package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.FileInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 123
 */
@Data
public class SupplyMaterialVO {
    /**
     * 主键-使用雪花算法
     */
    private Long id;

    /**
     * 针对子表version字段汇总后求出的md5值
     */
    private String versionMd5;

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
    private String materialClassification;

    /**
     * 物料分类编码
     */
    private String objectTypeCode;

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
     * 物料编码
     */
    private String materialCode;

    /**
     * 缩略图
     */
    private List<FileInfo> thumbnail;

    /**
     * 上传后的文件信息
     */
    private List<FileInfo> fileInfo;

    /**
     * 物料来源：1-当期集采；2-过期集采；3-市场寻源；4-项目采购
     */
    private Integer materialSource;

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
     * 创建时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    /**
     * 更新时间
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 更新人名称
     */
    private String updateName;

    /**
     * 物料属性
     */
    List<GroupInfoVO> groupList;

}
