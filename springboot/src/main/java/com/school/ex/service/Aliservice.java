package com.school.ex.service;

import com.alipay.api.AlipayApiException;
import com.school.ex.pojo.AlipayBeanTwo;

public interface Aliservice {
    /**
     * 支付宝支付接口
     * @param alipayBean
     * @return
     * @throws AlipayApiException
     */
     String aliPay(AlipayBeanTwo alipayBean) throws AlipayApiException;

}
