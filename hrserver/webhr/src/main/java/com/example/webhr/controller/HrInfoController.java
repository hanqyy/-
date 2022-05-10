package com.example.webhr.controller;

import com.example.webhr.mapper.HrMapper;
import com.example.webhr.model.Hr;
import com.example.webhr.model.RespBean;
import com.example.webhr.service.HrService;
import com.example.webhr.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
public class HrInfoController {
    @Autowired
    HrService hrService;

    @GetMapping("/hr/info")
    public Hr getCurrentHr(Authentication authentication){
        return (Hr) authentication.getPrincipal();
    }

    @PutMapping("/hr/info")
    public RespBean updateHr(@RequestBody Hr hr,Authentication authentication){
        if(hrService.updateHr(hr) ==1 ){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(hr,authentication.getCredentials(),authentication.getAuthorities()));
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPasswd(@RequestBody Map<String,Object> info){
        String oldpass = (String)info.get("oldpass");
        String pass = (String) info.get("pass");
        Integer hrid = (Integer) info.get("hrid");
        if(hrService.updateHrPasswd(oldpass,pass,hrid)){
            return RespBean.ok("更新成功！");
        }
        return RespBean.error("更新失败！");
    }

    @PutMapping("/hr/img")
    public RespBean updateHrImg(@RequestParam("imgName") MultipartFile imgFile, @RequestParam("hrid")Integer hrid){
        try {
            //获取原始文件名
            String originalFilename = imgFile.getOriginalFilename();
            // 找到.最后出现的位置
            int lastIndexOf = originalFilename.lastIndexOf(".");
            //获取文件后缀
            String suffix = originalFilename.substring(lastIndexOf);
            //使用UUID随机产生文件名称，防止同名文件覆盖
            String fileName = UUID.randomUUID().toString() + suffix;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功
            if(hrService.updateHrImg(fileName,hrid)){
                return RespBean.ok("头像更新成功！",fileName);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespBean.error("上传图片失败！");
    }


}
