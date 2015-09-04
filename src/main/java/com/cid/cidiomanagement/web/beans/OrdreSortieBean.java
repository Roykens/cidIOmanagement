package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.EtatType;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.IOrdreSortieService;
import com.cid.cidiomanagement.service.IPersonnelService;
import com.cid.cidiomanagement.service.ServiceException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@ManagedBean
@SessionScoped
public class OrdreSortieBean implements Serializable{

    @ManagedProperty(value = "#{IOrdreSortieService}")
    private IOrdreSortieService sortieService;

    @ManagedProperty(value = "#{IPersonnelService}")
    private IPersonnelService personnelService;
    
    @ManagedProperty(value = "#{IDonneeService}")
    private IDonneeService donneeService;

    private Affectation affectation = new Affectation();

    private List<Affectation> affectations = new ArrayList<>();

    private BonSortie bonSortie = new BonSortie();

    private List<BonSortie> bonSorties = new ArrayList<>();

    private OrdreSortie ordreSortie = new OrdreSortie();

    private List<OrdreSortie> ordreSorties = new ArrayList<>();

    private List<Personnel> personnels = new ArrayList<>();

    private List<Article> arcArticles = new ArrayList<>();

    private Date dateOrdre;
    
    private String objet;
    
    private int noChapitre;
    
    private int noOrdre;
    
    private String dateO;

    private String nomArticle;

    private String nomPersonnel;

    /**
     * Creates a new instance of OrdreSortieBean
     */
    public OrdreSortieBean() {
    }

    public IOrdreSortieService getSortieService() {
        return sortieService;
    }

    public void setSortieService(IOrdreSortieService sortieService) {
        this.sortieService = sortieService;
    }

    public Affectation getAffectation() {
        return affectation;
    }

    public void setAffectation(Affectation affectation) {
        this.affectation = affectation;
    }

    public BonSortie getBonSortie() {
        if(bonSortie == null){
            bonSortie = new BonSortie();
        }
        return bonSortie;
    }

    public void setBonSortie(BonSortie bonSortie) {
        this.bonSortie = bonSortie;
    }

    public OrdreSortie getOrdreSortie() {
        if(ordreSortie == null){
            ordreSortie = new OrdreSortie();
        }
        return ordreSortie;
    }

    public void setOrdreSortie(OrdreSortie ordreSortie) {
        this.ordreSortie = ordreSortie;
    }

    public List<Affectation> getAffectations() {
        return affectations;
    }

    public void setAffectations(List<Affectation> affectations) {
        this.affectations = affectations;
    }

    public List<BonSortie> getBonSorties() {
        try {
            bonSorties = sortieService.findAllBonSortie();
            return bonSorties;
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setBonSorties(List<BonSortie> bonSorties) {
        this.bonSorties = bonSorties;
    }

    public List<OrdreSortie> getOrdreSorties() {
        try {
            ordreSorties = sortieService.findAllOrdre();
            return ordreSorties;
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setOrdreSorties(List<OrdreSortie> ordreSorties) {
        this.ordreSorties = ordreSorties;
    }

    public Date getDateOrdre() {
        return dateOrdre;
    }

    public void setDateOrdre(Date dateOrdre) {
        this.dateOrdre = dateOrdre;
    }

    public List<Personnel> getPersonnels() {

        try {
            personnels = personnelService.findAllPersonnel();
            return personnels;
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setPersonnels(List<Personnel> personnels) {
        this.personnels = personnels;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getNomPersonnel() {
        return nomPersonnel;
    }

    public void setNomPersonnel(String nomPersonnel) {
        this.nomPersonnel = nomPersonnel;
    }

    public List<Article> getArcArticles() {
        try {
            arcArticles = donneeService.findAllArticle();
            return arcArticles;
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setArcArticles(List<Article> arcArticles) {
        this.arcArticles = arcArticles;
    }

    public IPersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(IPersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    public IDonneeService getDonneeService() {
        return donneeService;
    }

    public void setDonneeService(IDonneeService donneeService) {
        this.donneeService = donneeService;
    }

    public String getDateO() {
        return dateO;
    }

    public void setDateO(String dateO) {
        this.dateO = dateO;
    }
    
    
    
    public void addAffectation(){
        try {
            Article a = donneeService.findByDesignation(nomArticle);
            affectation.setArticle(a);
            affectations.add(affectation);
            System.out.println("\n\n ============    J'ai exactement " + affectations.size() + " articles\n\n");
            affectation = new Affectation();
            for (Affectation affectation1 : affectations) {
                System.out.println(affectation1);
            }
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public String saveOrdre() {
        try {
            ordreSortie.setEtatType(EtatType.Encour);
            sortieService.saveOrUpdateOrdre(ordreSortie);
            ordreSortie = new OrdreSortie();
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }

    public String updateOrdre() {
        try {
            sortieService.saveOrUpdateOrdre(ordreSortie);
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String deleteOrdre() {
        try {
            sortieService.deletOrdre(ordreSortie.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String deleteBon() {
        try {
            sortieService.deletOrdre(bonSortie.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }

    public String saveBonSortie() {
        try {
            Personnel p = personnelService.findPersonnelByNom(nomPersonnel);
            if (p != null) {
                bonSortie.setPersonnel(p);
            }
            
            OrdreSortie o = sortieService.findByDateString(dateO);
            System.out.println("===================  L'ordre de sortie est =======    "+ o);
            if(o != null){
                System.out.println("\n\nJ'ai l'ordre =====================   " + o);
                bonSortie.setOrdeSortie(o);
            }
            sortieService.saveOrUpdateBonSortie(bonSortie);
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }

    public String updateBonSortie() {
        try {
            sortieService.saveOrUpdateBonSortie(bonSortie);
        } catch (ServiceException ex) {
            Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "succes";
    }
    
    public String finirBon(){
        for (Affectation affectation1 : affectations) {
            try {
                affectation1.setBonSortie(bonSortie);
                sortieService.saveOrUpdateAffectation(affectation1);
            } catch (ServiceException ex) {
                Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        affectations = new ArrayList<>();
        return "";
    }
    
    public void produceBs(){
        System.out.println("Je suis ici \n\n\n");
        System.out.println(bonSortie);
        FacesContext context = FacesContext.getCurrentInstance();

        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-Disposition", "attachment; filename=pv.pdf");
                
               // commandeService.produceTrash(bonCommande.getId(), "Commande de logiciels" ,((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                sortieService.imprimerBsp(bonSortie.getId(), ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                context.responseComplete();
            } catch (IOException ex) {
                Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServiceException ex) {
                Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   public void produceOrdreSortie(){
        System.out.println("Je suis ici \n\n\n");
        System.out.println(bonSortie);
        FacesContext context = FacesContext.getCurrentInstance();

        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-Disposition", "attachment; filename=pv.pdf");
                
               // commandeService.produceTrash(bonCommande.getId(), "Commande de logiciels" ,((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
               // sortieService.imprimerBsp(bonSortie.getId(), ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                sortieService.imprimerOrdreSortie(ordreSortie.getId(), ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream(), dateOrdre, noChapitre, noOrdre, objet);
                context.responseComplete();
            } catch (IOException ex) {
                Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServiceException ex) {
                Logger.getLogger(OrdreSortieBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public int getNoChapitre() {
        return noChapitre;
    }

    public void setNoChapitre(int noChapitre) {
        this.noChapitre = noChapitre;
    }

    public int getNoOrdre() {
        return noOrdre;
    }

    public void setNoOrdre(int noOrdre) {
        this.noOrdre = noOrdre;
    }
   
   

}
