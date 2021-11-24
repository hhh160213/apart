package com.school.ex.controller;

import com.school.ex.entity.ApiResult;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Property;
import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;
import com.school.ex.service.RepairService;
import com.school.ex.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/repair")
public class RepairController extends BaseController {

    @Autowired
    private RepairService repairService;
    @PostMapping("/getList")
    public ApiResult getList(){
        List<Repairer> allList= repairService.getList();
        return OK(allList);
    }
    @PostMapping("/getbynameList")
    public ApiResult getList(String name){
        List<Repairer> namelist = repairService.getBYnameList(name);
        return  OK(namelist);
    }


    @PostMapping("/insertrepair")
    public ApiResult insertWord(Repairer repairer){
        int result = repairService.insertOwner(repairer);
        if (result==0){
            return ERROR(0,"该报修已存在");
        }
        return OK(1);
    }

    @PostMapping("/repairhandle")
    public ApiResult repairhandle(int id, String complete){
        int result = repairService.repairhandle(id, complete);
        if (result==0){
            return ERROR(0,"出现问题,刷新重试");
        }
        return OK(1);
    }



}
