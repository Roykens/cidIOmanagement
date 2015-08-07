package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IBonCommandeDao;
import com.cid.cidiomanagement.dao.ICommandeDao;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.service.ICommandeService;
import com.cid.cidiomanagement.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.io.OutputStream;
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
public class CommandeServiceImpl implements ICommandeService {

    private ICommandeDao commandeDao;
    private IBonCommandeDao bonCommandeDao;

    public ICommandeDao getCommandeDao() {
        return commandeDao;
    }

    public void setCommandeDao(ICommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    public IBonCommandeDao getBonCommandeDao() {
        return bonCommandeDao;
    }

    public void setBonCommandeDao(IBonCommandeDao bonCommandeDao) {
        this.bonCommandeDao = bonCommandeDao;
    }

    @Override
    public BonCommande saveOrUpdateBon(BonCommande bonCommande) throws ServiceException {

        try {
            if (bonCommande.getId() == null) {
                return bonCommandeDao.create(bonCommande);
            } else {
                return bonCommandeDao.update(bonCommande);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BonCommande> findAllCommande() throws ServiceException {
        try {
            return bonCommandeDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deleteCommande(Long id) throws ServiceException {
        try {
            BonCommande comm = bonCommandeDao.findById(id);
            if (comm != null) {
                bonCommandeDao.delete(comm);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BonCommande findBonById(Long id) throws ServiceException {
        try {
            return bonCommandeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Commande saveOrUpdateCommande(Commande commande) throws ServiceException {
        try {
            if (commande.getId() == null) {
                return commandeDao.create(commande);
            } else {
                return commandeDao.update(commande);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Commande findCommandeById(Long id) throws ServiceException {
        try {
            return commandeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Commande> findAllByBon(Long id) throws ServiceException {
        try {
            BonCommande bonCommande = bonCommandeDao.findById(id);
            if(bonCommande != null){
                return commandeDao.findByBon(bonCommande);
            }
            else{
                return null;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void produireBonCommande(Long bonCommandeId, OutputStream stream) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void produireOrdreEntree(Long bonCommandeId, OutputStream stream) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
