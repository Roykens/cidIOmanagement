package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
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
public class Affectation implements Serializable{
    
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    @Min(0)
    private Integer qteDemandee;
    
    @Basic
    @Min(0)
    private Integer nombre;
    
    @Basic
    @Min(0)
    private int prix;
    
    
    @ManyToOne
    private Article article;
    
    @ManyToOne
    private BonSortie bonSortie;
    
    @Basic
    private String observation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNombre() {
        return nombre;
    }

    public void setNombre(Integer nombre) {
        this.nombre = nombre;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public BonSortie getBonSortie() {
        return bonSortie;
    }

    public void setBonSortie(BonSortie bonSortie) {
        this.bonSortie = bonSortie;
    }

    public Integer getQteDemandee() {
        return qteDemandee;
    }

    public void setQteDemandee(Integer qteDemandee) {
        this.qteDemandee = qteDemandee;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }
    
     public String getCategorie(){
        return article.getCategorie().getNomenclatureSommaire();
    }
    
    
    public boolean myCompare(Object obj){
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Affectation other = (Affectation) obj;
        if (!Objects.equals(this.article, other.article)) {
            return false;
        }
        
        return Objects.equals(this.prix, other.prix);
           
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.qteDemandee);
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.prix);
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
        final Affectation other = (Affectation) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.qteDemandee, other.qteDemandee)) {
            return false;
        }
        
        if (!Objects.equals(this.prix, other.prix)) {
            return false;
        }
        return Objects.equals(this.nombre, other.nombre);
    }

    @Override
    public String toString() {
        return "Affectation{" + "id=" + id + ", qteDemandee=" + qteDemandee + ", nombre=" + nombre + ", prix=" + prix + ", article=" + article + ", observation=" + observation + '}';
    }
}
