package com.cid.cidiomanagement.dao.impl;

import com.cid.cidiomanagement.dao.IArticleDao;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Article_;
import com.cid.cidiomanagement.entities.Categorie;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.impl.GenericDao;
import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class ArticleDaoImpl extends GenericDao<Article, Long> implements IArticleDao{

    @Override
    public List<Article> findByCategorie(Categorie categorie) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> niveauRoot = cq.from(Article.class);
        cq.where(cb.equal(niveauRoot.get(Article_.categorie), categorie));
        cq.orderBy(cb.asc(niveauRoot.get(Article_.designation)));
        cq.distinct(true);
        return getManager().createQuery(cq).getResultList();
    }

    @Override
    public Article findByReference(String reference) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> candidatureRoot = cq.from(Article.class);
        cq.where(cb.equal(candidatureRoot.get(Article_.reference), reference));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public Article findByNom(String nom) throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> artRoot = cq.from(Article.class);
        cq.where(cb.like(artRoot.get(Article_.designation), nom));
        return getManager().createQuery(cq).getSingleResult();
    }

    @Override
    public List<Article> findAll() throws DataAccessException {
        CriteriaBuilder cb = getManager().getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> artRoot = cq.from(Article.class);
        cq.select(artRoot).where(cb.equal(artRoot.get(Article_.active), true));
        return getManager().createQuery(cq).getResultList();
    }
    
    
    
}
