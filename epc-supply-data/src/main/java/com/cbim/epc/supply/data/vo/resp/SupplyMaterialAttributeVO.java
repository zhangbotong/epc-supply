package com.cbim.epc.supply.data.vo.resp;

import com.cbim.epc.supply.data.domain.AttributeValue;
import lombok.*;

/**
 * <p>
 * 供应资源管理-物料管理属性表视图
 * </p>
 * @author 123
 */

@Data
public class SupplyMaterialAttributeVO {

    /**
     * 主键-使用雪花算法
     */
    private Long id;

    /**
     * 物料管理主表id
     */
    private Long materialId;

    /**
     * 当前版本，用于判断属性是否为最新版本
     */
    private Long version;

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
     * 值类型：text-文本；number-数值；date-日期
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
     * 日期格式：yyyy年MM月dd日/yyyy-MM-dd/yyyy年MM月dd日 HH:mm:ss/yyyy-MM-dd HH:mm:ss
     */
    private String datePattern;

    /**
     * 属性值，分为三种存储结构：
     * 枚举 ==>
     *     {
     *         "enumValues": {
     *             "1": {
     *                 "value": "12",
     *                 "check": "1"    // "1"表示勾选
     *             },
     *             "2": {
     *                 "value": "13",
     *                 "check": "0"    // "0"表示未勾选
     *             }
     *             ...
     *         }
     *     },
     * 输入 ==>
     *     {
     *       "inputValue": "xxxxxxx"
     *     },
     * 枚举区间 ==>
     *     {
     *         "rangeValue": {
     *             "1": {
     *                 "min": "",
     *                 "max": "",
     *                 "check": "1"
     *             },
     *             "2": {
     *                 "min": "",
     *                 "max": "",
     *                 "check": "0"
     *             },
     *             "3": {
     *                 "min": "",
     *                 "max": "",
     *                 "check": "1"
     *             }
     *             ...
     *         }
     *     }
     */

    private AttributeValue attributeValue;

    /**
     * 是否变更：0-未变更；1-已变更
     */
    private Integer hasChanges;
}
