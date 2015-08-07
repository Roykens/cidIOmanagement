package com.cid.cidiomanagement.dao;

import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.io.Serializable;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface ICategorieDao extends IGenericDao<Categorie, Long>{
    public Categorie findByArticle(Article article) throws DataAccessException;
    
    public Categorie findByNomenclature(String nomenclature) throws DataAccessException;
}
