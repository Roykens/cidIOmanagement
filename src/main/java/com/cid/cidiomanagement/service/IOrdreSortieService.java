package com.cid.cidiomanagement.service;

import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.OrdreSortie;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IOrdreSortieService {
    
    public Affectation saveOrUpdateAffectation(Affectation affectation) throws ServiceException;
    
    public Affectation findAffectationById(Long id) throws ServiceException;
    
    public void deleteAffectation(Long id) throws ServiceException;
    
    public List<Affectation> findAllAffectation() throws ServiceException;
    
    public List<Affectation> findAllByBon(Long idBon) throws ServiceException;
    
    
    
    public BonSortie saveOrUpdateBonSortie(BonSortie bonSortie) throws ServiceException;
    
    public BonSortie findBonById(Long id) throws ServiceException;
    
    public void deleteBon(Long id) throws ServiceException;
    
    public List<BonSortie> findAllBonSortie() throws ServiceException;
    
    public List<BonSortie> findAllByOrdre(Long idOdre) throws ServiceException;
    
    
    
    public OrdreSortie saveOrUpdateOrdre(OrdreSortie ordreSortie) throws ServiceException;
    
    public OrdreSortie findOrdreById(Long id) throws ServiceException;
    
    public void deletOrdre(Long id) throws ServiceException;
    
    public List<OrdreSortie> findAllOrdre() throws ServiceException;
}
