package io.kimmking.dubbo.demo.consumer.mapper;

import io.kimmking.dubbo.demo.consumer.entity.TbUserAssets;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * tb_user_assetsMapper
 * @author system generate 
 */
@Repository
public interface TbUserAssetsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbUserAssets record);

    TbUserAssets selectByPrimaryKey(Integer id);

    List<TbUserAssets> selectAll();
    
    List<TbUserAssets> selectByCondition(TbUserAssets userAssets);

    int updateByUserAccount(TbUserAssets record);
}