package com.cbim.epc.supply.data.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

/**
 * 属性值
 *
 * @author xiaozp
 * @since 2023/5/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewAttributeValue {

    private String usedValue;

    private String inputValue;

    private Map<String, EnumValue> enumValues;

    private Map<String, RangeValue> rangeValue;

    @Data
    public static class RangeValue {
        private String min;
        private String max;
    }

    @Data
    public static class EnumValue {
        private String value;
    }
}
