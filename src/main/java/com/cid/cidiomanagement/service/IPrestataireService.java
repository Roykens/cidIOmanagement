package com.cid.cidiomanagement.service;

import com.cid.cidiomanagement.dao.IPrestataireDao;
import com.cid.cidiomanagement.entities.Prestataire;
import com.royken.generic.dao.DataAccessException;
import java.util.List;

/**
 *
 * @author royken
 */
public interface IPrestataireService {
    
    public Prestataire saveOrUpdatePrestataire(Prestataire prestataire) throws ServiceException;
    
    public Prestataire findPrestataireById(Long id) throws ServiceException;
    
    public Prestataire findByNom(String nom) throws ServiceException;
    
    public void deletePrestataire(Long id) throws ServiceException;
    
    public List<Prestataire> findAllPrestataire() throws ServiceException;
}
