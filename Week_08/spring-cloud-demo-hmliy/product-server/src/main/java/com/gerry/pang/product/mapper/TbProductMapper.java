package com.gerry.pang.product.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gerry.pang.product.entity.TbProduct;

/**
 * tb_productMapper
 * @author system generate 
 */
@Repository
public interface TbProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbProduct record);

    TbProduct selectByPrimaryKey(Integer id);

    List<TbProduct> selectAll();

    int updateByPrimaryKey(TbProduct record);
}