package com.unisound.iot.dao.mapper.source1.album;

import com.unisound.iot.common.modle.dataRpc.AlbumType;
import com.unisound.iot.common.modle.dataRpc.AlbumTypeDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Created by yingwuluohan on 2018/10/7.
 * @Company 北京云知声技术有限公司
 */
@Mapper
public interface SoundAlbumDao {


    /**
     * 音频专辑合集分类列表查询
     * @param albumType
     * @return
     */
    List<AlbumType> findSoundAlbumList(AlbumType albumType);

    /**
     * 创建专辑合集分类
     * @param albumType
     */
    void addAlbumType(AlbumType albumType);

    /**
     * 创建专辑分类子表
     * @param albumTypeDetail
     */
    void addAlbumTypeDetail(AlbumTypeDetail albumTypeDetail);

    /**
     * 编辑专辑合集主表
     * @param albumType
     */
    void updateAlbumType(AlbumType albumType);

    /**
     * 删除专辑合集子表
     * @param albumTypeId
     * @param albumId
     * @param updater
     */
    void deleteAlbumTypeDetail(@Param("albumTypeId") Long albumTypeId, @Param("albumId") Long albumId, @Param("updater") String updater);
    void deleteAlbumDetailById(@Param("albumTypeId") Long albumTypeId ,@Param("updater") String updater);

    /**
     * 更新原有专辑合集子表的权重
     * @param albumType
     */
    void updateAlbumDetailLevel(AlbumType albumType);

    /**
     * 根据主键查询专辑合集的详细信息
     * @param albumTypeId
     * @return
     */
    AlbumType findAlbumTypeInfo( @Param("albumTypeId") Long albumTypeId );

    /**
     * 分页查询专辑合集行数
     * @param albumType
     * @return
     */
    Integer findSoundAlbumCount(AlbumType albumType);

    /**
     * 查询专辑合集子表
     * @param albumTypeId
     * @return
     */
    List<Long> findAlbumTypeDetail( @Param("albumTypeId")Long albumTypeId );

    /**
     * 删除专辑合集
     * @param albumTypeId
     * @param updater
     */
    void deleteAlbumType(@Param("albumTypeId")Long albumTypeId,@Param("updater") String updater);

    /**
     * 查询专辑合集主键
     * @param scopeType
     * @param scopeValue
     * @return
     */
    List<Long> findAlbumTypeIdList(@Param("scopeType")String scopeType,@Param("scopeValue") String scopeValue);

    List<String> findTemplateInfo();
}
