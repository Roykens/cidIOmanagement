package com.cid.cidiomanagement.service;

import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Commande;
import java.io.OutputStream;
import java.util.List;

/**
 *
 * @author royken
 */
public interface ICommandeService {
    
    public BonCommande saveOrUpdateBon(BonCommande bonCommande)throws ServiceException;
    
    public List<BonCommande> findAllCommande() throws ServiceException;
    
    public void deleteCommande(Long id) throws ServiceException;
    
    public BonCommande findBonById(Long id) throws ServiceException;
    
    public Commande saveOrUpdateCommande(Commande commande) throws ServiceException;
    
    public Commande findCommandeById(Long id ) throws ServiceException;
    
    public List<Commande> findAllByBon(Long id) throws ServiceException;
    
    public void deleteBonCommande(Long idBon) throws ServiceException;
    
    public void produireBonCommande(Long bonCommandeId, OutputStream stream) throws ServiceException;
    
    public void produireOrdreEntree(Long bonCommandeId, OutputStream stream, String noFacture, java.util.Date dateFacture, int noOrdre, int noChapitre) throws ServiceException;
    
    public void produceBonCommande(Long bonId, String objet ,OutputStream outputStream) throws ServiceException;
      
      
    
    
}
