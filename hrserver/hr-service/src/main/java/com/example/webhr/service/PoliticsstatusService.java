package com.example.webhr.service;

import com.example.webhr.mapper.PoliticsstatusMapper;
import com.example.webhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticsstatusService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper. getAllPoliticsstatus();
    }

}
