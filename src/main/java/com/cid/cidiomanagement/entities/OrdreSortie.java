package com.cid.cidiomanagement.entities;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
public class OrdreSortie implements Serializable {

    @Transient
    private final DateFormat df;
    

    @Version
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "ordeSortie")
    private List<BonSortie> bonSorties;

    @Temporal(TemporalType.DATE)
    @Column(unique = true)
    private Date dateOrdreSortie;
    
    @Basic
    @Column(unique = true)
    private String dateString;

    @Basic
    @Enumerated(EnumType.STRING)
    private EtatType etatType;
    
    public OrdreSortie() {
        // df = new SimpleDateFormat("MM-yyyy");
         df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.FRANCE);
    }

    
    
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

    public List<BonSortie> getBonSorties() {
        return bonSorties;
    }

    public void setBonSorties(List<BonSortie> bonSorties) {
        this.bonSorties = bonSorties;
    }

    public Date getDateOrdreSortie() {
        return dateOrdreSortie;
    }

    public void setDateOrdreSortie(Date dateOrdreSortie) {
        this.dateOrdreSortie = dateOrdreSortie;
    }

    public EtatType getEtatType() {
        return etatType;
    }

    public void setEtatType(EtatType etatType) {
        this.etatType = etatType;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
    
    
    

    @Override
    public String toString() {
        return dateString;
    }
    
}
