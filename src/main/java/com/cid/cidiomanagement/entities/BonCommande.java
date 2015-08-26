package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
public class BonCommande implements Serializable {

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dateCommande;

    @Basic
    private String objet;
    
    @Basic
    @Enumerated(EnumType.STRING)
    private EtatType etat;

    @ManyToOne
    private Prestataire prestataire;

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

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public EtatType getEtat() {
        return etat;
    }

    public void setEtat(EtatType etat) {
        this.etat = etat;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }
    
    

    @Override
    public String toString() {
        return "BonCommande{" + "version=" + version + ", id=" + id + ", dateCommande=" + dateCommande + ", etat=" + etat + '}';
    }
    
    

}
