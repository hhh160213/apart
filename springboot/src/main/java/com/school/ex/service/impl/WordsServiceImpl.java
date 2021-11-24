package com.school.ex.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.school.ex.dao.ParkingDao;
import com.school.ex.dao.WordsDao;
import com.school.ex.pojo.Owner;
import com.school.ex.pojo.Parking;
import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;
import com.school.ex.service.ParkingService;
import com.school.ex.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordsServiceImpl implements WordsService {
    @Autowired
    private WordsDao wordsDao;


    @Override
    public List<Words> getList() {
//            wordsDao.selectById(1);
        List<Words> allList= wordsDao.selectList(null);
//        List<Words> wordsList = wordsDao.selectList(null);

        return allList;
    }

    @Override
    public int del(int id) {

        int result = wordsDao.deleteById(id);
        return result;
    }

    public int insertWord(Words words) {
        return wordsDao.insert(words);
    }

    @Override
    public List<Words> getBYnameList(String uaname) {
        QueryWrapper<Words> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uaname",uaname);
        List<Words>   Namelist=wordsDao.selectList(queryWrapper);
        return Namelist;



    }


}
