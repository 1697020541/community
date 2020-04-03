package com.jinwan.community.controller;

import com.jinwan.community.dto.AccessTokenDTO;
import com.jinwan.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
     private GithubProvider  githubProvider;
     @GetMapping("/callback")
    public  String  callback(@RequestParam(name= "code") String code,
                             @RequestParam(name="state") String state){

         AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
         accessTokenDTO.setClient_id("Iv1.0ebcbb7268abcbd0");
         accessTokenDTO.setClient_secret("a7e65a3c28d295bef11e10554d002d9d09e14e97");
         accessTokenDTO.getRedirect_uri("http://localhost:8080/callback");
         accessTokenDTO.setState(state);
         accessTokenDTO.setCode(code);

         githubProvider.getAccessToken(accessTokenDTO);
         return "index";

     }




}
