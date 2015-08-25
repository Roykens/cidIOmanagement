package com.cid.cidiomanagement.service.util;

import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ImportationResult {
    private int nombreImporte;
    
    private List<ImportationError> erreurs;

    public ImportationResult() {
    }

    public int getNombreImporte() {
        return nombreImporte;
    }

    public void setNombreImporte(int nombreImporte) {
        this.nombreImporte = nombreImporte;
    }

    public List<ImportationError> getErreurs() {
        return erreurs;
    }

    public void setErreurs(List<ImportationError> erreurs) {
        this.erreurs = erreurs;
    }
    
    
}
