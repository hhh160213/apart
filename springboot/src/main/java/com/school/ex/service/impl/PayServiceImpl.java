package com.school.ex.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
//import com.alipay.easysdk.factory.Factory;
//import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.ex.bo.PaymentBo;
import com.school.ex.dao.PayDao;
import com.school.ex.entity.ApiResult;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Pay;
import com.school.ex.service.PayService;
//import com.school.ex.util.AlipayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PayServiceImpl implements PayService {
    @Autowired

    private PayDao payDao;

    //支付成功后要跳转的页面
    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Override
    public ApiResult getList(PageVO pageVO, String apartment, String doorId, String payPeopleTel) {
        LambdaQueryWrapper<Pay> queryWrapper = new LambdaQueryWrapper<>();
        if(!apartment.equals("")){
            queryWrapper.like(Pay::getApartment,apartment);
        }
        if(!doorId.equals("")){
            queryWrapper.like(Pay::getDoorId,doorId);
        }
        if(!payPeopleTel.equals("")){
            queryWrapper.like(Pay::getPayPeopleTel,payPeopleTel);
        }
        Page<Pay> page = new Page<>(pageVO.getPage(),pageVO.getLimit());
        IPage<Pay> list = payDao.selectPage(page,queryWrapper);
        ApiResult apiResult=new ApiResult(list.getTotal(),list.getRecords());
        return apiResult;
    }

    /**
     * 获取业主缴费信息
     * @param pageVO
     * @param payName
     * @param status
     * @param name
     * @return
     */
    @Override
    public ApiResult getOwnerList(PageVO pageVO, String payName, String status,String name) {
        LambdaQueryWrapper<Pay> queryWrapper = new LambdaQueryWrapper<>();
        if(!name.equals("")){
            queryWrapper.eq(Pay::getPayPeople,name);
        }
        if(!payName.equals("")){
            queryWrapper.eq(Pay::getPayName,payName);
        }
        if(!status.equals("")){
            queryWrapper.eq(Pay::getStatus,status);
        }
        Page<Pay> page = new Page<>(pageVO.getPage(),pageVO.getLimit());
        IPage<Pay> list = payDao.selectPage(page,queryWrapper);
        ApiResult apiResult=new ApiResult(list.getTotal(),list.getRecords());
        return apiResult;
    }





    @Override
    public int payCharge(int payId,HttpServletRequest request) {
        Pay pay = payDao.selectById(payId);
        pay.setPayType("支付宝");
        if (pay.getStatus().equals("已支付")){
            return 2;
        }
        pay.setStatus("已支付");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        pay.setDeadLine(time);

        return payDao.updateById(pay);
    }

    @Override
    public int update(Pay pay) {
        return 0;
    }

    @Override
    public int delById(int id) {
        return 0;
    }





    @Override
    public int insert(Pay pay) {
        return payDao.insert(pay);
    }
}
