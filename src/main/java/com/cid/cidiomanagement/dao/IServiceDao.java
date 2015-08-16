package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Service;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IServiceDao extends IGenericDao<Service, Long>{
    
    public Service findServiceByCode(String code) throws DataAccessException;
    
}
