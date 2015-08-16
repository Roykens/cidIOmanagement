package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IAffectationDao;
import com.cid.cidiomanagement.dao.IBonSortieDao;
import com.cid.cidiomanagement.dao.IOrdreSortieDao;
import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.cid.cidiomanagement.service.IOrdreSortieService;
import com.cid.cidiomanagement.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Transactional
public class OrdreSortieServiceImpl implements IOrdreSortieService {

    private IAffectationDao affectationDao;

    private IBonSortieDao bonSortieDao;

    private IOrdreSortieDao ordreSortieDao;

    public IAffectationDao getAffectationDao() {
        return affectationDao;
    }

    public void setAffectationDao(IAffectationDao affectationDao) {
        this.affectationDao = affectationDao;
    }

    public IBonSortieDao getBonSortieDao() {
        return bonSortieDao;
    }

    public void setBonSortieDao(IBonSortieDao bonSortieDao) {
        this.bonSortieDao = bonSortieDao;
    }

    public IOrdreSortieDao getOrdreSortieDao() {
        return ordreSortieDao;
    }

    public void setOrdreSortieDao(IOrdreSortieDao ordreSortieDao) {
        this.ordreSortieDao = ordreSortieDao;
    }

    @Override
    public Affectation saveOrUpdateAffectation(Affectation affectation) throws ServiceException {
        try {
            if (affectation.getId() == null) {
                return affectationDao.create(affectation);
            } else {
                return affectationDao.update(affectation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Affectation findAffectationById(Long id) throws ServiceException {
        try {
            return affectationDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteAffectation(Long id) throws ServiceException {
        try {
            Affectation affectation = affectationDao.findById(id);
            if (affectation != null) {
                affectationDao.delete(affectation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Affectation> findAllAffectation() throws ServiceException {
        try {
            return affectationDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Affectation> findAllByBon(Long idBon) throws ServiceException {
        try {
            BonSortie bs = bonSortieDao.findById(idBon);
            if (bs == null) {
                throw new ServiceException("Service not found");
            }
            return affectationDao.findByBonSortie(bs);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public BonSortie saveOrUpdateBonSortie(BonSortie bonSortie) throws ServiceException {
        try {
            if (bonSortie.getId() == null) {
                return bonSortieDao.create(bonSortie);
            } else {
                return bonSortieDao.update(bonSortie);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BonSortie findBonById(Long id) throws ServiceException {
        try {
            return bonSortieDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteBon(Long id) throws ServiceException {
        try {
            BonSortie bs = bonSortieDao.findById(id);
            if (bs != null) {
                bonSortieDao.delete(bs);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<BonSortie> findAllBonSortie() throws ServiceException {
        try {
            return bonSortieDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<BonSortie> findAllByOrdre(Long idOdre) throws ServiceException {
        try {
            OrdreSortie os = ordreSortieDao.findById(idOdre);
            if (os == null) {
                throw new ServiceException("Service not found");
            }
            return bonSortieDao.findByOrdreSortie(os);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public OrdreSortie saveOrUpdateOrdre(OrdreSortie ordreSortie) throws ServiceException {
        try {
            if (ordreSortie.getId() == null) {
                return ordreSortieDao.create(ordreSortie);
            } else {
                return ordreSortieDao.update(ordreSortie);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public OrdreSortie findOrdreById(Long id) throws ServiceException {
        try {
            return ordreSortieDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deletOrdre(Long id) throws ServiceException {
        try {
            OrdreSortie os = ordreSortieDao.findById(id);
            if(os != null){
                ordreSortieDao.delete(os);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<OrdreSortie> findAllOrdre() throws ServiceException {
        try {
            return ordreSortieDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

}
