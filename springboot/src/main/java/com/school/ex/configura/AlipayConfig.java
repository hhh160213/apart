package com.school.ex.configura;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置文件读取
 *
 */
@Configuration
//@ConfigurationProperties
//@PropertySource("classpath:config/alipay.properties")
@Data
@Component
public class AlipayConfig {

    // 商户appid
    public static String APPID = "2021000118612919";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCMj9nj8Ehpc5b4h3OIS7ZvwLwRi+xNhh4f5tnB2OIcMbNafjPQSZmGWwyOHeBKyZkySv9m37o/38FZfEh4kjqQsvc2x0AkTeufzldTLVoVIFmLVMGh1HKZpRo/3dazeR7HwX0hheBjr4XxjYGejVs3P1zw3bIL2z+Abat8mvDgUTznznJukjXPk5OO7XBJslU1rlgHXR/7If/R7qqSHezNvIsx8MD+87GqaW+c1lmNh5z53w+GdcsVT1Wf9C8JJEHlr7iR16zCf1HgTbEsewNGIuNP5+TY9r8j1+UqYvewIoNrYJbBdnK5hcMzEu2rgJDl4+ZKjB+nX0HdnL3PjZKpAgMBAAECggEAIEJvjnm6FMGbl4R2xoQh5boFL72+SPiL92Ri+7NMCY56jD1EpPwPSR+iXQeMT32/NCS0Qyb7tucil+gHNezRlh4M0LygAvUDK9UOg593+jtHccvM+NgqSQr0YE4IlX4py0yj7EquehJxmkOQyRPGKILZUMqAr2ntRAtnfRyORdzn0IjE0cXKOvNQdvK2wsbmyLMBs5JSoQB4gFqtenf756SPCizX1TgoKypSKo0gwEgu7ywGT8iOimq0MMah9jkfHrnT0JGtqh7tVoJp52VZWkOd0jX+0OF/ulBWWbMgnV8qx4dWpT4MPNPDsyEBspRHcpQRfiUzttAgjTE9J1XM1QKBgQDN9Td5qSW6G2qOAOreXPqDihMx8japv6ndN+MEOcYC94cJC5Av9A6CucHIHho3K4NIA0U5tZW4FDd3RWTuGFDLYj3dB8hMbfPW8bLknVHs1reFTu3MWttVw41lcoD8f4hO2xQGvO/MS/1Lliydg92YRCw5GeRkUWdwVNdhh5B0LwKBgQCutvDzhQ9qwZAWsIYbTY0oMMZ1TTJ5gvx5qaq9bALFtZcs5olheF52WeHS0EGimw1zdRQMdC+R33UPf/BXTsI/7rTkBo3U/V/mnG+G3W6NFtjbOVVxPN0OdSxycSON8jF19dJ9umiRPWrGb1vXFKAg3qhogphqgcN5sBdOfDC4pwKBgHpvvHrmwzbP9ZhVk0p9m6929dJLW5OuDR0ljB+G+N/qpqwJ472eKMIuC7UbsBn8FB9LUmvsNwUnf5G3/zhL3DwMOxqa9cf51WuCZAbkPz+8pXzqXxONL7bcecJFP8jbR/0Y397hkuywf/PfYsaBDcrnPCL18YBxpDxzM1cXcBihAoGABHO49Buh5vCDwXBCGpQBUtLbW8XZ0WrbECaYqTcFYxBbh9NgENHGZnGn5X1VDtWwpA6Jsj+LrsXo77os36ysGoD0rxLTrme+d9voo6Ek3geFAyEU4kvQvjuSJGBprYZMaFsZQAbt/zV3cRMJXhOyeOPzmqYpSB2CY6lvDprviuUCgYEAkc/4298wkeC46m8GsFB6fdFe/7zkTSNuTekFBbC9EaJ7q2JQIIElcFl78aFYDz50TMCtXzunuHwmMqst9sieus8U4cAZ6nF4+tQr/lfGwKwlYhyGMAuM1t9WiPfMRWwe0JVPblZ0z/5hPyivFdFYb8AXokK3UPtHr4HPdtkzD9Q=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = " http://localhost:8383/payCharge/fallback ";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://localhost:9999";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnV4vJhmxZETtR86yAqWPjd8M1BSNnt5+PhVT7EC8HTmQdCubpRtF8DirJvDX+fnQgPrLZt3jiVok9cKK/kGG4i5O8huGn5TYifFhamoGvoO332yKiaQknBwgL9gHTI/dqm++GnLPmcpmHly/DKBWk8oqU8ykyQV01Zg8bthDktTiPIbClEAyz1fPH2jOPTZfnfQwKNUSbMz+xuLVqPjP568AS+GenVVyRXWg81imuvXenWoPvdYGo/NDvBCkAYB/kAq1jjuVfw28dodZ8GWusFP3ReEL7vkuZ2DPuxaNHCmpPY9tWwaIqVVMtYqiFE6lznVCAi2i8iBPoOf57eYW2wIDAQAB";
    // 日志记录目录
    public static String log_path = "D:/logs/";
    // RSA2
    public static String SIGNTYPE = "RSA2";



}
