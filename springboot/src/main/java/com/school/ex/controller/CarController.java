package com.school.ex.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.school.ex.entity.ApiResult;
import com.school.ex.entity.PageVO;
import com.school.ex.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/car")
public class CarController extends BaseController {
    @Autowired
    private CarService carService;
    @PostMapping("/getCarInOutList")
    public ApiResult getCarInOutList(PageVO pageVO, String carNo, String isAccess){
        IPage data = carService.getCarInList(pageVO, carNo,isAccess);
        if(data==null){
            return ERROR(0,"出现错误,刷新重试");
        }
        return OK(data);
    }

    //车辆进出
    @PostMapping("/carIn")
    public ApiResult carIn(String carNo){
        String msg = carService.catInOut(carNo,1);
        if(msg==null){
            return OK(0);
        }
        return ERROR("",msg);
    }

    @PostMapping("/carOut")
    public ApiResult carOut(String carNo){
        String msg = carService.catInOut(carNo,0);
        if(msg==null){
            return OK(0);
        }
        return ERROR("",msg);
    }
}
