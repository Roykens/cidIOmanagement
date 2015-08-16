package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Personnel;
import com.cid.cidiomanagement.entities.Service;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPersonnelDao extends IGenericDao<Personnel, Long>{
    
    public List<Personnel> findAllPersonnelByService(Service service) throws DataAccessException;
    
    public Personnel findPersonnelByNom(String nom) throws DataAccessException;
}
