package com.cbim.epc.supply.data.vo.req;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.cbim.epc.supply.common.mybatis.pojo.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaterialQueryParam extends PageParam {

    /**
     * 物料分类编码
     */
    private String objectTypeCode;

    /**
     * 供应商id
     */
    private String vendorId;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 排序字段
     */
    private String sortField = "updateDate";

    /**
     * 是否升序 desc:降序 asc:升序
     */
    private String sorted;

    /**
     * 发布状态（多选） 1：发布 ，0 草稿
     */
    private String publishStatus;

    /**
     * 物料分类编码 多个逗号隔开（用于移动端查询参数）
     */
    private String objectTypeCodes;

    /**
     * 物料来源（多选）
     */
    private String materialSources;

    /**
     * 是否查询所有 1:是(代表不区分供应商) 0:否（代表区分供应商数据）
     */
    private String isQueryAll = "0";

    /**
     * 适用范围 多个逗号隔开
     */
    private String applicableScopeCode;

    /**
     * 根据名称或者编码搜索
     */
    private String materialNameOrCode;

    /**
     * 物料id 多个
     */
    private List<String> ids;

    /**
     * 是否分页 1:是 0:否
     */
    private String isPage = "1";

    public String getSortField() {

        return sortField;
    }

    public static MaterialQueryParam buildParam(MaterialQueryParam rParam) {
        MaterialQueryParam param = new MaterialQueryParam();
        BeanUtils.copyProperties(rParam, param);
        if("0".equals(param.getIsPage())){
            param.setPageNum(null);
            param.setPageSize(null);
        }
        if (StrUtil.isBlank(param.getSortField()) || Objects.isNull(param.getSortField())) {
            param.setSortField("updateDate");
        }
        if (StrUtil.isBlank(param.getSorted()) || Objects.isNull(param.getSorted())) {
            param.setSorted("desc");
        }
        if (ObjectUtil.isNotEmpty(param.getPublishStatus())) {
            param.setPublishStatus(Arrays.asList(param.getPublishStatus().split(",")).stream().map(v -> String.format("'%s'", v)).collect(Collectors.joining(",")));
        }
        if (ObjectUtil.isNotEmpty(param.getObjectTypeCodes())) {
            param.setObjectTypeCodes(String.join(",", Arrays.asList(param.getObjectTypeCodes().split(",")).stream().map(v -> String.format("'%s'", v)).collect(Collectors.toList())));
        }
        if (ObjectUtil.isNotEmpty(param.getMaterialSources())) {
            param.setMaterialSources(String.join(",", Arrays.asList(param.getMaterialSources().split(",")).stream().map(v -> String.format("'%s'", v)).collect(Collectors.toList())));
        }
        if (ObjectUtil.isNotEmpty(param.getApplicableScopeCode())) {
            param.setApplicableScopeCode(String.join(",", Arrays.asList(param.getApplicableScopeCode().split(",")).stream().map(v -> String.format("'%s'", v)).collect(Collectors.toList())));
        }
        return param;
    }

}
