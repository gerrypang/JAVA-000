package io.kimmking.dubbo.demo.consumer.mapper;

import io.kimmking.dubbo.demo.consumer.entity.TbFrozenAssets;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * tb_frozen_assetsMapper
 * @author system generate 
 */
@Repository
public interface TbFrozenAssetsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbFrozenAssets record);

    TbFrozenAssets selectByPrimaryKey(Integer id);

    List<TbFrozenAssets> selectAll();

    List<TbFrozenAssets> selectByCondition(TbFrozenAssets record);

    int updateByPrimaryKey(TbFrozenAssets record);

    int updateByCondition(TbFrozenAssets record);
    
	int unfrozenAssets(TbFrozenAssets record);
}