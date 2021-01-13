package com.gerry.pang.fegin;

import org.springframework.stereotype.Component;

import com.gerry.pang.fegin.dto.TbProductDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductFeignHystrix implements ProductFeign {

	@Override
	public void orderProduct(TbProductDTO product) {
		log.info("===== orderProduct Hystrix ======");
	}

}
