package com.jinwan.community.controller;

import com.jinwan.community.dto.AccessTokenDTO;
import com.jinwan.community.dto.GithubUser;
import com.jinwan.community.mapper.UserMapper;
import com.jinwan.community.mode.User;
import com.jinwan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
     private GithubProvider  githubProvider;
     @Autowired
     private UserMapper userMapper;

     @GetMapping("/callback")
    public  String  callback(@RequestParam(name= "code") String code,
                             @RequestParam(name="state") String state,
                             HttpServletRequest  request){
         AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
         accessTokenDTO.setClient_id("Iv1.0ebcbb7268abcbd0");
         accessTokenDTO.setClient_secret("a7e65a3c28d295bef11e10554d002d9d09e14e97");
         accessTokenDTO.getRedirect_uri("http://localhost:8080/callback");
         accessTokenDTO.setState(state);
         accessTokenDTO.setCode(code);

         String accessToken = githubProvider.getAccessToken(accessTokenDTO);
         GithubUser githubUser = githubProvider.getUser(accessToken);
         if(githubUser !=null ){
             //登录成功
             User user=new User();
             user.setAccountId(String.valueOf(githubUser.getId()));
             user.setName(githubUser.getName());
             user.setGmtCreate(System.currentTimeMillis());
             user.setGmtModified(user.getGmtCreate());
             user.setToken(UUID.randomUUID().toString());
             System.out.println("nihao");
             System.out.println(user);
             userMapper.insert(user);
             request.getSession().setAttribute("user",user);
             return "redirect:/";
         }else {
             return "redirect:/";
         }


     }




}
