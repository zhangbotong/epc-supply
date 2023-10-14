package com.cbim.epc.supply.data.convert;

import com.cbim.epc.supply.common.mybatis.pojo.PageResult;
import com.cbim.epc.supply.data.domain.FileInfo;
import com.cbim.epc.supply.data.domain.SupplyMaterial;
import com.cbim.epc.supply.data.vo.resp.SupplyMaterialVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-27T16:07:26+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 1.8.0_251 (Oracle Corporation)"
)
public class SupplyMaterialConvertImpl implements SupplyMaterialConvert {

    @Override
    public SupplyMaterial toEntity(SupplyMaterialVO vo) {
        if ( vo == null ) {
            return null;
        }

        SupplyMaterial.SupplyMaterialBuilder supplyMaterial = SupplyMaterial.builder();

        supplyMaterial.id( vo.getId() );
        supplyMaterial.parentName( vo.getParentName() );
        supplyMaterial.parentCode( vo.getParentCode() );
        supplyMaterial.objectTypeCode( vo.getObjectTypeCode() );
        supplyMaterial.applicableScopeName( vo.getApplicableScopeName() );
        supplyMaterial.applicableScopeCode( vo.getApplicableScopeCode() );
        supplyMaterial.measureUnitName( vo.getMeasureUnitName() );
        supplyMaterial.measureUnitCode( vo.getMeasureUnitCode() );
        supplyMaterial.materialName( vo.getMaterialName() );
        supplyMaterial.materialCode( vo.getMaterialCode() );
        List<FileInfo> list = vo.getThumbnail();
        if ( list != null ) {
            supplyMaterial.thumbnail( new ArrayList<FileInfo>( list ) );
        }
        List<FileInfo> list1 = vo.getFileInfo();
        if ( list1 != null ) {
            supplyMaterial.fileInfo( new ArrayList<FileInfo>( list1 ) );
        }
        if ( vo.getMaterialSource() != null ) {
            supplyMaterial.materialSource( String.valueOf( vo.getMaterialSource() ) );
        }
        supplyMaterial.publishStatus( vo.getPublishStatus() );
        supplyMaterial.hasChanges( vo.getHasChanges() );
        supplyMaterial.hasBeenPublished( vo.getHasBeenPublished() );
        supplyMaterial.referenceCount( vo.getReferenceCount() );

        return supplyMaterial.build();
    }

    @Override
    public List<SupplyMaterialVO> toVoList(List<SupplyMaterial> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SupplyMaterialVO> list = new ArrayList<SupplyMaterialVO>( entities.size() );
        for ( SupplyMaterial supplyMaterial : entities ) {
            list.add( toVo( supplyMaterial ) );
        }

        return list;
    }

    @Override
    public List<SupplyMaterial> toEntities(List<SupplyMaterialVO> voList) {
        if ( voList == null ) {
            return null;
        }

        List<SupplyMaterial> list = new ArrayList<SupplyMaterial>( voList.size() );
        for ( SupplyMaterialVO supplyMaterialVO : voList ) {
            list.add( toEntity( supplyMaterialVO ) );
        }

        return list;
    }

    @Override
    public PageResult<SupplyMaterialVO> toVoPage(PageResult<SupplyMaterial> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<SupplyMaterialVO> pageResult = new PageResult<SupplyMaterialVO>();

        pageResult.setList( toVoList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setSize( page.getSize() );
        pageResult.setCurrent( page.getCurrent() );
        pageResult.setPages( page.getPages() );

        return pageResult;
    }

    @Override
    public SupplyMaterialVO toVo(SupplyMaterial entity) {
        if ( entity == null ) {
            return null;
        }

        SupplyMaterialVO supplyMaterialVO = new SupplyMaterialVO();

        supplyMaterialVO.setId( entity.getId() );
        supplyMaterialVO.setParentName( entity.getParentName() );
        supplyMaterialVO.setParentCode( entity.getParentCode() );
        supplyMaterialVO.setObjectTypeCode( entity.getObjectTypeCode() );
        supplyMaterialVO.setApplicableScopeName( entity.getApplicableScopeName() );
        supplyMaterialVO.setApplicableScopeCode( entity.getApplicableScopeCode() );
        supplyMaterialVO.setMeasureUnitName( entity.getMeasureUnitName() );
        supplyMaterialVO.setMeasureUnitCode( entity.getMeasureUnitCode() );
        supplyMaterialVO.setMaterialName( entity.getMaterialName() );
        supplyMaterialVO.setMaterialCode( entity.getMaterialCode() );
        List<FileInfo> list = entity.getThumbnail();
        if ( list != null ) {
            supplyMaterialVO.setThumbnail( new ArrayList<FileInfo>( list ) );
        }
        List<FileInfo> list1 = entity.getFileInfo();
        if ( list1 != null ) {
            supplyMaterialVO.setFileInfo( new ArrayList<FileInfo>( list1 ) );
        }
        if ( entity.getMaterialSource() != null ) {
            supplyMaterialVO.setMaterialSource( Integer.parseInt( entity.getMaterialSource() ) );
        }
        supplyMaterialVO.setPublishStatus( entity.getPublishStatus() );
        supplyMaterialVO.setHasChanges( entity.getHasChanges() );
        supplyMaterialVO.setHasBeenPublished( entity.getHasBeenPublished() );
        supplyMaterialVO.setReferenceCount( entity.getReferenceCount() );
        supplyMaterialVO.setCreateDate( entity.getCreateDate() );
        supplyMaterialVO.setUpdateDate( entity.getUpdateDate() );
        supplyMaterialVO.setCreateName( entity.getCreateName() );
        supplyMaterialVO.setUpdateName( entity.getUpdateName() );

        return supplyMaterialVO;
    }
}
