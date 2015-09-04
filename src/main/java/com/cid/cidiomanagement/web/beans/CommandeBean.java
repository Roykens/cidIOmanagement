package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.ICommandeService;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.IPrestataireService;
import com.cid.cidiomanagement.service.ServiceException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author royken
 */
@ManagedBean
@SessionScoped
public class CommandeBean implements Serializable {

    @ManagedProperty("#{ICommandeService}")
    private ICommandeService commandeService;
    @ManagedProperty("#{IDonneeService}")
    private IDonneeService donneeService;
    @ManagedProperty("#{IPrestataireService}")
    private IPrestataireService prestataireService;
    private Commande commande = new Commande();
    private BonCommande bonCommande;
    private Article article = new Article();
    private Prestataire prestataire = new Prestataire();
    private String nomPrestataire;
    private String nomArticle;
    private List<Commande> commandes = new ArrayList<>();
    private List<BonCommande> bonCommandes = new ArrayList<>();
    private List<Article> articles = new ArrayList<>();
    private List<Prestataire> prestataires = new ArrayList<>();
    String noFacture = new String();
    Date dateFacture = new Date();
    int noOrdreEntree;
    int noChapitre;

    /**
     * Creates a new instance of CommandeBean
     */
    public CommandeBean() {
        bonCommande = new BonCommande();
       
    }

    @PostConstruct
    public void init() {
        //users = new Iuser();
        bonCommande = new BonCommande();
    }

    public ICommandeService getCommandeService() {
        return commandeService;
    }

    public void setCommandeService(ICommandeService commandeService) {
        this.commandeService = commandeService;
    }

    public IDonneeService getDonneeService() {
        return donneeService;
    }

    public void setDonneeService(IDonneeService donneeService) {
        this.donneeService = donneeService;
    }

    public IPrestataireService getPrestataireService() {
        return prestataireService;
    }

    public void setPrestataireService(IPrestataireService prestataireService) {
        this.prestataireService = prestataireService;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public BonCommande getBonCommande() {
         if(bonCommande == null){
            bonCommande = new BonCommande();
                   // (BonCommande)super.getInstance(BonCommande.class);
        }
        return bonCommande;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public String getNomPrestataire() {
        return nomPrestataire;
    }

    public void setNomPrestataire(String nomPrestataire) {
        this.nomPrestataire = nomPrestataire;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }

    public String getNoFacture() {
        return noFacture;
    }

    public void setNoFacture(String noFacture) {
        this.noFacture = noFacture;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public List<BonCommande> getBonCommandes() {
        try {
            bonCommandes = commandeService.findAllCommande();
            return bonCommandes;
        } catch (ServiceException ex) {
            Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setBonCommandes(List<BonCommande> bonCommandes) {
        this.bonCommandes = bonCommandes;
    }

    public List<Article> getArticles() {
        try {
            articles = donneeService.findAllArticle();
            return articles;
        } catch (ServiceException ex) {
            Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Prestataire> getPrestataires() {
        try {
            prestataires = prestataireService.findAllPrestataire();
            return prestataires;
        } catch (ServiceException ex) {
            Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setPrestataires(List<Prestataire> prestataires) {
        this.prestataires = prestataires;
    }

    public void addCommande() {
        try {
            Article art = donneeService.findByDesignation(nomArticle);
            System.out.println("J'ajoute l'article    : " + art);
            commande.setArticle(art);

            commandes.add(commande);
            commande = new Commande();

//            System.out.println("Ma liste d'articles");
//            for (Commande commande1 : commandes) {
//                System.out.println(commande1);
//            }
        } catch (ServiceException ex) {
            Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toto() {
        System.out.println("Le bon de Commande \n\n\n\n");
        System.out.println(bonCommande);
    }

    public void finirBon() {

        for (Commande commande1 : commandes) {
            try {
                commande1.setBonCommande(bonCommande);
                commandeService.saveOrUpdateCommande(commande1);
            } catch (ServiceException ex) {
                Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        commandes = new ArrayList<>();
        bonCommande = new BonCommande();
    }

    public String saveBonCommande() {
        try {
            System.out.println("Le bon de commande : ");
            System.out.println(bonCommande);
            Prestataire prestataire1 = prestataireService.findByNom(nomPrestataire);
            System.out.println(prestataire1);
            bonCommande.setPrestataire(prestataire1);
            System.out.println(bonCommande);
            commandeService.saveOrUpdateBon(bonCommande);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", bonCommande.getObjet() + " a été enregistré "));
            bonCommande = new BonCommande();
        } catch (ServiceException ex) {
            Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "success";
    }

    public void procesVerbal() throws ServiceException, FileNotFoundException {

        System.out.println("Je suis ici \n\n\n");
        System.out.println(bonCommande);
        FacesContext context = FacesContext.getCurrentInstance();

        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-Disposition", "attachment; filename=pv.pdf");
                commandeService.produceTrash(bonCommande.getId(), "Commande de logiciels", ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());

                context.responseComplete();
            } catch (IOException ex) {
                Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void produireOrdreEntree() {
        System.out.println("Je suis ici \n\n\n");
        System.out.println(bonCommande);
        FacesContext context = FacesContext.getCurrentInstance();

        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-Disposition", "attachment; filename=ordreEntree.pdf");

                // commandeService.produceTrash(bonCommande.getId(), "Commande de logiciels" ,((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                commandeService.produireOrdreEntree(bonCommande.getId(), ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream(), noFacture, dateFacture, noOrdreEntree, noChapitre);
                context.responseComplete();
            } catch (IOException | ServiceException ex) {
                Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getNoOrdreEntree() {
        return noOrdreEntree;
    }

    public void setNoOrdreEntree(int noOrdreEntree) {
        this.noOrdreEntree = noOrdreEntree;
    }

    public int getNoChapitre() {
        return noChapitre;
    }

    public void setNoChapitre(int noChapitre) {
        this.noChapitre = noChapitre;
    }
    
    

}
