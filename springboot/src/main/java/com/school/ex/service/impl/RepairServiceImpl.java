package com.school.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.school.ex.dao.OwnerDao;
import com.school.ex.dao.PropertyDao;
import com.school.ex.dao.Repairao;
import com.school.ex.entity.PageVO;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Property;
import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;
import com.school.ex.service.IndexService;
import com.school.ex.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {

    @Autowired
    private Repairao repairao;


    @Override
    public int insertOwner(Repairer repairer) {
        return repairao.insert(repairer);
    }

    @Override
    public List<Repairer> getList() {
        List<Repairer> allList= repairao.selectList(null);
        return allList;
    }

    @Override
    public List<Repairer> getBYnameList(String name) {
        QueryWrapper<Repairer> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("name",name);
         List<Repairer>   Namelist=repairao.selectList(queryWrapper);
         return Namelist;

    }

    @Override
    public int repairhandle(int id, String complete) {
        Repairer r = new Repairer();
        r.setId(id);
        r.setComplete(complete);
        return repairao.updateById(r);



    }

    @Override
    public int del(int id) {
        int result = repairao.deleteById(id);
        return result;
    }


}
