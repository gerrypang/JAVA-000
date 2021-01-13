package com.gerry.pang.order.mapper;

import com.gerry.pang.order.entity.TbOrderProductRelation;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * tb_order_product_relationMapper
 * @author system generate 
 * @since 2020-12-14 16:49:30
 */
@Repository
public interface TbOrderProductRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOrderProductRelation record);

    TbOrderProductRelation selectByPrimaryKey(Integer id);

    List<TbOrderProductRelation> selectAll();

    int updateByPrimaryKey(TbOrderProductRelation record);
}