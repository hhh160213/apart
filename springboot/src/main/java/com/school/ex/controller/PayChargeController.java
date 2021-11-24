package com.school.ex.controller;


import com.school.ex.bo.PaymentBo;
import com.school.ex.pojo.PayItemInformationParam;
import com.school.ex.service.PayChargeService;
import com.school.ex.service.PayService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  支付宝支付，控制器
 *
 * @author wangziyang
 * */
@RestController
@RequestMapping( "/payCharge")
@AllArgsConstructor
public class PayChargeController extends BaseController{

    private PayChargeService payChargeService;

    @Autowired
    private PayService payService;

    /**
     *  下单支付
     * */
    @GetMapping(value = "/confirm" , produces = {"text/html;charset=UTF-8"})
    public Object pay (PaymentBo bo,HttpServletRequest request) throws Exception {
        //这个接口其实应该是post方式的，但是我这里图方便，直接以get方式访问，
        //且返回格式是text/html，这样前端页面就能直接显示支付宝返回的html片段
        //真实场景下由post方式请求，返回code、msg、data那种格式的标准结构，让前端拿到data里的
        //html片段之后自行加载

        //由于我这里并没有真正的传参数，所以象征性的new一下，避免空指针
        return payChargeService.pay(bo);
    }


    /**
     *  支付成功的回调
     * */
    @PostMapping(value = "/fallback")
    public Object fallback (HttpServletRequest request) {
        Map map = request.getParameterMap();
        return null;
    }

}