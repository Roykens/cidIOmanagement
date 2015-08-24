package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IAffectationDao;
import com.cid.cidiomanagement.dao.IBonSortieDao;
import com.cid.cidiomanagement.dao.IOrdreSortieDao;
import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.OrdreSortie;
import com.cid.cidiomanagement.service.IOrdreSortieService;
import com.cid.cidiomanagement.service.ServiceException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.royken.generic.dao.DataAccessException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Transactional
public class OrdreSortieServiceImpl implements IOrdreSortieService {

    private IAffectationDao affectationDao;

    private IBonSortieDao bonSortieDao;

    private IOrdreSortieDao ordreSortieDao;

    public IAffectationDao getAffectationDao() {
        return affectationDao;
    }

    public void setAffectationDao(IAffectationDao affectationDao) {
        this.affectationDao = affectationDao;
    }

    public IBonSortieDao getBonSortieDao() {
        return bonSortieDao;
    }

    public void setBonSortieDao(IBonSortieDao bonSortieDao) {
        this.bonSortieDao = bonSortieDao;
    }

    public IOrdreSortieDao getOrdreSortieDao() {
        return ordreSortieDao;
    }

    public void setOrdreSortieDao(IOrdreSortieDao ordreSortieDao) {
        this.ordreSortieDao = ordreSortieDao;
    }

