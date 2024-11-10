package com.scm.smart_contact_manager.helper;

import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

    public static void removeMessage(){

        try {
            System.out.println("removing message from session");
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");
        }
        catch (Exception e){
            System.out.println("Error in SessionHelper: " + e);
            e.printStackTrace();
        }
    }
}
