package com.cbim.epc.supply.data.convert;

import com.cbim.epc.supply.common.mybatis.pojo.PageResult;
import com.cbim.epc.supply.data.domain.AttributeValue;
import com.cbim.epc.supply.data.domain.NewAttributeValue;
import com.cbim.epc.supply.data.domain.SupplyMaterialAttribute;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialAttributeVO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-27T16:07:26+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
)
public class SupplyMaterialAttributeConvertImpl implements SupplyMaterialAttributeConvert {

    @Override
    public SupplyMaterialAttribute toEntity(SupplyMaterialAttributeVO vo) {
        if ( vo == null ) {
            return null;
        }

        SupplyMaterialAttribute.SupplyMaterialAttributeBuilder supplyMaterialAttribute = SupplyMaterialAttribute.builder();

        supplyMaterialAttribute.id( vo.getId() );
        supplyMaterialAttribute.materialId( vo.getMaterialId() );
        supplyMaterialAttribute.groupName( vo.getGroupName() );
        supplyMaterialAttribute.groupCode( vo.getGroupCode() );
        supplyMaterialAttribute.attributeName( vo.getAttributeName() );
        supplyMaterialAttribute.attributeCode( vo.getAttributeCode() );
        supplyMaterialAttribute.valueType( vo.getValueType() );
        supplyMaterialAttribute.valueInputType( vo.getValueInputType() );
        supplyMaterialAttribute.numericType( vo.getNumericType() );
        supplyMaterialAttribute.attributeValue( attributeValueToNewAttributeValue( vo.getAttributeValue() ) );
        supplyMaterialAttribute.unitName( vo.getUnitName() );
        supplyMaterialAttribute.unitCode( vo.getUnitCode() );
        supplyMaterialAttribute.sort( vo.getSort() );
        supplyMaterialAttribute.isRequired( vo.getIsRequired() );
        supplyMaterialAttribute.selectType( vo.getSelectType() );
        supplyMaterialAttribute.hasChanges( vo.getHasChanges() );

        return supplyMaterialAttribute.build();
    }

    @Override
    public List<SupplyMaterialAttribute> toEntities(List<SupplyMaterialAttributeVO> voList) {
        if ( voList == null ) {
            return null;
        }

        List<SupplyMaterialAttribute> list = new ArrayList<SupplyMaterialAttribute>( voList.size() );
        for ( SupplyMaterialAttributeVO supplyMaterialAttributeVO : voList ) {
            list.add( toEntity( supplyMaterialAttributeVO ) );
        }

        return list;
    }

