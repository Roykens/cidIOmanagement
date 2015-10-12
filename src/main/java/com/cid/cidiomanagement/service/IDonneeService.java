package com.cid.cidiomanagement.service;

import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.service.util.ImportationResult;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IDonneeService extends Serializable{
    
    /*** Annotation of applying method level Spring Security
     * @param categorie
     * @return 
     * @throws com.cid.cidiomanagement.service.ServiceException ***/
    @PreAuthorize("hasRole('ADMIN')")
    public Categorie saveOrUpdateCategorie(Categorie categorie) throws ServiceException;
    
    public Categorie findCategorieById(Long id) throws ServiceException;
    
    public void deleteCategorie(Long id) throws ServiceException;
    
    public List<Categorie> findAllCategorie() throws ServiceException;
    
    public Categorie findByArticle(Long idArticle) throws ServiceException;
    
    public Categorie findByNomenclature(String nomenclature) throws ServiceException;
    
    
    
    public Article saveOrUpdateArticle(Article article) throws ServiceException;
    
    public Article findArticleById(Long id) throws ServiceException;
    
    public void deleteArticle(Long id) throws ServiceException;
    
    public List<Article> findAllArticle() throws ServiceException;
    
    public List<Article> findByRange(int debut, int fin) throws ServiceException;
    
    public Long countArticles() throws ServiceException;
    
    public List<Article> findArticleByCtegorie(Long idCategorie) throws ServiceException;
    
    public Article findByReference(String reference) throws ServiceException;
    
    public Article findByDesignation(String designation) throws ServiceException;
    
    public ImportationResult importArticle(InputStream stream, Long idCategorie) throws ServiceException;
}
