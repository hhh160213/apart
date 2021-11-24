package com.school.ex.service;


import com.school.ex.pojo.Repairer;
import com.school.ex.pojo.Words;

import java.util.List;

public interface WordsService {
    List<Words> getList();
    int del(int id);
    public int insertWord(Words words);
    List<Words> getBYnameList(String uaname);




}
