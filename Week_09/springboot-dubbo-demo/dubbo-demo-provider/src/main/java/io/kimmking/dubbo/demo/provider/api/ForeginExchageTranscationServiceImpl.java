package io.kimmking.dubbo.demo.provider.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.kimmking.dubbo.demo.api.dto.UserTransactionDTO;
import io.kimmking.dubbo.demo.api.service.ForeginExchageTranscationService;
import io.kimmking.dubbo.demo.provider.entity.TbFrozenAssets;
import io.kimmking.dubbo.demo.provider.entity.TbUserAssets;
import io.kimmking.dubbo.demo.provider.mapper.TbFrozenAssetsMapper;
import io.kimmking.dubbo.demo.provider.mapper.TbUserAssetsMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@DubboService(version = "1.0.0")
public class ForeginExchageTranscationServiceImpl implements ForeginExchageTranscationService {
	
	@Autowired
	private TbUserAssetsMapper userAssetsMapper;
	
	@Autowired
	private TbFrozenAssetsMapper frozenAssetsMapper;
	
	@Override
	@HmilyTCC(confirmMethod="transactionConfirm", cancelMethod="transactionCancle")
	@Transactional(rollbackFor = Exception.class)
	public Boolean transaction(UserTransactionDTO userTransactionDTO) {
		log.info(">>>>> transaction start ");
		boolean result = false;
		final Integer toUserAccount = userTransactionDTO.getToUserAccount();
		final String toAssetsType = userTransactionDTO.getToAssetsType();
		final BigDecimal toAmount = userTransactionDTO.getToAmount();
		
		// 查找账号是否存在
		List<TbUserAssets> user = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(toUserAccount).build());
		if (CollectionUtils.isEmpty(user)) {
			String errMsg = String.format("用户%d不存在", toUserAccount);
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}

		// 查看查看金额是否满足
		List<TbUserAssets> userAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(toUserAccount).assetsType(toAssetsType).build());
		if (CollectionUtils.isEmpty(userAssetsList)) {
			String errMsg = String.format("用户%d资产%s为0", toUserAccount, toAssetsType);
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		}
		
		TbUserAssets oneTypeUserAssets = userAssetsList.get(0);
		if (oneTypeUserAssets.getAmount().compareTo(toAmount) < 0) {
			String errMsg = String.format("用户%d资产%s小于%f", toUserAccount, toAssetsType, toAmount.floatValue());
			log.error(errMsg);
			throw new IllegalArgumentException(errMsg);
		} 
		
		// 冻结金额
		TbFrozenAssets frozenAssets = TbFrozenAssets.builder()
				.userAccount(toUserAccount)
				.assetsType(toAssetsType).assetStatus(0)
				.amount(toAmount).frozenTime(new Date()).build();
		frozenAssetsMapper.insert(frozenAssets);
		
		// from 账户减 amount
		oneTypeUserAssets.setAmount(oneTypeUserAssets.getAmount().subtract(toAmount));
		userAssetsMapper.updateByUserAccount(oneTypeUserAssets);
		
		// 随机模拟异常
		if (new Random().nextInt(10) % 2 == 0) {
			result = true;
		} else {
			throw new HmilyRuntimeException("扣减异常！");
		}
		log.info("=====> result:" + result);
		return result;
	}
	
	public Boolean transactionConfirm(UserTransactionDTO userTransactionDTO) {
		log.info("===== transactionConfirm ===== ");
		final Integer toUserAccount = userTransactionDTO.getToUserAccount();
		final String toAssetsType = userTransactionDTO.getToAssetsType();
		final BigDecimal toAmount = userTransactionDTO.getToAmount();

		final String fromAssetsType = userTransactionDTO.getFromAssetsType();
		final BigDecimal fromAmount = userTransactionDTO.getFromAmount();

		// 解冻金额
		TbFrozenAssets frozenAssets = TbFrozenAssets.builder().userAccount(toUserAccount).assetStatus(0).amount(toAmount).assetsType(toAssetsType).build();
		List<TbFrozenAssets> frozenAssetsList = frozenAssetsMapper.selectByCondition(frozenAssets);
		frozenAssetsList.forEach(n -> {
			// 解冻
			n.setAssetStatus(1);
			n.setUnfrozenTime(new Date());
			frozenAssetsMapper.unfrozenAssets(n);
		});

		// 更新账号金额
		List<TbUserAssets> toUserAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(toUserAccount).assetsType(toAssetsType).build());
		List<TbUserAssets> userAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(toUserAccount).assetsType(fromAssetsType).build());
		if (CollectionUtils.isNotEmpty(userAssetsList)) {
			userAssetsList.forEach(n -> {
				log.info("用户{} 资金类型：{}，余额：{}", toUserAccount, n.getAmount(), fromAssetsType);
				n.setAmount(n.getAmount().add(fromAmount));
				userAssetsMapper.updateByUserAccount(n);
			});
		} else {
			TbUserAssets toUserAssets = toUserAssetsList.get(0);
			TbUserAssets newUserAssets = TbUserAssets.builder()
					.username(toUserAssets.getUsername())
					.userAccount(toUserAccount)
					.assetsType(fromAssetsType)
					.amount(fromAmount).build();
			userAssetsMapper.insert(newUserAssets);
		}
		return true; 
	}

	public Boolean transactionCancle(UserTransactionDTO userTransactionDTO) {
		log.info("===== transactionCancle ===== ");
		final Integer toUserAccount = userTransactionDTO.getToUserAccount();
		final String toAssetsType = userTransactionDTO.getToAssetsType();
		final BigDecimal toAmount = userTransactionDTO.getToAmount();
		// 解冻金额
		TbFrozenAssets frozenAssets = TbFrozenAssets.builder().userAccount(toUserAccount).assetStatus(0).amount(toAmount).assetsType(toAssetsType).build();
		List<TbFrozenAssets> frozenAssetsList = frozenAssetsMapper.selectByCondition(frozenAssets);
		frozenAssetsList.forEach(n -> {
			// 解冻
			n.setAssetStatus(1);
			n.setUnfrozenTime(new Date());
			frozenAssetsMapper.unfrozenAssets(n);
		});
		
		// 恢复账号金额
		List<TbUserAssets> userAssetsList = userAssetsMapper.selectByCondition(TbUserAssets.builder().userAccount(toUserAccount).assetsType(toAssetsType).build());
		if (CollectionUtils.isNotEmpty(userAssetsList)) {
			userAssetsList.forEach(n -> {
				n.setAmount(n.getAmount().add(toAmount));
				userAssetsMapper.updateByUserAccount(n);
			});
		}
		return true; 
	}

}
