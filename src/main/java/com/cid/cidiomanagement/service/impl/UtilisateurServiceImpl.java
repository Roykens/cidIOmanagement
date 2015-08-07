package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IUtilisateurDao;
import com.cid.cidiomanagement.entities.RoleType;
import com.cid.cidiomanagement.entities.Utilisateur;
import com.cid.cidiomanagement.service.IUtilisateurService;
import com.cid.cidiomanagement.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Transactional
public class UtilisateurServiceImpl implements UserDetailsService, IUtilisateurService {

    
    private IUtilisateurDao utilisateurDao;

    public IUtilisateurDao getUtilisateurDao() {
        return utilisateurDao;
    }

    public void setUtilisateurDao(IUtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        try {
            Utilisateur user = utilisateurDao.findByLogin(string);
            GrantedAuthority authority = buildUserAuthority(user.getRole());
            return buildUserForAuthetication(user, authority);
        } catch (DataAccessException ex) {
            Logger.getLogger(UtilisateurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private UserDetails buildUserForAuthetication(Utilisateur utilisateur, GrantedAuthority authority) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(authority);
        return new User(utilisateur.getLogin(), utilisateur.getPassword(), utilisateur.isEnabled(), true, true, true, authorities);
    }

    private GrantedAuthority buildUserAuthority(RoleType role) {
        return new SimpleGrantedAuthority(stringFromRole(role));
    }

    private String stringFromRole(RoleType role) {
        if (role == RoleType.ADMIN) {
            return "ADMIN";
        } else {
            return "USER";
        }
    }

    @Override
    public List<Utilisateur> findAll() throws ServiceException {
        System.out.println("J'entre dans le servcie\n\n");
        try {
            return utilisateurDao.findAll();
          } catch (DataAccessException ex) {
            Logger.getLogger(UtilisateurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Utilisateur saveOrUpdateUtilisateur(Utilisateur utilisateur) throws ServiceException {
        try {
            if (utilisateur.getId() == null) {

                return utilisateurDao.create(utilisateur);
            } else {
                return utilisateurDao.update(utilisateur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(UtilisateurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Utilisateur findById(Long id) throws ServiceException {
        try {
            return utilisateurDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(UtilisateurServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