    @Override
    public Affectation saveOrUpdateAffectation(Affectation affectation) throws ServiceException {
        try {
            if (affectation.getId() == null) {
                return affectationDao.create(affectation);
            } else {
                return affectationDao.update(affectation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Affectation findAffectationById(Long id) throws ServiceException {
        try {
            return affectationDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteAffectation(Long id) throws ServiceException {
        try {
            Affectation affectation = affectationDao.findById(id);
            if (affectation != null) {
                affectationDao.delete(affectation);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Affectation> findAllAffectation() throws ServiceException {
        try {
            return affectationDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<Affectation> findAllByBon(Long idBon) throws ServiceException {
        try {
            BonSortie bs = bonSortieDao.findById(idBon);
            if (bs == null) {
                throw new ServiceException("Service not found");
            }
            return affectationDao.findByBonSortie(bs);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public BonSortie saveOrUpdateBonSortie(BonSortie bonSortie) throws ServiceException {
        try {
            if (bonSortie.getId() == null) {
                return bonSortieDao.create(bonSortie);
            } else {
                return bonSortieDao.update(bonSortie);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BonSortie findBonById(Long id) throws ServiceException {
        try {
            return bonSortieDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteBon(Long id) throws ServiceException {
        try {
            BonSortie bs = bonSortieDao.findById(id);
            if (bs != null) {
                bonSortieDao.delete(bs);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<BonSortie> findAllBonSortie() throws ServiceException {
        try {
            return bonSortieDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<BonSortie> findAllByOrdre(Long idOdre) throws ServiceException {
        try {
            OrdreSortie os = ordreSortieDao.findById(idOdre);
            if (os == null) {
                throw new ServiceException("Service not found");
            }
            return bonSortieDao.findByOrdreSortie(os);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public OrdreSortie saveOrUpdateOrdre(OrdreSortie ordreSortie) throws ServiceException {
        try {
            if (ordreSortie.getId() == null) {
                return ordreSortieDao.create(ordreSortie);
            } else {
                return ordreSortieDao.update(ordreSortie);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public OrdreSortie findOrdreById(Long id) throws ServiceException {
        try {
            return ordreSortieDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deletOrdre(Long id) throws ServiceException {
        try {
            OrdreSortie os = ordreSortieDao.findById(id);
            if (os != null) {
                ordreSortieDao.delete(os);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<OrdreSortie> findAllOrdre() throws ServiceException {
        try {
            return ordreSortieDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public OrdreSortie findByDateString(String date) throws ServiceException {
        try {
            return ordreSortieDao.findByDateString(date);
        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void imprimerBsp(Long idBsp, OutputStream stream) throws ServiceException {
        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4.rotate());
            doc.open();
            Util.produceHeader(doc);
            BonSortie bs = bonSortieDao.findById(idBsp);
            produceBspTable(bs, doc);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceBspTable(BonSortie bs, Document doc) {
        try {
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            List<Affectation> affectations = affectationDao.findByBonSortie(bs);

            doc.add(new Chunk("\n\n"));
            Chunk ch1 = new Chunk("BON DE SORTIE PROVISOIRE", bf12);
            Paragraph p1 = new Paragraph(ch1);
            p1.setAlignment(Element.ALIGN_CENTER);
            doc.add(p1);
            Chunk ch2 = new Chunk("DEMANDE DE MATERIEL (1)", bf1);
            Paragraph p2 = new Paragraph(ch2);
            p2.setAlignment(Element.ALIGN_CENTER);
            doc.add(p2);
            doc.add(new Chunk("\n\n"));

            float relativewiths[] = {2, 2, 3, 3, 7, 3, 2, 3, 3, 4};
            PdfPTable table = new PdfPTable(relativewiths);
            table.setWidthPercentage(100);
            Chunk ch = new Chunk(" N", bf1);
            Chunk expo = new Chunk("o", bf1);
            expo.setTextRise(4f);
            Chunk ch3 = new Chunk("d'ordre", bf1);
            Phrase ph = new Phrase(ch);
            ph.add(expo);
            ph.add(ch3);
            PdfPCell cellex = new PdfPCell(ph);
            cellex.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellex.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellex.setPaddingBottom(4f);
            cellex.setPaddingTop(5f);
            cellex.setBorderWidth(0.01f);
            table.addCell(cellex);

            Chunk ch4 = new Chunk(" N", bf1);
            Chunk expo1 = new Chunk("o", bf1);
            expo.setTextRise(4f);
            Chunk ch5 = new Chunk("NS", bf1);
            Phrase ph1 = new Phrase(ch4);
            ph1.add(expo1);
            ph1.add(ch5);
            
            cellex = new PdfPCell(ph1);
            cellex.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellex.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellex.setPaddingBottom(4f);
            cellex.setPaddingTop(5f);
            cellex.setBorderWidth(0.01f);
            table.addCell(cellex);
            
            
            
            table.addCell(Util.createBonDefaultFHeader("CODE MERCURIAL", bf1));
            table.addCell(Util.createBonDefaultFHeader("DATE D'ENT AU MAG", bf1));
            table.addCell(Util.createBonDefaultFHeader("DESIGNATION DES MATIERES, DENREES ET OBJETS", bf1));
            table.addCell(Util.createBonDefaultFHeader("QTE DEMANDEE", bf1));
            table.addCell(Util.createBonDefaultFHeader("QTE EN STOCK", bf1));
            table.addCell(Util.createBonDefaultFHeader("QTE ACCORDEE", bf1));
            Chunk che = new Chunk(" N", bf1);
            Chunk expoa = new Chunk("o", bf1);
            expoa.setTextRise(4f);
            Chunk chr = new Chunk(" FICHE DEMANDEUR", bf1);
            Phrase phf = new Phrase(che);
            phf.add(expoa);
            phf.add(chr);
            cellex = new PdfPCell(phf);
            cellex.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellex.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellex.setPaddingBottom(4f);
            cellex.setPaddingTop(5f);
            cellex.setBorderWidth(0.01f);
            table.addCell(cellex);

            Chunk cho = new Chunk(" OBSERVATION ET/OU N", bf1);
            Chunk expov = new Chunk("o", bf1);
            expov.setTextRise(5f);
            Chunk chg = new Chunk(" DE SERIE", bf1);
            Phrase phs = new Phrase(cho);
            phs.add(expov);
            phs.add(chg);
            table.addCell(new PdfPCell(phs));

            int i = 1;
            for (Affectation af : affectations) {
                table.addCell(Util.createBonDefaultFHeader(String.valueOf(i++), bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getArticle().getCategorie().getNomenclatureSommaire(), bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getArticle().getReference(), bf1));
                table.addCell(Util.createBonDefaultFHeader("", bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getArticle().getDesignation(), bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getQteDemandee() + "", bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getArticle().getQuantite() + "", bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getNombre() + "", bf1));
                table.addCell(Util.createBonDefaultFHeader("", bf1));
                table.addCell(Util.createBonDefaultFHeader(af.getObservation(), bf1));
            }

            doc.add(table);

            doc.add(new Chunk("\n"));

            float widths[] = {4, 5, 4};
            PdfPTable signature = new PdfPTable(widths);
            signature.setWidthPercentage(100);
            Chunk s1 = new Chunk("LE DEMANDEUR\n", bf1);
            Chunk s2 = new Chunk(bs.getPersonnel().getNom(), bf1);
            Paragraph para = new Paragraph(s1);
            para.add(s2);
            PdfPCell cell = new PdfPCell(para);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorderColor(BaseColor.WHITE);
            // cell.se
            signature.addCell(cell);

            para = new Paragraph("AVIS ET SIGNATURE DE L'ORDONNATEUR-MATIERES OU SON REPRESENTANTS", bf1);
            cell = new PdfPCell(para);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorderColor(BaseColor.WHITE);
            signature.addCell(cell);

            para = new Paragraph("LE COMPTABLE-MATIERES", bf1);
            cell = new PdfPCell(para);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorderColor(BaseColor.WHITE);
            signature.addCell(cell);
            doc.add(signature);

            doc.add(new Chunk("\n", bf1));
            PdfPTable date = new PdfPTable(widths);
            date.setWidthPercentage(100);
            PdfPCell cell1 = new PdfPCell(new Paragraph(new Chunk("A ............................... Le ............................\n", bf1)));
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell1.setBorderColor(BaseColor.WHITE);
            date.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph(new Chunk("A .............................. Le ..............................\n", bf1)));
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell1.setBorderColor(BaseColor.WHITE);
            date.addCell(cell1);

            cell1 = new PdfPCell(new Paragraph(new Chunk("A .............................. Le ...............................\n", bf1)));
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell1.setBorderColor(BaseColor.WHITE);
            date.addCell(cell1);

            doc.add(date);

        } catch (DataAccessException | DocumentException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
