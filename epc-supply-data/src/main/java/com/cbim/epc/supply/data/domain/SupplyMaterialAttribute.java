package com.cbim.epc.supply.data.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.cbim.epc.supply.common.mybatis.handler.EnumValuesTypeHandler;
import lombok.*;

import java.util.List;

/**
 * <p>
 * 供应资源管理-物料管理属性表
 * </p>
 *
 * @author xiaozp
 * @since 2023-04-04
 */
@TableName(value = "epc_supply_material_attribute", autoResultMap = true)
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplyMaterialAttribute {

    /**
     * 主键-使用雪花算法
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 物料管理主表id
     */
    private Long materialId;

    /**
     * 参数分组名称
     */
    private String groupName;

    /**
     * 参数分组编码
     */
    private Long groupCode;

    /**
     * 属性名称
     */
    private String attributeName;

    /**
     * 属性编码
     */
    private Long attributeCode;

    /**
     * 值类型：text-文本；number-数值
     */
    private String valueType;

    /**
     * 值输入类型：input-输入；enum-枚举；range-枚举区间
     */
    private String valueInputType;

    /**
     * 数值类型：integer-整数；float-小数
     */
    private String numericType;

    /**
     * 小数点后的位数
     */
    private Integer decimalPlaces;

    /**
     * 属性值，分为三种存储结构，
     *     枚举 ==>
     *     {
     *         "enumValues": {
     *             "1146037801575145474": {
     *                 "value": "123"
     *             },
     *             "1146037801575145475": {
     *                 "value": "124"
     *             }
     *             ...
     *         }
     *     },
     *     输入 ==>
     *     {
     *       "inputValue": "xxxxxxx"
     *     },
     *     枚举区间 ==>
     *     {
     *         "rangeValue": {
     *             "1146037801575145474": {
     *                 "min": "",
     *                 "max": ""
     *             },
     *             "1146037801575145475": {
     *                 "min": "",
     *                 "max": ""
     *             },
     *             "1146037801575145476": {
     *                 "min": "",
     *                 "max": ""
     *             }
     *             ...
     *         }
     *     }
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private NewAttributeValue attributeValue;

    /**
     * 已勾选的枚举值对应的key，有勾选的格式：["1146037801575145474","1146037801575145475"]
     */
    @TableField(typeHandler = EnumValuesTypeHandler.class)
    private List<String> checks;

    /**
     * 计量单位名称
     */
    private String unitName;

    /**
     * 计量单位编码
     */
    private String unitCode;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否必填：yes-是；no-否
     */
    private String isRequired;

    /**
     * 选择类型：radio-单选；check-多选
     */
    private String selectType;


    /**
     * 是否变更：0-未变更；1-已变更
     */
    private Integer hasChanges;
}
