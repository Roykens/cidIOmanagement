package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IBonSortieDao;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.BonSortie_;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class BonSortieDaoImpl extends GenericDao<BonSortie, Long> implements IBonSortieDao{

    @Override
    public List<BonSortie> findAll() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<BonSortie> cq = cb.createQuery(BonSortie.class);
        Root<BonSortie> bsRoot = cq.from(BonSortie.class);
        cq.select(bsRoot);
        cq.orderBy(cb.desc(bsRoot.get(BonSortie_.datesortie)));
        return getManager().createQuery(cq).getResultList();
    }

    
    
    @Override
    public List<BonSortie> findByOrdreSortie(OrdreSortie ordreSortie) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<BonSortie> cq = cb.createQuery(BonSortie.class);
        Root<BonSortie> bonRoot = cq.from(BonSortie.class);
        cq.where(cb.equal(bonRoot.get(BonSortie_.ordeSortie), ordreSortie));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public List<BonSortie> findByMonthAndYear(int month, int year) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
