package com.school.ex.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.ex.pojo.Car_inout;
import com.school.ex.pojo.Car_no;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;


@Mapper
@Repository
public interface CarInDao extends BaseMapper<Car_inout> {
}
