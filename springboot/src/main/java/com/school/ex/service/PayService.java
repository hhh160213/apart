package com.school.ex.service;

import com.alipay.api.AlipayApiException;
import com.school.ex.bo.PaymentBo;
import com.school.ex.entity.ApiResult;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface PayService {
    /**
     * 支付宝支付接口
     * @param order
     * @return
     * @throws AlipayApiException
     */
    public ApiResult getList(PageVO pageVO, String apartment, String doorId, String payPeopleTel);
    public int update(Pay pay);
    public int delById(int id);



    int insert(Pay pay);

    ApiResult getOwnerList(PageVO pageVO, String payName, String status, String name);

    int payCharge(int id, HttpServletRequest request);
}
