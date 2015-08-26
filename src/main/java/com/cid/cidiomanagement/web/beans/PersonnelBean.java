package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.entities.Service;
import com.cid.cidiomanagement.service.IPersonnelService;
import com.cid.cidiomanagement.service.ServiceException;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@ManagedBean
@SessionScoped
public class PersonnelBean implements Serializable {
    
    @ManagedProperty(value = "#{IPersonnelService}")
    private IPersonnelService personnelService;
    
    private Personnel personne = new Personnel();
    
    private List<Personnel> personnels = new ArrayList<>();
    
    private Service service = new Service();
    
    private List<Service> services = new ArrayList<>();
    
    private String codeService;
    
    public IPersonnelService getPersonnelService() {
        return personnelService;
    }
    
    public void setPersonnelService(IPersonnelService personnelService) {
        this.personnelService = personnelService;
    }
    
    public Personnel getPersonne() {
        if(personne == null){
            personne = new Personnel();
        }
        return personne;
    }
    
    public void setPersonne(Personnel personne) {
        this.personne = personne;
    }
    
    public Service getService() {
        if(service == null){
            service = new Service();
        }
        return service;
    }
    
    public void setService(Service service) {
        this.service = service;
    }
    
    public List<Personnel> getPersonnels() {
        try {
            personnels = personnelService.findAllPersonnel();
            return personnels;
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
    public void setPersonnels(List<Personnel> personnels) {
        this.personnels = personnels;
    }
    
    public List<Service> getServices() {
        try {
            services = personnelService.findAllService();
            return services;
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
    public void setServices(List<Service> services) {
        this.services = services;
    }
    
    public String getCodeService() {
        return codeService;
    }
    
    public void setCodeService(String codeService) {
        this.codeService = codeService;
    }

    /**
     * Creates a new instance of PersonnelBean
     */
    public PersonnelBean() {
    }
    
    public String savePersonnel() {
        try {
            Service s = personnelService.findbyCode(codeService);
            if (s != null) {
                personne.setService(s);
                personnelService.saveOrUpdatePersonnel(personne);
            }
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String updatePersonnel() {
        try {
            personnelService.saveOrUpdatePersonnel(personne);
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String deletePersonnel() {
        try {
            personnelService.deletePersonnel(personne.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String saveService(){
        System.out.println("\n\nJ'enregistre 1=========================");
        try {
            System.out.println("\n\nJ'enregistre=========================");
            System.out.println(service);
            personnelService.saveOrUpdateService(service);
            //service = new Service();
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String updateService(){
        try {
            personnelService.saveOrUpdateService(service);
            service = new Service();
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String deleteService(){
        try {
            personnelService.deleteService(service.getId());
            service = new Service();
        } catch (ServiceException ex) {
            Logger.getLogger(PersonnelBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
}
