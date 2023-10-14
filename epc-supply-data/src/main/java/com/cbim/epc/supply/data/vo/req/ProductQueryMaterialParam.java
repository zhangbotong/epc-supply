package com.cbim.epc.supply.data.vo.req;

import lombok.Data;

import java.util.List;

@Data
public class ProductQueryMaterialParam {

    private String entCode;


    private List<String> objectCodes;

}
