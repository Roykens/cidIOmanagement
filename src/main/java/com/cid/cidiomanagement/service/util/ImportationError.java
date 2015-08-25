package com.cid.cidiomanagement.service.util;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ImportationError {
    private int ligne;
    private String raison;

    public ImportationError() {
    }

    public ImportationError(int ligne, String raison) {
        this.ligne = ligne;
        this.raison = raison;
    }

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }
    
}
