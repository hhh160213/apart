package com.school.ex.pojo;

import lombok.Data;

/**
 * 支付实体对象
 * 根据支付宝接口协议，其中的属性名，必须使用下划线，不能修改
 */
@Data
public class AlipayBeanTwo {
private String outTradeNo;
private String totalAmount;
private String subject;


}
