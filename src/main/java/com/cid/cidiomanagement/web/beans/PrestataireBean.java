package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.IPrestataireService;
import com.cid.cidiomanagement.service.ServiceException;
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
 * @author royken
 */
@ManagedBean
@SessionScoped
public class PrestataireBean implements Serializable{

    @ManagedProperty("#{IPrestataireService}")
    private IPrestataireService prestataireService;
    
    private Prestataire prestataire = new Prestataire();
    
    private List<Prestataire> prestataires = new ArrayList<Prestataire>();
    
    
    /**
     * Creates a new instance of PrestataireBean
     */
    public PrestataireBean() {
    }

    public IPrestataireService getPrestataireService() {
        return prestataireService;
    }

    public void setPrestataireService(IPrestataireService prestataireService) {
        this.prestataireService = prestataireService;
    }

    public Prestataire getPrestataire() {
        if(prestataire == null){
            prestataire = new Prestataire();
        }
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public List<Prestataire> getPrestataires() {
        try {
            prestataires = prestataireService.findAllPrestataire();
            return prestataires;
        } catch (ServiceException ex) {
            Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setPrestataires(List<Prestataire> prestataires) {
        this.prestataires = prestataires;
    }
    
    
    public String savePrestataire(){
        try {
            prestataireService.saveOrUpdatePrestataire(prestataire);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", prestataire.getNom() + " a été enregistré "));
        } catch (ServiceException ex) {
            Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String updatePrestataire(){
        try {
            prestataireService.saveOrUpdatePrestataire(prestataire);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", prestataire.getNom() + " a été modifié "));
        } catch (ServiceException ex) {
            Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String deletePrestataire(){
        try {
            prestataireService.deletePrestataire(prestataire.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
}
