package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.ICommandeDao;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Article_;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Commande_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class CommandeDaoImpl extends GenericDao<Commande, Long> implements ICommandeDao{

    @Override
    public List<Commande> findByBon(BonCommande bonCommande) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Commande> cq = cb.createQuery(Commande.class);
        Root<Commande> commRoot = cq.from(Commande.class);
        Path<Article> artPath = commRoot.get(Commande_.article);        
        cq.where(cb.equal(commRoot.get(Commande_.bonCommande), bonCommande));
      //  cq.orderBy(cb.asc(artPath.get(Article_.categorie)));
      //  cq.orderBy(cb.asc(artPath.get(Article_.designation)));
        cq.orderBy(cb.asc(artPath.get(Article_.categorie)), cb.asc(artPath.get(Article_.designation)));
        
        return getManager().createQuery(cq).getResultList();
    }
    
}
