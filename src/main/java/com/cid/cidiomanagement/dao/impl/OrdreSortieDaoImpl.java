package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IOrdreSortieDao;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.cid.cidiomanagement.entities.OrdreSortie_;
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
public class OrdreSortieDaoImpl extends GenericDao<OrdreSortie, Long> implements IOrdreSortieDao{

    @Override
    public OrdreSortie findByDateString(String date) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<OrdreSortie> cq = cb.createQuery(OrdreSortie.class);
        Root<OrdreSortie> orRoot = cq.from(OrdreSortie.class);
        cq.where(cb.like(orRoot.get(OrdreSortie_.dateString), date));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
