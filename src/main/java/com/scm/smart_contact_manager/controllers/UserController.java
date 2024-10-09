package com.scm.smart_contact_manager.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

    // user dashboard page
    @RequestMapping(value = "/dashboard")
    public String userDashBoard(){
        System.out.println("User dashboard");
        return "user/dashboard";
    }

    // user add contacts page
    @RequestMapping(value = "/profile")
    public String userProfile(){
        System.out.println("User Profile");
        return "user/profile";
    }

    // user view contacts


}
