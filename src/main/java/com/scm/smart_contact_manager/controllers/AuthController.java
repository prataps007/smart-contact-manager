package com.scm.smart_contact_manager.controllers;

import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import com.scm.smart_contact_manager.repositories.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    //  verify email
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token,
                              HttpSession httpSession){

        User user = userRepo.findByEmailToken(token).orElse(null);
        if(user != null){
            // user fetched
            if(user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);

                httpSession.setAttribute("message", Message.builder()
                        .type(MessageType.green)
                        .content("Your email is verified ! Now you can login")
                        .build());

                return "success_page";
            }

            httpSession.setAttribute("message", Message.builder()
                    .type(MessageType.red)
                    .content("Email not verified ! Token is not associated with user")
                    .build());

            return "error_page";
        }

        httpSession.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email not verified ! Token is not associated with user")
                .build());

        System.out.println("Verify Email");
        return "error_page";
    }
}
