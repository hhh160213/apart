package com.school.ex.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class ApiResult {
    public ApiResult(Object data) {
        Data = data;
        meta=new Meta();
    }
    public ApiResult( long total,Object data) {
        this.total=total;
        Data = data;
        meta=new Meta();
    }

    public ApiResult(Object data, String msg,int status) {
        Data = data;
        this.meta = new Meta(msg,status);
    }

    private Object Data;
    private Meta meta;
    private int page=1;
    private long total;
    private int limit=10;
}
