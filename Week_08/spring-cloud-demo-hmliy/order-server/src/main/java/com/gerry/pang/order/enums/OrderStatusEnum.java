package com.gerry.pang.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum {

    CREATE_ORDER(1, "创建单"),

	ORDER_FAIL(2, "创建单失败"),

	ORDER_SUCCESS(3, "创建单成功");

    private final int code;

    private final String desc;
}