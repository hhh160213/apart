package com.school.ex.controller;

import com.alipay.api.AlipayApiException;
import com.school.ex.bo.PaymentBo;
import com.school.ex.entity.ApiResult;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Pay;
import com.school.ex.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController extends BaseController {
    @Autowired
    private PayService payService;

    @PostMapping("/getList")
    public ApiResult getList(PageVO pageVO,String apartment,String doorId,String payPeopleTel){
        ApiResult list = payService.getList(pageVO, apartment, doorId, payPeopleTel);
        return list;
    }

    @PostMapping("/getOwnerList")
    public ApiResult getOwnerList(PageVO pageVO, String payName, String status, HttpServletRequest request){
        Owner owner = (Owner) request.getSession().getAttribute("login");
        String name=owner.getName();
        ApiResult list = payService.getOwnerList(pageVO, payName, status,name);
        return list;
    }


//    @PostMapping("/payCharge")
//    public ApiResult payCharge(int payId, String payPeople, String payName, String payType, HttpServletRequest request){
//        //Owner owner = (Owner) request.getSession().getAttribute("login");
//        //String name=owner.getName();
//        int times = payService.payCharge(payId, payPeople, payName,payType,request);
//        if(times==2){
//            return ERROR(null,"已经缴费过不能缴费");
//        }
//        ApiResult result=new ApiResult(times,"缴费成功");
//        return result;
//    }



}
