package com.school.ex.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayConfig {
    // 请填写您的AppId，例如：2019091767145019（必填）
    private static final String appID = "2021000118612919";
    //应用私钥，这里修改生成的私钥即可（必填）
    private static final String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFPHxp+6eZsRRq9AQ1fdMdfoq1qtc/6xMpUhKQYDqdVOmjFEHa5ASDIs1M9+K7N1UWAjT20HtRW6U1TiaPcofw81EGvhXzygG4XQk0cuxk8TKtXMQEK4eG4OHIaaP05p1/FVPRE8mv2ATyc74CO3oBjCO0VR50xTa4rH6yw/xKlP0Kv+C0DDgXN6teXKTx2+x+aQmRNdfPyaViTgA7GHqoE7JyMHVzheHxsNFMy3uXxqph4H9kyXlbii3OQB9o3tINhn6KvIIK3WXdShRvd9lpKzLjneJvjpnMIwmhcB9JQYW4tSB9NdlEmXJlPP+9bptVszJleh3T+48wkc1Js3SVAgMBAAECggEAFWdCFUR4CBiKTyQkCPPNlk9duRFYwsvgqCKnGMpcoCmj3q7CZYd7shVFJbgslCavZHm2SG/1X3x5bbbAOwsLC4oK2ipvHFq3LAbGE8bictTMb00XsBPOEbzOiINv80eYxXGHpW6oV7c8FDbDr1geoWEB21ZRLjEAOWp8b/tL7C8Hfup2PU3R/j0X5KJ42XkDQmaNzmNRL1ivynd6sSBZ7kW6cE4Bn8pjNyQNs3aF0S4vu7DXIlFIEDuOr/YsFeyMlXbkl/2g0tl7m1OWU2cjOgr34CzKBGb9gDPbZfvl/th3zt27mFlYnt4ecI9muTFAjzRwDpuKrds/BWFas5M0AQKBgQDFeixiF+6V56p2/OFIDXRFpYpFsTPP8ze3zj90DxvoOddTud+h3CM8//ubpez/FdotgIZd2QgTkhyuhh1RcRFOLUYXA5gWy5jqcl5D22YaezIq1/d4Lug4IWJlqX44UbFe/ICyFPS9SgT0A5Qv/xwU4PGgZgd8HP0QSjNhryeR3QKBgQCsuJn7spUXY1GHmN13cl2dnw0O1892raSfsKuZ9/kwz4IwRoFpbqGZPDrtfk3rlzl/wKhDUTExncPk71AICFKDDIUF5svjdFFbyXtWPonNr2Dc+msHyr2tv1bse6h30wduJLoDRLrcXNkIh3NVXs8s9jDb2EKNQVrHP9fN6lquGQKBgB08vGt9jZZmKUfaouQPEeLp4nH1DW3BXcN55s0hG4mjXq0HvQNy20jBwZfG1jLiIl5QRBSK+HYlUNfKBXc0XVKMw27O2mqtlCNJ8YjsZ86N5NyRAxOpJjqBPtc8Y/KkrbUZf1heX+nSlq8mBH+irp6suzl7QapvhEwwIDFbRGmFAoGAFxpCDnaelphESXza8qUrfG/P0VJwf5FOWcXJzjhq7mpzIX3sxWt01ghGYYnpZj++AyaD2y07BUXy8+ZGsCxczQaZrGZoky31y+8xPiHzUL3KmQQt2Mb3hAv51nvb1ts1PThQYSuKSvtoVEm51Ytu7ranZmCQ1lANkmUpunNEWdECgYBzyhJM3/kVQQU1cnEGNJKfO+mHS9//VeAdjLhilXozyimPMR+SINSInB9cX6OnPuaDpiLStRFvbiW1qYwISJvlJG+fV6XTRs7pmZ7lUl23X7UOZ0KWoUsZXwpQZZcq4zSlNRKtWCTjFq3DKzYlvT6ZfyEGvhm0AHB3pHUNDRtezA==";
    //支付宝公钥，而非应用公钥（必填）
    public static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhTx8afunmbEUavQENX3THX6KtarXP+sTKVISkGA6nVTpoxRB2uQEgyLNTPfiuzdVFgI09tB7UVulNU4mj3KH8PNRBr4V88oBuF0JNHLsZPEyrVzEBCuHhuDhyGmj9OadfxVT0RPJr9gE8nO+Ajt6AYwjtFUedMU2uKx+ssP8SpT9Cr/gtAw4FzerXlyk8dvsfmkJkTXXz8mlYk4AOxh6qBOycjB1c4Xh8bDRTMt7l8aqYeB/ZMl5W4otzkAfaN7SDYZ+iryCCt1l3UoUb3fZaSsy453ib46ZzCMJoXAfSUGFuLUgfTXZRJlyZTz/vW6bVbMyZXod0/uPMJHNSbN0lQIDAQAB";
    //默认即可（必填）
    public static final String charset = "utf-8";
    //默认即可（必填）
    public static final String signType = "RSA2";
    @Bean
    public AlipayClient alipayClient(){
        //沙箱环境使用https://openapi.alipaydev.com/gateway.do，线上环境使用https://openapi.alipay.com/gateway.do
        return new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", appID, privateKey, "json", charset, publicKey, signType);
    }
    /**
     * 验签，是否正确
     */
    public static boolean checkSign(HttpServletRequest request){
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>();
        requestMap.forEach((key, values) -> {
            String strs = "";
            for(String value : values) {
                strs = strs + value;
            }
            System.out.println(key +"===>"+strs);
            paramsMap.put(key, strs);
        });
        System.out.println();
        //调用SDK验证签名
        try {
            return AlipaySignature.rsaCheckV1(paramsMap, PayConfig.publicKey, PayConfig.charset, PayConfig.signType);
        } catch (AlipayApiException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("*********************验签失败********************");
            return false;
        }

    }
}
