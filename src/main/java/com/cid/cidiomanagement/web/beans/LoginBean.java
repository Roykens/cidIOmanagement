package com.cid.cidiomanagement.web.beans;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@ManagedBean
@RequestScoped
public class LoginBean implements Serializable{
    
    private String userName = ""; 
    private String password = "";
    
    @ManagedProperty(value="#{authenticationManager}")
    private AuthenticationManager authenticationManager;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    
    public String login() {
//        try {
//            
//            Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(), this.getPassword());
//            Authentication result = authenticationManager.authenticate(request);
//            SecurityContextHolder.getContext().setAuthentication(result);
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//            
//            return "incorrect";
//        }
        
        return "correct";
    }
    
     public String cancel() {
        return null;
    }

    public String logout(){
        SecurityContextHolder.clearContext();
        return "loggedout";
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    
}
