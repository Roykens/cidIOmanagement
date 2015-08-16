package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IAffectationDao extends IGenericDao<Affectation, Long>{
    
    public List<Affectation> findByBonSortie(BonSortie bonSortie) throws DataAccessException;
    
    public List<Affectation> findAllByOrdreSortie(OrdreSortie ordreSortie) throws DataAccessException;
}
