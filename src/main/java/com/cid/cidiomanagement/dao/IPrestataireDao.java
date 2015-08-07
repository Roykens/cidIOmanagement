package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Prestataire;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.io.Serializable;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPrestataireDao extends IGenericDao<Prestataire, Long>{
    public Prestataire findByNom( String Nom) throws DataAccessException;
}
