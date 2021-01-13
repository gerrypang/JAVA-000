package com.gerry.pang.order.mapper;

import com.gerry.pang.order.entity.TbOrder;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * tb_orderMapper
 * @author system generate 
 * @since 2020-12-14 16:49:30
 */
@Repository
public interface TbOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TbOrder record);

    TbOrder selectByPrimaryKey(Integer id);

    List<TbOrder> selectAll();

    int updateByPrimaryKey(TbOrder record);
}