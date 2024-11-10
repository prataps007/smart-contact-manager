package com.scm.smart_contact_manager.controllers;

import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.Helper;
import com.scm.smart_contact_manager.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInFormation(Model model, Authentication authentication){
        if(authentication==null){
            return;
        }

        System.out.println("Adding logged in user information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);

        User user = userService.getUserByEmail(username);
        System.out.println("User(RootController): " + user);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println("User Profile");
        model.addAttribute("loggedInUser",user);
    }
}
