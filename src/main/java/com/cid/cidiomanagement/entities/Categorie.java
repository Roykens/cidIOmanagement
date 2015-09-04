package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
public class Categorie implements Serializable{
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    @Column(unique = true, nullable = false)
    private String nomenclatureSommaire;
    
    @Basic
    @Column(unique = true)
    private String nom;
    
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)
   private List<Article> articles;
    
     @Basic
    private boolean active = true;

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

    public String getNomenclatureSommaire() {
        return nomenclatureSommaire;
    }

    public void setNomenclatureSommaire(String nomenclatureSommaire) {
        this.nomenclatureSommaire = nomenclatureSommaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
   

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
    
    @Override
    public String toString() {
        return nomenclatureSommaire;
    }
    
    
    
}
