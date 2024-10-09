package com.scm.smart_contact_manager.controllers;

import com.scm.smart_contact_manager.entities.User;
import com.scm.smart_contact_manager.forms.UserForm;
import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import com.scm.smart_contact_manager.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String Home(Model model){
        System.out.println("Home page handler");
        model.addAttribute("name","Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code with Durgesh");
        model.addAttribute("githubRepo","xyz");
        return "home";
    }

    // about
    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin",true);
        System.out.println("About page handler");
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services page handler");
        return "services";
    }
    

    // services
    @RequestMapping("/contact")
    public String contactPage(){
        System.out.println("Contact page handler");
        return "contact";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.println("Login page handler");
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        System.out.println("Register handler");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);

        return "register";
    }

    // processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){
        System.out.println("Processing registration");

        // validate the form
        if(rBindingResult.hasErrors()){
            return "register";
        }

//        User user = User.builder()
//                .name(userForm.getName())
//                .email(userForm.getEmail())
//                .password(userForm.getPassword())
//                .about(userForm.getAbout())
//                .phoneNumber(userForm.getPhoneNumber())
//                .profilePic("")
//                .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setEnabled(true);
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(user.getProfilePic());

        User saved = userService.saveUser(user);

        System.out.println(saved);

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", "Registration Successful");

        return "redirect:/register";
    }

}
