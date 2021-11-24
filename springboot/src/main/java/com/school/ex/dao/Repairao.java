package com.school.ex.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.ex.pojo.Property;
import com.school.ex.pojo.Repairer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Repairao extends BaseMapper<Repairer> {
}
