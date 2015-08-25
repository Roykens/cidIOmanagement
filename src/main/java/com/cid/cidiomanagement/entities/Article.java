package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.validation.constraints.Min;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
public class Article implements Serializable{
    
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    @Column(unique = true, nullable = false)
    private String reference;
    
    @Basic
    private String designation;
    
    @Basic
    private String conditionnement;
    
    @Basic
    @Min(0)
    private Double prixUnitaire;
    
    @Basic
    @Min(0)
    private Integer quantite;
    
    @ManyToOne
    private Categorie categorie;
    
    @OneToOne(mappedBy = "article")
    private Affectation affectation;

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getConditionnement() {
        return conditionnement;
    }

    public void setConditionnement(String conditionnement) {
        this.conditionnement = conditionnement;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Affectation getAffectation() {
        return affectation;
    }

    public void setAffectation(Affectation affectation) {
        this.affectation = affectation;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }
    
    

//    @Override
//    public String toString() {
//        return  designation;
//    }
    
    

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", reference=" + reference + ", designation=" + designation + ", conditionnement=" + conditionnement + ", prixUnitaire=" + prixUnitaire + ", quantite=" + quantite + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.version;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.reference);
        hash = 37 * hash + Objects.hashCode(this.designation);
        hash = 37 * hash + Objects.hashCode(this.conditionnement);
        hash = 37 * hash + Objects.hashCode(this.prixUnitaire);
        hash = 37 * hash + Objects.hashCode(this.quantite);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.reference, other.reference)) {
            return false;
        }
        if (!Objects.equals(this.designation, other.designation)) {
            return false;
        }
        if (!Objects.equals(this.conditionnement, other.conditionnement)) {
            return false;
        }
        if (!Objects.equals(this.prixUnitaire, other.prixUnitaire)) {
            return false;
        }
        if (!Objects.equals(this.quantite, other.quantite)) {
            return false;
        }
        return true;
    }
    
    
    
}
