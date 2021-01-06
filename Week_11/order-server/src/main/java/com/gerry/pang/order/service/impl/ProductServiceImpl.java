package com.gerry.pang.order.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gerry.pang.order.dto.TbProductDTO;
import com.gerry.pang.order.entity.TbProduct;
import com.gerry.pang.order.enums.ProductStatus;
import com.gerry.pang.order.mapper.TbProductMapper;
import com.gerry.pang.order.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private TbProductMapper productMapper;

	@Override
	@Transactional
	public void saveProduct(TbProductDTO product) {
		TbProduct productEntity = productMapper.selectByPrimaryKey(product.getId());
		final int orderNum = product.getOrderNum().intValue();
		// 需要考虑使用分布式锁
		if (productEntity.getStoreNum().intValue() - orderNum >= 0 && productEntity.getProductStatus().intValue() == ProductStatus.NOT_DEDUCT.getCode()) {
			productEntity.setProductStatus(ProductStatus.DEDUCTING.getCode());
			productEntity.setStoreNum(productEntity.getStoreNum().intValue() - orderNum);
			productEntity.setUpdateTime(new Date());
			productEntity.setVersion(productEntity.getVersion() + 1);
			productMapper.updateByPrimaryKey(productEntity);
		} else {
			throw new IllegalArgumentException("库存不足！");
		}
	}
	
	public void confirmProductStatus(TbProductDTO product) {
		log.info("========= confirmProductStatus 库存进行confirm操作 start ================");
		TbProduct productEntity = productMapper.selectByPrimaryKey(product.getId());
		productEntity.setProductStatus(ProductStatus.NOT_DEDUCT.getCode());
		productMapper.updateByPrimaryKey(productEntity);
		log.info("=========confirmProductStatus 库存进行confirm操作 end ================");
	}
	
	public void cancleProductStatus(TbProductDTO product) {
		log.info("=========库存进行cancel操作 start================");
		TbProduct productEntity = productMapper.selectByPrimaryKey(product.getId());
		productEntity.setProductStatus(ProductStatus.NOT_DEDUCT.getCode());
		productEntity.setVersion(productEntity.getStoreNum().intValue() + product.getOrderNum());
		productEntity.setUpdateTime(new Date());
		productEntity.setVersion(productEntity.getVersion() + 1);
		productMapper.updateByPrimaryKey(productEntity);
		log.info("=========库存进行cancel操作 end================");
	}


}
