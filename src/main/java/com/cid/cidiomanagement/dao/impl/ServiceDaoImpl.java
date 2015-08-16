package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IServiceDao;
import com.cid.cidiomanagement.entities.Service;
import com.cid.cidiomanagement.entities.Service_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ServiceDaoImpl extends GenericDao<Service, Long> implements IServiceDao{

    @Override
    public Service findServiceByCode(String code) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Service> cq = cb.createQuery(Service.class);
        Root<Service> servRoot = cq.from(Service.class);
        cq.where(cb.like(servRoot.get(Service_.code), code));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
