package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
public class Prestataire implements Serializable{
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    @Column(nullable = false, unique = true)
    private String nom;
    
    @Basic
    private String adresse;
    
    @Basic
    private String telephone;
    
    @Basic
    private String noContribuable;
    
    @Basic
    private Double air;
    
    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL)
    private List<BonCommande> bonCommandes;

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNoContribuable() {
        return noContribuable;
    }

    public void setNoContribuable(String noContribuable) {
        this.noContribuable = noContribuable;
    }

    public Double getAir() {
        return air;
    }

    public void setAir(Double air) {
        this.air = air;
    }

    @Override
    public String toString() {
        return  nom;
    }
    
    
    
}
