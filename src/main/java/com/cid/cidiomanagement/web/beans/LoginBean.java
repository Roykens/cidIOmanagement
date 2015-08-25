package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.service.IUtilisateurService;
import com.cid.cidiomanagement.service.ServiceException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
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
@SessionScoped
public class LoginBean implements Serializable {

    private String userName = "";
    private String password = "";

    @ManagedProperty(value = "#{authenticationManager}")
    private AuthenticationManager authenticationManager;

    @ManagedProperty(value = "#{IUtilisateurService}")
    private IUtilisateurService utilisateurService;

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

    public IUtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(IUtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public String login() {
        try {
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
            boolean result = utilisateurService.findUtilisateurByLoginAndPassword(userName, password);
            if (result) {
                HttpSession session = Util.getSession();
                session.setAttribute("username", userName);
                return "correct";
            } else {
                FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Identifiants Invalides!",
                                "Veuillez reéssayer!"));
            }

        } catch (ServiceException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "incorrect";
    }

    public String cancel() {
        return null;
    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "login";
    }

//    public String logout(){
//        SecurityContextHolder.clearContext();
//        return "loggedout";
//    }
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

}
