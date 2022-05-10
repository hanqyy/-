package com.example.webhr.service;

import com.example.webhr.mapper.HrRoleMapper;
import com.example.webhr.model.Hr;
import com.example.webhr.mapper.HrMapper;
import com.example.webhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HrService implements UserDetailsService {
    @Autowired
     HrMapper hrMapper;

    @Autowired
    HrRoleMapper hrRoleMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hr hr = hrMapper.loadUserByUsername(username);
        if(hr == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    public int updateHr(Hr hr) {
       return hrMapper.updateByPrimaryKeySelective(hr);
    }

    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    public int deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }

    public boolean updateHrPasswd(String oldpass, String pass, Integer hrid) {
        Hr hr = hrMapper.selectByPrimaryKey(hrid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
       if(encoder.matches(oldpass,hr.getPassword())){
           //与原密码匹配正确
           String encodePass = encoder.encode(pass);
           Integer result = hrMapper.updatePasswd(hrid,encodePass);
           if(result == 1){
               return true;
           }
       }
       return false;
    }

    public boolean updateHrImg(String fileName, Integer hrid) {
        String qiImg = "http://ra5nt7qh2.hn-bkt.clouddn.com/" + fileName;
        return hrMapper.updateHrImg(qiImg,hrid);
    }
}
