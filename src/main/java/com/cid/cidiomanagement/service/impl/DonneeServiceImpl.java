package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IArticleDao;
import com.cid.cidiomanagement.dao.ICategorieDao;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.Categorie;
import com.cid.cidiomanagement.service.IDonneeService;
import com.cid.cidiomanagement.service.ServiceException;
import com.cid.cidiomanagement.service.util.ImportationError;
import com.cid.cidiomanagement.service.util.ImportationResult;
import com.royken.generic.dao.DataAccessException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Transactional(readOnly = false)
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
                categorie.setActive(false);
                categorieDao.update(categorie);
                List<Article> articles = articleDao.findByCategorie(categorie);
                for (Article article : articles) {
                    article.setActive(false);
                    articleDao.update(article);
                }
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
            if (article != null) {
                article.setActive(false);
                articleDao.update(article);
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

    @Override
    public ImportationResult importArticle(InputStream stream, Long idCategorie) throws ServiceException {
        ImportationResult result = new ImportationResult();
        List<ImportationError> erreurs = new ArrayList<>();
        int count = 0;
        try {

            Categorie cat = categorieDao.findById(idCategorie);
            Workbook workbook = WorkbookFactory.create(stream);
            final Sheet sheet = workbook.getSheetAt(0);
            int index = 1;
            Row row = sheet.getRow(index++);
            String reference;
            String designation;
            String conditionnement;
            double prixU;
            int quantite;
            while (row != null) {
                Article article = new Article();
                if (row.getCell(1) != null) {
                    //cell.setCellType(Cell.CELL_TYPE_STRING);
                    Cell cell = row.getCell(1);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    reference = cell.getStringCellValue();
                    System.out.println("La valuer de la cellule "+ reference);
                    article.setReference(reference);

                    if (row.getCell(2) != null) {
                        designation = row.getCell(2).getStringCellValue();
                        article.setDesignation(designation);

                        if (row.getCell(3) != null) {
                            conditionnement = row.getCell(3).getStringCellValue();
                            article.setConditionnement(conditionnement);

                            if (row.getCell(4) != null) {
                                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    prixU = row.getCell(4).getNumericCellValue();
                                    article.setPrixUnitaire(prixU);

                                    if (row.getCell(5) != null) {
                                        if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                            quantite = (int) row.getCell(5).getNumericCellValue();
                                            article.setQuantite(quantite);
                                            article.setCategorie(cat);
                                            try {
                                                System.out.println(article);
                                                articleDao.create(article);
                                                count++;
                                            } catch (Exception ex) {
                                                ImportationError err = new ImportationError(index, ex.getMessage());
                                                erreurs.add(err);
                                            }
                                        } else {
                                            ImportationError err = new ImportationError(index, "La quantité doit être un nombre");
                                            erreurs.add(err);
                                        }

                                    } else {
                                        ImportationError err = new ImportationError(index, "La quantité est obligatoire");
                                        erreurs.add(err);
                                    }

                                } else {
                                    ImportationError err = new ImportationError(index, "Le prix unitaire doit être un nombre");
                                    erreurs.add(err);
                                }
                            }
                        } else {
                            ImportationError err = new ImportationError(index, "Le prix unitaire est obligatoire");
                            erreurs.add(err);
                        }

                    } else {
                        ImportationError err = new ImportationError(index, "La désignation est obligatoire");
                        erreurs.add(err);
                    }
                } else {
                    ImportationError err = new ImportationError(index, "La reférence est obligatoire");
                    erreurs.add(err);
                }
                row = sheet.getRow(index++);
            }

        } catch (DataAccessException | IOException | InvalidFormatException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        result.setNombreImporte(count);
        result.setErreurs(erreurs);
        return result;
    }

    @Override
    public List<Article> findByRange(int debut, int fin) throws ServiceException {
        try {
            return articleDao.findRange(debut, fin);
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Long countArticles() throws ServiceException {
        try {
            return articleDao.count();
        } catch (DataAccessException ex) {
            Logger.getLogger(DonneeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1L;
    }

}
