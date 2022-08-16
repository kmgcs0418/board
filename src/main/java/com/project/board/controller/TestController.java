package com.project.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@Slf4j
public class TestController {


    @GetMapping("/whoami")
    @Secured({"ROLE_MEMBER"})
    @ResponseBody
    public String getAuthor(@AuthenticationPrincipal User user){

        GrantedAuthority grantedAuthority = user.getAuthorities().stream().findFirst().get();

        log.info("roles {}",grantedAuthority.getAuthority());

        String username = user.getUsername();


        return username;
    }
}
