package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IArticleDao extends IGenericDao<Article, Long>{
    
    public List<Article> findByCategorie(Categorie categorie) throws DataAccessException;
    
    public Article findByReference(String reference) throws DataAccessException;
    
    public Article findByNom(String nom) throws DataAccessException;
}
