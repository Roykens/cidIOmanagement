package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Utilisateur;
import com.cid.cidiomanagement.service.IUtilisateurService;
import com.cid.cidiomanagement.service.ServiceException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@ManagedBean
@SessionScoped
public class UtilisateurBean implements Serializable {

    private List<Utilisateur> utilisateurs;
    
    private Utilisateur user = new Utilisateur();
    
    private String name = "ZOZOZOZOZOZOZO";
    
     @ManagedProperty(value="#{IUtilisateurService}")
    private IUtilisateurService utilisateurService;
    /**
     * Creates a new instance of UtilisateurBean
     */
    public UtilisateurBean() {
        
        utilisateurs = new ArrayList<Utilisateur>();
        System.out.println(utilisateurService);
    }
    
    public void saveUtilisateur() throws ServiceException{
        utilisateurService.saveOrUpdateUtilisateur(user);
    }
    
    public List<Utilisateur> getAllUtilisateur(){
        System.out.println("JE SUIS DANS LE SERVICE \n\n\n");
         try {
             System.out.println("J'EXECUTE LA REQUETE\n\n");
             utilisateurs =  utilisateurService.findAll();
             for (Utilisateur utilisateur : utilisateurs) {
                 System.out.println(utilisateur);
             }
             return utilisateurs;
         } catch (ServiceException ex) {
             Logger.getLogger(UtilisateurBean.class.getName()).log(Level.SEVERE, null, ex);
         }
         return Collections.EMPTY_LIST;
    }

    public IUtilisateurService getUtilisateurService() {
        return utilisateurService;
    }

    public void setUtilisateurService(IUtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
    
    

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
   
    
    
}
