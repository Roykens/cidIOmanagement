package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.Min;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
public class Commande implements Serializable{
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    private int nombre;
    
    @Basic
    @Min(0)
    private int prixArticle;
    
    @ManyToOne
    private Article article;
    
    @ManyToOne
    private BonCommande bonCommande;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public BonCommande getBonCommande() {
        return bonCommande;
    }

    public void setBonCommande(BonCommande bonCommande) {
        this.bonCommande = bonCommande;
    }

    public int getPrixArticle() {
        return prixArticle;
    }

    public void setPrixArticle(int prixArticle) {
        this.prixArticle = prixArticle;
    }

    
    public String getCategorie(){
        return article.getCategorie().getNomenclatureSommaire();
    }
    
    @Override
    public String toString() {
        return "Commande{"+"categorie="+article.getCategorie().getNomenclatureSommaire() + "nombre=" + nombre + ", article=" + article.getDesignation() + '}';
    }
    
    
}
