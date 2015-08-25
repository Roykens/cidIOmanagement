package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.ServiceException;
import java.io.IOException;
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
import javax.faces.event.ActionEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author royken
 */
@ManagedBean
@RequestScoped
public class DonneesBean {

    @ManagedProperty("#{IDonneeService}")
    private IDonneeService donneeService;
    
    private UploadedFile file;
    
    private Categorie categorie = new Categorie();
    
    private Categorie categorieChoisi = new Categorie();
    
    private List<Categorie> categories = new ArrayList<Categorie>();
    
    private Article article = new Article();
    
    private Article articleChoisi = new Article();
    
    private List<Article> articles = new ArrayList<Article>();
    
    private Long id;
    
    private String reference;

    public IDonneeService getDonneeService() {
        return donneeService;
    }

    public void setDonneeService(IDonneeService donneeService) {
        this.donneeService = donneeService;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Categorie getCategorieChoisi() {
        return categorieChoisi;
    }

    public void setCategorieChoisi(Categorie categorieChoisi) {
        this.categorieChoisi = categorieChoisi;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    
    

    public List<Categorie> getCategories() {
        try {
            categories = donneeService.findAllCategorie();
            return categories;
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Article getArticleChoisi() {
        return articleChoisi;        
    }

    public UploadedFile getFile() {
        file=null;
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    

    public void setArticleChoisi(Article articleChoisi) {
        this.articleChoisi = articleChoisi;
    }

    public List<Article> getArticles() {
        try {
            articles = donneeService.findAllArticle();
            return articles;
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String saveCategorie(){
        try {
            System.out.println("\n\n je suis " + categorie);
            donneeService.saveOrUpdateCategorie(categorie);           
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", categorie.getNom() + " a été enregistré "));
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "saveCategorie";
    }
    
    public String updateCategorie(){
        try {
            donneeService.saveOrUpdateCategorie(categorieChoisi);
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "updateCategorie";
    }
    
    public String deleteCategorie(){
        try {
            System.out.println("L'id choisi est : "  + categorieChoisi.getId());
            donneeService.deleteCategorie(categorieChoisi.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "deleteCategorie";
    }
    
    
    
    public String saveArticle(){
        try {
            Categorie cat = donneeService.findByNomenclature(reference);
            article.setCategorie(cat);
            donneeService.saveOrUpdateArticle(article);
            reference = new String();
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "saveArticle";
    }
    
    public String updateArticle(){
        try {
            Categorie cat = donneeService.findByNomenclature(reference);
            article.setCategorie(cat);
            donneeService.saveOrUpdateArticle(article);
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "updateArticle";
    }
    
    public String deleteArticle(){
        try {
            donneeService.deleteArticle(article.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "deleteArticle";
    }
    
    public void importArticle(){
        if (file != null && reference!=null ) {
            try {
                Categorie cat = donneeService.findByNomenclature(reference);
                donneeService.importArticle(file.getInputstream(), cat.getId());
                // noteService.importNotes(file.getInputstream(),idC,idE, idAca,session.ordinal());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "information","importation reussie "));
                file = null;
                reference = new String();
            } catch (IOException | ServiceException ex) {
                Logger.getLogger(DonneesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }     
    }
    
    
    
    
    /**
     * Creates a new instance of DonneesBean
     */
    public DonneesBean() {
    }
}
