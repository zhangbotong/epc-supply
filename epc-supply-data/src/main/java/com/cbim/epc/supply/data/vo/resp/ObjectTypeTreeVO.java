package com.cbim.epc.supply.data.vo.resp;

import lombok.Data;

import java.util.List;

@Data
public class ObjectTypeTreeVO {

    public Integer supplyCount;

    private String objCode;
    private Integer deep;
    private String code;
    private String pcode;
    private List<String> ents;
    private String pTitle;
    private String pid;
    private String pId;
    private String source;
    private String objName;
    private String title;
    private List<ObjectTypeTreeVO> children;
    private Boolean allowRemove;
    private String pCode;
    private String ptitle;
    private Integer count;
    private String id;
}
