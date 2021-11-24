package com.school.ex.controller;

import com.school.ex.entity.ApiResult;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Property;
import com.school.ex.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/getList")
    public ApiResult getList(PageVO pageVO, String name){
        List<Property> list = propertyService.getList(name);
        return  OK(list);
    }

    @PostMapping("/update")
    public ApiResult update(Property property,String newPwd){
        return propertyService.changePwd(property,newPwd);
//        if(result==0){
//            ERROR(result,"出现了异常");
//        }
//        return OK(1);
    }

    @DeleteMapping("/deleteProperty")
    public ApiResult deltetPropertyById(int id){
        int result = propertyService.del(id);
        if(result==0){
                ERROR(result,"出现了异常,请退出登录重试");
        }
        return OK(1);
    }

    @PostMapping("/initPwd")
    public ApiResult initPwd(String adminAcc,String pwd,int id){
        return propertyService.initPwd(adminAcc,pwd,id);
    }
}
