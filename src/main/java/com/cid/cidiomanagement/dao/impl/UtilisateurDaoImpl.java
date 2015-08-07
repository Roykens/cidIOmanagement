package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IUtilisateurDao;
import com.cid.cidiomanagement.entities.Utilisateur;
import com.cid.cidiomanagement.entities.Utilisateur_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class UtilisateurDaoImpl extends GenericDao<Utilisateur, Long> implements IUtilisateurDao{

    @Override
    public Utilisateur findByLogin(String login) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Utilisateur> cq = cb.createQuery(Utilisateur.class);
        Root<Utilisateur> userRoot = cq.from(Utilisateur.class);
        cq.where(cb.like(userRoot.get(Utilisateur_.login),login));
       return getManager().createQuery(cq).getSingleResult();
    }
    
}
