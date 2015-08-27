package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IBonCommandeDao;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.BonCommande_;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Commande_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class BonCommandeDaoImpl extends GenericDao<BonCommande, Long> implements IBonCommandeDao{

    @Override
    public List<BonCommande> findAll() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<BonCommande> cq = cb.createQuery(BonCommande.class);
        Root<BonCommande> bcRoot = cq.from(BonCommande.class);
        cq.select(bcRoot);
        cq.orderBy(cb.desc(bcRoot.get(BonCommande_.dateCommande)));
        return getManager().createQuery(cq).getResultList();
//        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
