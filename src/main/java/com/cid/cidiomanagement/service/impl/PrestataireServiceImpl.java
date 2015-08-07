package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IPrestataireDao;
import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.IPrestataireService;
import com.cid.cidiomanagement.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author royken
 */
@Transactional
public class PrestataireServiceImpl implements IPrestataireService {

    private IPrestataireDao prestataireDao;

    public IPrestataireDao getPrestataireDao() {
        return prestataireDao;
    }

    public void setPrestataireDao(IPrestataireDao prestataireDao) {
        this.prestataireDao = prestataireDao;
    }

    @Override
    public Prestataire saveOrUpdatePrestataire(Prestataire prestataire) throws ServiceException {

        try {
            if (prestataire.getId() == null) {
                return prestataireDao.create(prestataire);
            }
            else{
                return prestataireDao.update(prestataire);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PrestataireServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Prestataire findPrestataireById(Long id) throws ServiceException {
        try {
            return prestataireDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PrestataireServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deletePrestataire(Long id) throws ServiceException {
        try {
            Prestataire prestataire = prestataireDao.findById(id);
            if(prestataire != null){
                prestataireDao.delete(prestataire);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PrestataireServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Prestataire> findAllPrestataire() throws ServiceException {
        try {
            return prestataireDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(PrestataireServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Prestataire findByNom(String nom) throws ServiceException {
        try {
            return prestataireDao.findByNom(nom);
        } catch (DataAccessException ex) {
            Logger.getLogger(PrestataireServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
