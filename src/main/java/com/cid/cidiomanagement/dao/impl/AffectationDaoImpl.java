package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IAffectationDao;
import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.Affectation_;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.BonSortie_;
import com.cid.cidiomanagement.entities.OrdreSortie;
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
public class AffectationDaoImpl extends GenericDao<Affectation, Long> implements IAffectationDao{

    @Override
    public List<Affectation> findByBonSortie(BonSortie bonSortie) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Affectation> cq = cb.createQuery(Affectation.class);
        Root<Affectation> affRoot = cq.from(Affectation.class);
        cq.where(cb.equal(affRoot.get(Affectation_.bonSortie), bonSortie));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Affectation> findAllByOrdreSortie(OrdreSortie ordreSortie) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Affectation> cq = cb.createQuery(Affectation.class);
        CriteriaQuery<BonSortie> cq2 = cb.createQuery(BonSortie.class);
        Root<Affectation> afRoot = cq.from(Affectation.class);
        Path<BonSortie> bsPath = afRoot.get(Affectation_.bonSortie);   
        List<BonSortie> sorties = getManager().createQuery(cq2).getResultList();
        cq.where(cb.and(cb.equal(bsPath.get(BonSortie_.ordeSortie), ordreSortie), afRoot.get(Affectation_.bonSortie).in(sorties)));
        return getManager().createQuery(cq).getResultList();
    }
    
}
