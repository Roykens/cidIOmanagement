package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Utilisateur;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.io.Serializable;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IUtilisateurDao extends IGenericDao<Utilisateur, Long>{
    
    public Utilisateur findByLogin(String login) throws DataAccessException;
    
    public Utilisateur findByLoginAndPassword(String login, String password) throws DataAccessException;
}
