package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IOrdreSortieDao extends IGenericDao<OrdreSortie, Long>{
    
    public OrdreSortie findByDateString(String date) throws DataAccessException;
    
  //  public List<Affectation> findAllAffectationByOrdre(OrdreSortie sortie) throws DataAccessException;
    
}
