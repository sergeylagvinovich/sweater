package com.example.sweater.controllers;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepos userRepos;

    @GetMapping("/registration")
    public String registration()
    {
        return  "registration";
    }

    @PostMapping("/registration")
    public String AddUser(User user, Map<String,Object> model)
    {
        User userFronDB = userRepos.findByUsername(user.getUsername());
        if(userFronDB != null)
        {
            model.put("message","User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepos.save(user);
        return "redirect:/login";
    }
}
