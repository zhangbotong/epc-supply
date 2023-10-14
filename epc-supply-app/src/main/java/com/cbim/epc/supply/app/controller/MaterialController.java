package com.cbim.epc.supply.app.controller;

import com.cbim.epc.base.usercontext.UserContext;
import com.cbim.epc.base.usercontext.UserDTO;
import com.cbim.epc.base.util.PageResponse;
import com.cbim.epc.base.util.SingleResponse;
import com.cbim.epc.supply.app.service.MaterialService;
import com.cbim.epc.supply.data.vo.req.MaterialParam;
import com.cbim.epc.supply.data.vo.req.MaterialQueryParam;
import com.cbim.epc.supply.data.vo.resp.MaterialResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 物料管理
 */
@RestController
@Slf4j
@RequestMapping("/api/material")
@RequiredArgsConstructor
public class MaterialController {

    @Autowired
    private MaterialService materialService;


    /**
     * 权限
     */
    @RequestMapping("/auth")
    public SingleResponse<Map<String,Object>> auth() {
        Map<String,Object> result = new HashMap<>();
        String permission = "0";//管理员
        UserDTO user = UserContext.getUser();
        // id:1648154231308562432 账户:14100000002  id:1648220708107722752  账户:14100000003 供应商信息
        if ("1648154231308562432".equals(user.getId().toString()) || "1648220708107722752".equals(user.getId().toString()))
            permission = "1";//供应商
        result.put("permission",permission);
        return SingleResponse.buildSuccess(result);
    }

    /**
     * 台账列表
     */
    @RequestMapping("/search")
    public PageResponse<MaterialResult> selectMaterialPage(@RequestBody MaterialQueryParam queryParam) {
        return materialService.selectMaterialPage(queryParam);
    }

    /**
     * 新增或编辑
     */
    @RequestMapping("/saveOrUpdate")
    public SingleResponse saveOrUpdate(@RequestBody MaterialParam entity) {
        return materialService.saveOrUpdate(entity);
    }

    /**
     * 根据主键id获取详情
     *
     * @param id 主键id
     * @param operationType 操作类型 edit编辑 copy复制 detail详情
     * @return 详情信息
     */
    @GetMapping("/{id}/{operationType}")
    public SingleResponse<MaterialResult> selectById(@PathVariable(value = "id") Long id,@PathVariable(required = false,value = "operationType") String operationType) {
        return SingleResponse.buildSuccess(materialService.selectById(id,operationType));
    }

    /**
     * 根据主键id删除
     *
     * @param id 主键id
     * @return 删除的记录数
     */
    @DeleteMapping("/{id}")
    public SingleResponse<Integer> deleteById(@PathVariable(value = "id") Long id) {
        return SingleResponse.buildSuccess(materialService.deleteById(id));
    }

    /**
     * 下架
     * @param id 主键id
     * @param isOff 是否下架 0-下架/暂存；1-上架/发布
     * @return 下架的记录数量
     */
    @PutMapping("/off/{id}/{isOff}")
    public SingleResponse<Integer> off(@PathVariable(value = "id") Long id,
                                       @PathVariable(value = "isOff") Integer isOff) {
        return SingleResponse.buildSuccess(materialService.off(id, isOff));
    }


    /**
     * 跳转编辑页
     * @param id 主键id
     */
    @GetMapping("/toEdit/{id}")
    public SingleResponse<MaterialResult> toEdit(@PathVariable(value = "id") Long id){
        return materialService.toEdit(id);
    }

}
