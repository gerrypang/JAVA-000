package com.gerry.pang.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    NOT_DEDUCT(1, "未扣减库存"),

    DEDUCTING(2, "扣减库存中"),

    DEDUCT_FAIL(3, "扣减库存失败"),

    DEDUCT_SUCCESS(4, "扣减库存成功");

    private final int code;

    private final String desc;
}
