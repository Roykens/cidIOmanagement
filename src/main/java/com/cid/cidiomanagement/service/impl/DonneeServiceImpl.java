package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IArticleDao;
import com.cid.cidiomanagement.dao.ICategorieDao;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Transactional
public class DonneeServiceImpl implements IDonneeService {

   
    private ICategorieDao categorieDao;

  
    private IArticleDao articleDao;

    public ICategorieDao getCategorieDao() {
        return categorieDao;
    }

    public void setCategorieDao(ICategorieDao categorieDao) {
        this.categorieDao = categorieDao;
    }

    public IArticleDao getArticleDao() {
        return articleDao;
    }

    public void setArticleDao(IArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Categorie saveOrUpdateCategorie(Categorie categorie) throws ServiceException {
        try {
            if (categorie.getId() == null) {
                return categorieDao.create(categorie);
            } else {
                return categorieDao.update(categorie);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Categorie findCategorieById(Long id) throws ServiceException {
        try {
            return categorieDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteCategorie(Long id) throws ServiceException {
        try {
            Categorie categorie = categorieDao.findById(id);
            if (categorie != null) {
                categorieDao.delete(categorie);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Categorie> findAllCategorie() throws ServiceException {
        try {
            return categorieDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Categorie findByArticle(Long idArticle) throws ServiceException {
        try {
            Article article = articleDao.findById(idArticle);
            if (article != null) {
                return categorieDao.findByArticle(article);
            } else {
                return null;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public Categorie findByNomenclature(String nomenclature) throws ServiceException {
        try {
            return categorieDao.findByNomenclature(nomenclature);
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Article saveOrUpdateArticle(Article article) throws ServiceException {
        try {
            if (article.getId() == null) {

                return articleDao.create(article);
            } else {
                return articleDao.update(article);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Article findArticleById(Long id) throws ServiceException {
        try {
            return articleDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void deleteArticle(Long id) throws ServiceException {
        try {
            Article article = articleDao.findById(id);
            if(article != null){
            articleDao.delete(article);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<Article> findAllArticle() throws ServiceException {
        try {
            return articleDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);           
        }
         return Collections.EMPTY_LIST;
    }

    @Override
    public List<Article> findArticleByCtegorie(Long idCategorie) throws ServiceException {
        try {
            Categorie categorie = categorieDao.findById(idCategorie);
            if (categorie != null) {
                return articleDao.findByCategorie(categorie);
            } else {
                return Collections.EMPTY_LIST;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Article findByReference(String reference) throws ServiceException {
        try {
            return articleDao.findByReference(reference);
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Article findByDesignation(String designation) throws ServiceException {
        try {
            return articleDao.findByNom(designation);
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}