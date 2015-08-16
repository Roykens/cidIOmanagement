package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IPersonnelDao;
import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.entities.Personnel_;
import com.cid.cidiomanagement.entities.Service;
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
public class PersonnelDaoImpl extends GenericDao<Personnel, Long> implements IPersonnelDao{

    @Override
    public List<Personnel> findAllPersonnelByService(Service service) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Personnel> cq = cb.createQuery(Personnel.class);
        Root<Personnel> perRoot = cq.from(Personnel.class);
        cq.where(cb.equal(perRoot.get(Personnel_.service), service));
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Personnel findPersonnelByNom(String nom) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Personnel> cq = cb.createQuery(Personnel.class);
        Root<Personnel> persRoot = cq.from(Personnel.class);
        cq.where(cb.like(persRoot.get(Personnel_.nom), nom));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
