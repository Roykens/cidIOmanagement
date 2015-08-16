package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IBonSortieDao extends IGenericDao<BonSortie, Long>{
    
    public List<BonSortie> findByOrdreSortie(OrdreSortie ordreSortie) throws DataAccessException;
    
    public List<BonSortie> findByMonthAndYear(int month, int year) throws DataAccessException;
}
