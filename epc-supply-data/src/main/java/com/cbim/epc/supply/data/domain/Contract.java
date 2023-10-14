package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cbim.epc.supply.common.mybatis.dataobject.BaseDO;
import com.cbim.epc.supply.data.handler.ContractorListTypeHandler;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 合同管理-合同主表
 * </p>
 *
 * @author xiaozp
 * @since 2023-06-08
 */
@TableName(value ="epc_contract", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends BaseDO {

    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 合同编码
     */
    private String contractCode;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 适用范围名称
     */
    private String applicableScopeName;

    /**
     * 适用范围编码
     */
    private String applicableScopeCode;

    /**
     * 合同分类：销售合同；采购合同
     */
    private String contractClassification;

    /**
     * 合同性质：主合同；补充协议
     */
    private String contractNature;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 签约金额
     */
    private BigDecimal signAmount;

    /**
     * 合同签定日期
     */
    private LocalDate signDate;

    /**
     * 合同周期
     */
    private String contractCycle;

    /**
     * 所属项目名称
     */
    private String projectName;

    /**
     * 所属项目编码
     */
    private String projectCode;

    /**
     * 建设地址
     */
    private String constructionAddress;

    /**
     * 质保规则
     */
    private String warrantyRule;

//    /**
//     * 附件
//     */
//    @TableField(typeHandler = FileInfoListTypeHandler.class)
//    private List<FileInfo> attachment;

//    /**
//     * 发包人
//     */
//    @TableField(typeHandler = EmployerListTypeHandler.class)
//    private List<Employer> employer;

    /**
     * 承包人
     */
    @TableField(typeHandler = ContractorListTypeHandler.class)
    private List<Contractor> contractor;

    /**
     * 数据类型：import-历史导入
     */
    private String dataType;


}