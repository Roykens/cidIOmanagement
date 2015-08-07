package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.ICategorieDao;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.entities.Categorie_;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.io.Serializable;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class CategorieDaoImpl extends GenericDao<Categorie, Long> implements ICategorieDao{

    @Override
    public Categorie findByArticle(Article article) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Categorie findByNomenclature(String nomenclature) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Categorie> cq = cb.createQuery(Categorie.class);
        Root<Categorie> cateroot = cq.from(Categorie.class);
        cq.where(cb.like(cateroot.get(Categorie_.nomenclatureSommaire), nomenclature));
        return getManager().createQuery(cq).getSingleResult();
    }
    
}
