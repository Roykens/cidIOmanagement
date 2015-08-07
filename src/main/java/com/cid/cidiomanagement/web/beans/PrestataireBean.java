package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.IPrestataireService;
import com.cid.cidiomanagement.service.ServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author royken
 */
@ManagedBean
@RequestScoped
public class PrestataireBean {

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
        } catch (ServiceException ex) {
            Logger.getLogger(PrestataireBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String updatePrestataire(){
        try {
            prestataireService.saveOrUpdatePrestataire(prestataire);
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
