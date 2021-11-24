package com.school.ex.controller;

import com.school.ex.entity.ApiResult;
import com.school.ex.pojo.Parking;
import com.school.ex.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController extends BaseController {

    @Autowired
    private ParkingService parkingService;

    @PostMapping("/getList")
    public ApiResult getList(String id, String owner, String address){
        List<Parking> list = parkingService.getList(id, owner, address);
        return OK(list);
    }

    @PostMapping("/update")
    public ApiResult update(Parking parking){
        int count = parkingService.update(parking);
        if(count==0){
            return ERROR(0,"修改/添加失败");
        }
        return OK(1);
    }

}
