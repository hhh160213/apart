package com.school.ex.controller;

import com.school.ex.entity.ApiResult;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Parking;
import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;
import com.school.ex.service.ParkingService;
import com.school.ex.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/words")
public class WordsController extends BaseController {

    @Autowired
    private WordsService wordsService;
    @PostMapping("/getList")
    public ApiResult getList(){
        List<Words> allList= wordsService.getList();
        return OK(allList);
    }

    @DeleteMapping("/deleteWords")
    public ApiResult deltetPropertyById(int id){
        int result = wordsService.del(id);
        if(result==0){
            ERROR(result,"出现了异常,请退出登录重试");
        }
        return OK(1);
    }

    @PostMapping("/insertword")
    public ApiResult insertWord(Words words){
        int result = wordsService.insertWord(words);
        if (result==0){
            return ERROR(0,"该留言已存在");
        }
        return OK(1);
    }

    @PostMapping("/getsingleWord")
    public ApiResult getList(String uaname){
        List<Words> namelist = wordsService.getBYnameList(uaname);
        return  OK(namelist);
    }


}
