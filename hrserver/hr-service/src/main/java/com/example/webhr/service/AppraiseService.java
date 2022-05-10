package com.example.webhr.service;

import com.example.webhr.mapper.AppraiseMapper;
import com.example.webhr.model.Appraise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppraiseService {
    @Autowired
    AppraiseMapper appraiseMapper;

    public int deleteApp(Integer id) {
        return appraiseMapper.deleteByPrimaryKey(id);
    }

    public int addApp(Appraise appraise) {
        return appraiseMapper.insertSelective(appraise);
    }

    public int updateApp(Appraise appraise) {
        return appraiseMapper.updateByPrimaryKeySelective(appraise);
    }
}
