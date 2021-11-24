package com.school.ex.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.school.ex.entity.ApiResult;

public class BaseController {
    public ApiResult OK(Object data){
        return new ApiResult(data);
    }
    public ApiResult OK(IPage page){
        return new ApiResult(page.getTotal(),page.getRecords());
    }
    public ApiResult ERROR(Object data,String msg){
        return new ApiResult(data,msg,500);
    }
}
