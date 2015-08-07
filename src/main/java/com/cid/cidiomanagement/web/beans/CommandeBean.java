package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.ICommandeService;
import com.cid.cidiomanagement.service.IDonneeService;
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
import javax.faces.bean.SessionScoped;

/**
 *
 * @author royken
 */
@ManagedBean
@SessionScoped
public class CommandeBean {
    
    @ManagedProperty("#{ICommandeService}")
    private ICommandeService commandeService;
    
    @ManagedProperty("#{IDonneeService}")
    private IDonneeService donneeService;
    
    @ManagedProperty("#{IPrestataireService}")
    private IPrestataireService prestataireService;
    
    private Commande commande = new Commande();
    
    private BonCommande bonCommande = new BonCommande();
    
    private Article article = new Article();
    
    private Prestataire prestataire = new Prestataire();
    
    private String nomPrestataire;
    
    private String nomArticle;
    
    
    private List<Commande> commandes = new ArrayList<Commande>();
    
    private List<BonCommande> bonCommandes = new ArrayList<BonCommande>();
    
    private List<Article> articles = new ArrayList<Article>();
    
    private List<Prestataire> prestataires = new ArrayList<Prestataire>();

    /**
     * Creates a new instance of CommandeBean
     */
    public CommandeBean() {
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
    
    public List<Commande>getTrash(){
        Article art = new Article();
        art.setDesignation("un article");
        Commande com = new Commande();
        com.setNombre(4);
        com.setArticle(art);
        List<Commande> coms = new ArrayList<Commande>();
        coms.add(com);
        coms.add(com);
        coms.add(com);
        coms.add(com);
        coms.add(com);
        return coms;
    }
    
    

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public BonCommande getBonCommande() {
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
    
    public void addCommande(){
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
    
    public void toto(){
        System.out.println("Le bon de Commande \n\n\n\n");
        System.out.println(bonCommande);
    }
    
    public void finirBon(){
        
        
        
        for (Commande commande1 : commandes) {
            try {
                commande1.setBonCommande(bonCommande);
                commandeService.saveOrUpdateCommande(commande1);
            } catch (ServiceException ex) {
                Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String saveBonCommande(){
        try {
            Prestataire prestataire1 = prestataireService.findByNom(nomPrestataire);
            System.out.println(prestataire1);
            bonCommande.setPrestataire(prestataire1);
            commandeService.saveOrUpdateBon(bonCommande);
        } catch (ServiceException ex) {
            Logger.getLogger(CommandeBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "success";
    }
}
