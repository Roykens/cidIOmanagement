package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IOrdreSortieDao;
import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.Affectation_;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.BonSortie_;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.cid.cidiomanagement.entities.OrdreSortie_;
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
public class OrdreSortieDaoImpl extends GenericDao<OrdreSortie, Long> implements IOrdreSortieDao{

    @Override
    public OrdreSortie findByDateString(String date) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<OrdreSortie> cq = cb.createQuery(OrdreSortie.class);
        Root<OrdreSortie> orRoot = cq.from(OrdreSortie.class);
        cq.where(cb.like(orRoot.get(OrdreSortie_.dateString), date));
        return getManager().createQuery(cq).getSingleResult();
    }

   /* @Override
    public List<Affectation> findAllAffectationByOrdre(OrdreSortie sortie) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Affectation> cq = cb.createQuery(Affectation.class);
        CriteriaQuery<BonSortie> cq2 = cb.createQuery(BonSortie.class);
        Root<Affectation> affRoot = cq.from(Affectation.class);
        Root<BonSortie> bsRoot = cq.from(BonSortie.class);
        Path<BonSortie> bsPath = affRoot.get(Affectation_.bonSortie);
        cq2.where(cb.equal(bsRoot.get(BonSortie_.ordeSortie), sortie));
        List<BonSortie> bs = getManager().createQuery(cq2).getResultList();
        cq.where(affRoot.get(Affectation_.bonSortie).in(bs));
        return getManager().createQuery(cq).getResultList();
    }*/
    
}
