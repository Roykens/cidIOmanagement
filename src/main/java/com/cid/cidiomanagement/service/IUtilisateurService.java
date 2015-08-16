package com.cid.cidiomanagement.service;

import com.cid.cidiomanagement.entities.Utilisateur;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IUtilisateurService {
    public Utilisateur findById(Long id) throws ServiceException;
    public boolean findUtilisateurByLoginAndPassword(String login, String password) throws ServiceException;
    public Utilisateur saveOrUpdateUtilisateur(Utilisateur utilisateur) throws ServiceException;
    public List<Utilisateur> findAll() throws ServiceException;
    
}
