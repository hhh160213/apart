package com.school.ex.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.school.ex.entity.ApiResult;
import com.school.ex.entity.OwnerVO;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Property;
import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;
import com.school.ex.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

//首页接口
@RestController
@RequestMapping("/")
public class IndexController extends BaseController {

    //登录接口
    //参数:account 账号,password 密码

    @Autowired
    private IndexService indexService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @PostMapping("/login")
    public ApiResult login(String account, String password,String code,String logintype, HttpServletRequest request){
        //获取session验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code) ||!captcha.equalsIgnoreCase(code)){
            return ERROR(null,"验证码输入错误，请重新输入");
        }

        //判断登录用户类别
        if(logintype.equals("1")){
            //管理员登录
            Property property = indexService.loginProperty(account,password);
            if (property==null){
                return ERROR(property,"账号密码错误");
            }

            //设置 标识
            property.setLogintype("1");
            return OK(property);
        }else{
            //业主登录
            Owner owner=indexService.loginOwner(account,password);
            if(owner==null){
                return ERROR(owner,"账号密码错误");
            }

            //设置 标识
            owner.setLogintype("2");
            request.getSession().setAttribute("login",owner);
            return OK(owner);
        }
    }

    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        //-------------------生成验证码 begin --------------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("验证码内容:"+text);
        //将验证码文本内容放入session
        request.getSession().setAttribute("captcha",text);

        //根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片,格式为jpg
            ImageIO.write(image,"jpg",outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证码 end --------------------------
    }

    @GetMapping("/")
    public int index(){
        return 12;
    }

    //获取全部业主信息
    //参数:PageVo 分页参数,name 查询条件:姓名,tel 查询条件:电话
    @PostMapping("/getOwnerList")
    public ApiResult getOwnerList(PageVO pageVO,String name,String tel){
        name=name==null?"":name;
        tel=tel==null?"":tel;
        List<OwnerVO> list = indexService.getOwnerList(pageVO,name,tel);
        ApiResult result = OK(list);
        return result;
    }


    @PostMapping("/getallownerList")
    public ApiResult getallownerList(){
        List<Owner> allList= indexService.getallList();
        return OK(allList);
    }
    @PostMapping("/getrepairname")
    public ApiResult getList(String name){
        List<Owner> namelist = indexService.getrepaieOwnerList(name);
        return  OK(namelist);
    }

    @PostMapping("/updateOwner")
    public ApiResult addOwner(OwnerVO owner){
        int result = indexService.updateOwner(owner);
        if (result==0){
            return ERROR(0,"该号码已存在");
        }
        return OK(1);
    }


    @PostMapping("/updateOwenerPwd")
    public ApiResult updateOwenerPwd(int id,String oldpwd,String newpwd){
        int result = indexService.updateOwenerPwd(id,oldpwd,newpwd);
        if(result==0){
            return ERROR(0,"旧密码错误,请重试");
        }
        return OK(1);
    }
}
