package com.scm.smart_contact_manager.helper;


import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    //@Value("${server.baseUrl}")
    private String baseUrl = "http://localhost:8081";

    public static String getEmailOfLoggedInUser(Authentication authentication){

        // if logged in with email-password
        if(authentication instanceof OAuth2AuthenticationToken){
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken)authentication;
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oAuth2User = (OAuth2User)authentication.getPrincipal();
            String username = "";

            // if logged in with google
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("getting email from google");
                username = oAuth2User.getAttribute("email").toString();

            }
            // if logged in with github
            else if(clientId.equalsIgnoreCase("github")){
                System.out.println("getting email from github");
                username = oAuth2User.getAttribute("email") != null ? oAuth2User.getAttribute("email").toString()
                        : oAuth2User.getAttribute("login").toString() + "@gmail.com";
            }

            return username;
        }
        else{
            System.out.println("getting data from local database");
            //System.out.println("user name: " + authentication.getName());
            return authentication.getName();
        }

    }

    public String getLinkForEmailVerification(String emailToken){
        return this.baseUrl + "/auth/verify-email?token=" + emailToken;
    }

}