    @Override
    public PageResult<SupplyMaterialAttributeVO> toVoPage(PageResult<SupplyMaterialAttribute> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<SupplyMaterialAttributeVO> pageResult = new PageResult<SupplyMaterialAttributeVO>();

        pageResult.setList( toVoList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setSize( page.getSize() );
        pageResult.setCurrent( page.getCurrent() );
        pageResult.setPages( page.getPages() );

        return pageResult;
    }

    @Override
    public SupplyMaterialAttributeVO toVo(SupplyMaterialAttribute entity) {
        if ( entity == null ) {
            return null;
        }

        SupplyMaterialAttributeVO supplyMaterialAttributeVO = new SupplyMaterialAttributeVO();

        supplyMaterialAttributeVO.setId( entity.getId() );
        supplyMaterialAttributeVO.setMaterialId( entity.getMaterialId() );
        supplyMaterialAttributeVO.setGroupName( entity.getGroupName() );
        supplyMaterialAttributeVO.setGroupCode( entity.getGroupCode() );
        supplyMaterialAttributeVO.setAttributeName( entity.getAttributeName() );
        supplyMaterialAttributeVO.setAttributeCode( entity.getAttributeCode() );
        supplyMaterialAttributeVO.setValueType( entity.getValueType() );
        supplyMaterialAttributeVO.setValueInputType( entity.getValueInputType() );
        supplyMaterialAttributeVO.setNumericType( entity.getNumericType() );
        supplyMaterialAttributeVO.setUnitName( entity.getUnitName() );
        supplyMaterialAttributeVO.setUnitCode( entity.getUnitCode() );
        supplyMaterialAttributeVO.setSort( entity.getSort() );
        supplyMaterialAttributeVO.setIsRequired( entity.getIsRequired() );
        supplyMaterialAttributeVO.setSelectType( entity.getSelectType() );
        supplyMaterialAttributeVO.setAttributeValue( newAttributeValueToAttributeValue( entity.getAttributeValue() ) );
        supplyMaterialAttributeVO.setHasChanges( entity.getHasChanges() );

        return supplyMaterialAttributeVO;
    }

    @Override
    public List<SupplyMaterialAttributeVO> toVoList(List<SupplyMaterialAttribute> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SupplyMaterialAttributeVO> list = new ArrayList<SupplyMaterialAttributeVO>( entities.size() );
        for ( SupplyMaterialAttribute supplyMaterialAttribute : entities ) {
            list.add( toVo( supplyMaterialAttribute ) );
        }

        return list;
    }

    protected NewAttributeValue.EnumValue enumValueToEnumValue(AttributeValue.EnumValue enumValue) {
        if ( enumValue == null ) {
            return null;
        }

        NewAttributeValue.EnumValue enumValue1 = new NewAttributeValue.EnumValue();

        enumValue1.setValue( enumValue.getValue() );

        return enumValue1;
    }

    protected Map<String, NewAttributeValue.EnumValue> stringEnumValueMapToStringEnumValueMap(Map<String, AttributeValue.EnumValue> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, NewAttributeValue.EnumValue> map1 = new LinkedHashMap<String, NewAttributeValue.EnumValue>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, AttributeValue.EnumValue> entry : map.entrySet() ) {
            String key = entry.getKey();
            NewAttributeValue.EnumValue value = enumValueToEnumValue( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected NewAttributeValue.RangeValue rangeValueToRangeValue(AttributeValue.RangeValue rangeValue) {
        if ( rangeValue == null ) {
            return null;
        }

        NewAttributeValue.RangeValue rangeValue1 = new NewAttributeValue.RangeValue();

        rangeValue1.setMin( rangeValue.getMin() );
        rangeValue1.setMax( rangeValue.getMax() );

        return rangeValue1;
    }

    protected Map<String, NewAttributeValue.RangeValue> stringRangeValueMapToStringRangeValueMap(Map<String, AttributeValue.RangeValue> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, NewAttributeValue.RangeValue> map1 = new LinkedHashMap<String, NewAttributeValue.RangeValue>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, AttributeValue.RangeValue> entry : map.entrySet() ) {
            String key = entry.getKey();
            NewAttributeValue.RangeValue value = rangeValueToRangeValue( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected NewAttributeValue attributeValueToNewAttributeValue(AttributeValue attributeValue) {
        if ( attributeValue == null ) {
            return null;
        }

        NewAttributeValue newAttributeValue = new NewAttributeValue();

        newAttributeValue.setInputValue( attributeValue.getInputValue() );
        newAttributeValue.setEnumValues( stringEnumValueMapToStringEnumValueMap( attributeValue.getEnumValues() ) );
        newAttributeValue.setRangeValue( stringRangeValueMapToStringRangeValueMap( attributeValue.getRangeValue() ) );

        return newAttributeValue;
    }

    protected AttributeValue.EnumValue enumValueToEnumValue1(NewAttributeValue.EnumValue enumValue) {
        if ( enumValue == null ) {
            return null;
        }

        AttributeValue.EnumValue enumValue1 = new AttributeValue.EnumValue();

        enumValue1.setValue( enumValue.getValue() );

        return enumValue1;
    }

    protected Map<String, AttributeValue.EnumValue> stringEnumValueMapToStringEnumValueMap1(Map<String, NewAttributeValue.EnumValue> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, AttributeValue.EnumValue> map1 = new LinkedHashMap<String, AttributeValue.EnumValue>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, NewAttributeValue.EnumValue> entry : map.entrySet() ) {
            String key = entry.getKey();
            AttributeValue.EnumValue value = enumValueToEnumValue1( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected AttributeValue.RangeValue rangeValueToRangeValue1(NewAttributeValue.RangeValue rangeValue) {
        if ( rangeValue == null ) {
            return null;
        }

        AttributeValue.RangeValue rangeValue1 = new AttributeValue.RangeValue();

        rangeValue1.setMin( rangeValue.getMin() );
        rangeValue1.setMax( rangeValue.getMax() );

        return rangeValue1;
    }

    protected Map<String, AttributeValue.RangeValue> stringRangeValueMapToStringRangeValueMap1(Map<String, NewAttributeValue.RangeValue> map) {
        if ( map == null ) {
            return null;
        }

        Map<String, AttributeValue.RangeValue> map1 = new LinkedHashMap<String, AttributeValue.RangeValue>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<String, NewAttributeValue.RangeValue> entry : map.entrySet() ) {
            String key = entry.getKey();
            AttributeValue.RangeValue value = rangeValueToRangeValue1( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }

    protected AttributeValue newAttributeValueToAttributeValue(NewAttributeValue newAttributeValue) {
        if ( newAttributeValue == null ) {
            return null;
        }

        AttributeValue attributeValue = new AttributeValue();

        attributeValue.setInputValue( newAttributeValue.getInputValue() );
        attributeValue.setEnumValues( stringEnumValueMapToStringEnumValueMap1( newAttributeValue.getEnumValues() ) );
        attributeValue.setRangeValue( stringRangeValueMapToStringRangeValueMap1( newAttributeValue.getRangeValue() ) );

        return attributeValue;
    }
}
