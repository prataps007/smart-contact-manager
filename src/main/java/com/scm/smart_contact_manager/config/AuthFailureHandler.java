package com.scm.smart_contact_manager.config;

import com.scm.smart_contact_manager.helper.Message;
import com.scm.smart_contact_manager.helper.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        if(exception instanceof DisabledException){
            // user is disabled
            HttpSession session = request.getSession();
            session.setAttribute("message", Message.builder().content("User is disabled, Email with verification link sent to your mail")
                    .type(MessageType.red)
                    .build());

            response.sendRedirect("/login");
        }
        else{
            response.sendRedirect("/login?error=true");
        }
    }


}
