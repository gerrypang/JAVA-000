package com.gerry.pang.order.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gerry.pang.order.entity.TbProduct;

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