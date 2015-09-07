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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@ManagedBean
@RequestScoped
public class UtilisateurBean implements Serializable {

    private List<Utilisateur> utilisateurs;

    private Utilisateur user = new Utilisateur();

    private String name = "ZOZOZOZOZOZOZO";

    @ManagedProperty(value = "#{IUtilisateurService}")
    private IUtilisateurService utilisateurService;

    /**
     * Creates a new instance of UtilisateurBean
     */
    public UtilisateurBean() {

        utilisateurs = new ArrayList<Utilisateur>();
        user = new Utilisateur();
        System.out.println(utilisateurService);
    }

    public void saveOrUpdateUtilisateur() throws ServiceException {
        if (user != null && user.getId() == null) {
            utilisateurService.saveOrUpdateUtilisateur(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", user.getNom() + " a été enregistré "));
            user = new Utilisateur();
        } else if (user != null && user.getId() != null) {
            utilisateurService.saveOrUpdateUtilisateur(user);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", user.getNom() + " a été modifé "));
            user = new Utilisateur();
        }
    }

    public void deleteUser() {
        try {
            if (user != null && user.getId() != null) {
                utilisateurService.deleteUtilisateur(user.getId());
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", user.getNom() + " a été supprimé "));
            user = new Utilisateur();
        } catch (ServiceException ex) {
            Logger.getLogger(UtilisateurBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Utilisateur> getAllUtilisateur() {
        System.out.println("JE SUIS DANS LE SERVICE \n\n\n");
        try {
            System.out.println("J'EXECUTE LA REQUETE\n\n");
            utilisateurs = utilisateurService.findAll();
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
        if(user == null){
            user = new Utilisateur();
        }
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public List<Utilisateur> getUtilisateurs() {
        try {
            return utilisateurs = utilisateurService.findAll();
        } catch (ServiceException ex) {
            Logger.getLogger(UtilisateurBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
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
