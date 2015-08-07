package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IPrestataireDao;
import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.entities.Prestataire_;
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
public class PrestataireDaoImpl extends GenericDao<Prestataire, Long> implements IPrestataireDao{

    @Override
    public Prestataire findByNom(String Nom) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Prestataire> cq = cb.createQuery(Prestataire.class);
        Root<Prestataire> presRoot = cq.from(Prestataire.class);
        cq.where(cb.like(presRoot.get(Prestataire_.nom), Nom));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
