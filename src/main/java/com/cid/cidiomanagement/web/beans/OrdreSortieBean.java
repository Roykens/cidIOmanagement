package com.cid.cidiomanagement.web.beans;

import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.service.IOrdreSortieService;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@ManagedBean
@SessionScoped
public class OrdreSortieBean implements Serializable {

    @ManagedProperty(value = "#{IOrdreSortieService}")
    private IOrdreSortieService sortieService;
    
    private Affectation affectation = new Affectation();
    
    private List<Affectation> affectations = new ArrayList<>();
    
    private BonSortie bonSortie = new BonSortie();
    
    private List<BonSortie> bonSorties = new ArrayList<>();
    
    private OrdreSortie ordreSortie = new OrdreSortie();
    
    private List<OrdreSortie> ordreSorties = new ArrayList<>();
    
    private List<Personnel> personnels = new ArrayList<>();
    
    private List<Article> arcArticles = new ArrayList<>();
    
    private Date dateOrdre;
    
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
        return bonSortie;
    }

    public void setBonSortie(BonSortie bonSortie) {
        this.bonSortie = bonSortie;
    }

    public OrdreSortie getOrdreSortie() {
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
        return bonSorties;
    }

    public void setBonSorties(List<BonSortie> bonSorties) {
        this.bonSorties = bonSorties;
    }

    public List<OrdreSortie> getOrdreSorties() {
        return ordreSorties;
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
        return personnels;
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
        return arcArticles;
    }

    public void setArcArticles(List<Article> arcArticles) {
        this.arcArticles = arcArticles;
    }
    
    
    
    
    
}
