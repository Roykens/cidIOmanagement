package com.cid.cidiomanagement.service.impl;

import co.royken.convert.FrenchNumberToWords;
import com.cid.cidiomanagement.dao.IAffectationDao;
import com.cid.cidiomanagement.dao.IArticleDao;
import com.cid.cidiomanagement.dao.IBonSortieDao;
import com.cid.cidiomanagement.dao.IOrdreSortieDao;
import com.cid.cidiomanagement.entities.Affectation;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.BonSortie;
import com.cid.cidiomanagement.entities.EtatType;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.WordUtils;
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

    private IArticleDao articleDao;

    public IArticleDao getArticleDao() {
        return articleDao;
    }

    public void setArticleDao(IArticleDao articleDao) {
        this.articleDao = articleDao;
    }

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
    public List<Affectation> findAllAffectationByOrdre(Long idOrdre) throws ServiceException {
        try {
            OrdreSortie os = ordreSortieDao.findById(idOrdre);
            //  List<Affectation> tmp = affectationDao.findAllByOrdreSortie(os);
            return affectationDao.findAllByOrdreSortie(os);

        } catch (DataAccessException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

   private static List<Affectation> mergeList(List<Affectation> affectations) {
        List<Affectation> result = new ArrayList<>();
        if(affectations.size() > 0){
            result.add(affectations.get(0));
            affectations.remove(0);
        }
        for (Affectation tmp11 : affectations) {
            for (int i = 0; i < result.size(); i++) {
               
                    if (result.get(i).myCompare(tmp11)) {
                      //  System.out.println("C'est bon");
                        result.get(i).setNombre(tmp11.getNombre() + result.get(i).getNombre());
                        break;
                    } else if(i == result.size()-1){
                       // System.out.println("C'est bon2");
                        result.add(tmp11);
                        break;
                    }               
            }
        }
        return result;
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
                if (bs.getOrdeSortie().getEtatType() != EtatType.acheve) {
                    bs.setActive(false);
                    bonSortieDao.update(bs);
                }
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
                if (os.getEtatType() != EtatType.acheve) {
                    os.setActive(false);
                    ordreSortieDao.update(os);
                }
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
                Article article = af.getArticle();
                article.setQuantite(article.getQuantite() - af.getNombre());
                articleDao.update(article);
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

    private static Set<String> getCategories(List<Affectation> affectations) {
        Set<String> result = new HashSet<>();
        for (Affectation affectation : affectations) {
            result.add(affectation.getCategorie());
        }
        return result;
    }

    private static Map<String, List<Affectation>> transform(List<Affectation> affectations) {

        Map<String, List<Affectation>> result = new HashMap<>();
        Set<String> cat = getCategories(affectations);
        for (String cat1 : cat) {
            List<Affectation> temp = new ArrayList<>();
            for (Affectation com : affectations) {
                if (com.getCategorie().equals(cat1)) {
                    temp.add(com);
                }
            }
            result.put(cat1, temp);
        }
        System.out.println("==================================================");
        System.out.println("Apres transformation");
        System.out.println(result);
        return result;
    }

    @Override
    public void imprimerOrdreSortie(Long idOrdre, OutputStream stream, Date dateOrdre, int noChapitre, int noOdre, String objet) throws ServiceException {
        try {
            OrdreSortie os = ordreSortieDao.findById(idOrdre);
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4);
            doc.open();
            Util.produceHeader(doc);
            produireOrdreSortieBody(os, doc, dateOrdre, noChapitre, noOdre, objet);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(OrdreSortieServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produireOrdreSortieBody(OrdreSortie os, Document doc, Date dateOrdre, int noChapitre, int noOrdre, String objet) {
        try {
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font bf12b = new Font(Font.FontFamily.TIMES_ROMAN, 7);
            Font bf12i = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.ITALIC);
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf1b = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font fontBig = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font fontBig2 = new Font(Font.FontFamily.TIMES_ROMAN, 14);

//            float relativewidth[] = {4, 2, 6};
//            PdfPTable firstTable = new PdfPTable(relativewidth);
//            firstTable.setWidthPercentage(100);
//            Phrase post = new Phrase("POSTE COMPTABLE ........................................\n", fontEntete);
//            Phrase postEng = new Phrase("ACCOUNTING POST\n .................................................................", bf10);
//            Phrase ph1 = new Phrase();
//            ph1.add(post);
//            ph1.add(postEng);
//            PdfPCell cell1 = new PdfPCell(ph1);
//            cell1.setBorderColor(BaseColor.WHITE);
//            cell1.setColspan(2);
//            //cell1.setPaddingBottom(2f);
//            cell1.setPaddingTop(2f);
//            firstTable.addCell(cell1);
//
//            Phrase code = new Phrase("CODE POSTE COMPTABLE\n", fontEntete);
//            Phrase codeEng = new Phrase("ACCOUNTING POST CODE\n", bf10);
//            Phrase codeA = new Phrase("CODE ANNEXE...............................\n", fontEntete);
//            Phrase codeAe = new Phrase("ANNEXE CODE", bf10);
//            ph1 = new Phrase();
//            ph1.add(code);
//            ph1.add(codeEng);
//            ph1.add(new Phrase("\n"));
//            ph1.add(codeA);
//            ph1.add(codeAe);
//            cell1 = new PdfPCell(ph1);
//            cell1.setRowspan(3);
//            cell1.setBorderColor(BaseColor.WHITE);
//            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
//            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
//            //cell1.setPaddingBottom(2f);
//            cell1.setPaddingTop(2f);
//            firstTable.addCell(cell1);
//
//            Phrase serv = new Phrase("SERVICE COMPTABLE AUPRES DE ..............\n", fontEntete);
//            Phrase serve = new Phrase("ACCOUNTING SERVICE TO \n ...................................................................", bf10);
//
//            ph1 = new Phrase();
//            ph1.add(serv);
//            ph1.add(serve);
//            cell1 = new PdfPCell(ph1);
//            cell1.setBorderColor(BaseColor.WHITE);
//            //cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
//            cell1.setColspan(2);
//            //cell1.setPaddingBottom(2f);
//            cell1.setPaddingTop(2f);
//            firstTable.addCell(cell1);
//
//            Phrase num = new Phrase("Numéro d'ordre au journal d'annexe : ..................\n", fontEntete);
//            Phrase nume = new Phrase("Order number in the journal of the store ", bf10);
//            Chunk underline = new Chunk("               ", fontEntete);
//            underline.setUnderline(0.2f, -2f);
//            ph1 = new Phrase();
//            ph1.add(num);
//            ph1.add(nume);
//            ph1.add(underline);
//            cell1 = new PdfPCell();
//            cell1.setBorderColor(BaseColor.WHITE);
//            cell1.addElement(ph1);
//            cell1.setColspan(2);
//            // cell1.setPaddingBottom(2f);
//            cell1.setPaddingTop(2f);
//            firstTable.addCell(cell1);
//            doc.add(firstTable);
            PdfPCell cell1;
            float relativewidth2[] = {5, 9};
            PdfPTable or = new PdfPTable(relativewidth2);
            or.setWidthPercentage(100);
            cell1 = new PdfPCell();
            cell1.setBorderColor(BaseColor.WHITE);
            or.addCell(cell1);
            Chunk ordre = new Chunk("ORDRE DE SORTIE N", fontBig);
            Chunk expo = new Chunk("o", fontEntete);
            expo.setTextRise(5f);
            Chunk un = new Chunk(String.valueOf(noOrdre)+"                 \n", fontBig);
        //    Chunk in = new Chunk("INCOMING ORDER No.", fontBig2);
            Chunk under = new Chunk("                            \n", fontBig);
            under.setUnderline(0.5f, -2f);
            Phrase ph = new Phrase();
            ph.add(ordre);
            ph.add(expo);
            ph.add(un);
         //   ph.add(in);
            ph.add(under);
            cell1 = new PdfPCell();
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.addElement(ph);
            or.addCell(cell1);
            doc.add(or);
            StringBuilder stb = new StringBuilder();
            stb.append("EXERCICE ...................................................................\n")
                    .append("CHAPITRE "+ String.valueOf(noChapitre)+"                        DU BUDGET\n")
                    .append("IMPUTATION BUDGETAIRE : ..................................\n")
                    .append("........................................................................................\n")
                    .append("........................................................................................\n")
                    .append("........................................................................................\n");
            Paragraph exer = new Paragraph(stb.toString(), bf10);
            exer.setAlignment(Element.ALIGN_CENTER);
            doc.add(exer);
            Calendar ca = Calendar.getInstance();
            // ca.setTime(dateFacture);
            String month = ca.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);
            month = WordUtils.capitalize(month);
            int year = ca.get(Calendar.YEAR);
            int day = ca.get(Calendar.DATE);
            Chunk seront = new Chunk("Seront portés en sortie, dans les écritures du dépositaire comptable, les matières et objets ci-dessous désignés",bf12);
            exer = new Paragraph(seront);
            exer.setAlignment(Element.ALIGN_CENTER);
            doc.add(exer);
            Paragraph objetp = new Paragraph(new Chunk(objet, fontEntete));
            objetp.setAlignment(Element.ALIGN_CENTER);
            doc.add(objetp);

            doc.add(new Chunk("\n"));
            float relativewidth3[] = {1, 2, 7, 3, 3, 3, 4, 4, 3};
            PdfPTable table = new PdfPTable(relativewidth3);
            table.setWidthPercentage(100);
            //table.setHeaderRows(2);
            PdfPCell cell = new PdfPCell();
            Paragraph par = new Paragraph("NUMEROS", bf12b);
            par.setAlignment(Element.ALIGN_CENTER);
            Chunk numer = new Chunk();
            cell.addElement(par);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("DESIGNATION DES MATIERES ET OBJETS\n", bf12b);
            Chunk eng = new Chunk("DESCRIPTION OF MATERIAL", bf12i);
            Paragraph p = new Paragraph();
            p.add(numer);
            p.add(eng);
            p.setAlignment(Element.ALIGN_CENTER);
            cell = new PdfPCell();
            cell.addElement(p);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setRowspan(2);
            table.addCell(cell);

            numer = new Chunk("ESPECES\nDES\nUNITES\n", bf12b);
            eng = new Chunk("UNIT OF\n MEASURE", bf12i);
            cell = new PdfPCell();
            p = new Paragraph();
            p.add(numer);
            p.add(eng);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("QUANTITES\n", bf12b);
            eng = new Chunk("QUANTITIES", bf12i);
            p = new Paragraph();
            p.add(numer);
            p.add(eng);
            p.setAlignment(Element.ALIGN_CENTER);
            cell = new PdfPCell();
            cell.addElement(p);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("PRIX DE L'UNITE\n", bf12b);
            cell = new PdfPCell();
            eng = new Chunk("UNIT PRICE", bf12i);
            p = new Paragraph();
            p.add(numer);
            p.add(eng);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRowspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("VALEURS", bf12b);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("NUMERO DE LA PIECE\n JUSTIFICATIVE\n D'ENTREE CORRESPONDANT", bf12b);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRowspan(2);
            cell.setRotation(90);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("DE FOLIO\n DU GRAND LIVRE JOURNAL", bf12b);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRotation(90);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("D'ORDRE\n DE LA CLASSE\n DE NOMENCLATURE\n SOMMAIRE", bf12b);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRotation(90);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("PARTIELLES", bf12b);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("PAR CLASSE DE LA NOMENCLATURE SOMMAIRE", bf12b);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Affectation> affectations1 = affectationDao.findAllByOrdreSortie(os);
            System.out.println("=================================== Avant le merge");
            System.out.println(affectations1);
            List<Affectation> affectations = affectationDao.findAllByOrdreSortie(os);
            //List<Commande> commandes = commandeDao.findByBon(bc);
//            if (os.getEtatType() == EtatType.Encour) {
//                for (Affectation affectation : affectations) {
//                    Article art = affectation.getArticle();
//                    int qteI = art.getQuantite();
//                    int qteF = affectation.getNombre();
//                    art.setQuantite(-qteI + qteF);
//                    articleDao.update(art);
//                }
//            }
            System.out.println("\n\n\n JE SUIS ICI ============= "+affectations.size()+ "pour l'id" + os.getId());
            System.out.println(affectations);
            System.out.println("\n========================================\n");
            Set<String> cat = new TreeSet<>();
            cat = getCategories(affectations);
            Map<String, List<Affectation>> data = transform(affectations);
            double prixFinal = 0.0;
            int index = 1;
            for (String cat1 : cat) {
                double prixTemp = 0.0;
                List<Affectation> temp = data.get(cat1);
                Collections.sort(temp, new Comparator<Affectation>() {

                    @Override
                    public int compare(Affectation t, Affectation t1) {
                        return t.getArticle().getDesignation().compareToIgnoreCase(t1.getArticle().getDesignation());
                    }

                });

                for (int i = 0; i < temp.size(); i++) {
                    // int artQ = temp.get(i).getArticle().getQuantite();
                    int nombrCommande = temp.get(i).getNombre();
                    //Article art = new Article();
                    //art = temp.get(i).getArticle();
                    //art.setQuantite(artQ+nombrCommande);
                    //articleDao.update(art);
                    table.addCell(Util.createDotedValueCell("", bf12));
                    table.addCell(Util.createDotedValueCell(temp.get(i).getCategorie(), bf12));
                    table.addCell(Util.createDotedValueCell(temp.get(i).getArticle().getDesignation(), bf12));
                    table.addCell(Util.createDotedValueCell(temp.get(i).getArticle().getConditionnement(), bf12));
                    table.addCell(Util.createDotedValueCell(String.valueOf(nombrCommande), bf12));
                    table.addCell(Util.createDotedValueCell(String.valueOf(temp.get(i).getPrix()), bf12));
                    table.addCell(Util.createDotedValueCell(String.valueOf(nombrCommande * temp.get(i).getPrix()), bf12));
                    prixTemp += temp.get(i).getNombre() * temp.get(i).getPrix();
                    if (i != temp.size() - 1) {
                        table.addCell(Util.createDotedValueCell("", bf12));
                        table.addCell(Util.createDotedValueCell("", bf12));
                    } else {

                        table.addCell(Util.createDotedValueCell(String.valueOf((int) Math.floor(prixTemp)), bf12));
                        table.addCell(Util.createDotedValueCell("", bf12));
                    }
                }
                prixFinal += prixTemp;
            }
            p = new Paragraph("TOTAL", fontEntete);
            cell1 = new PdfPCell();
            p.setAlignment(Element.ALIGN_CENTER);
            cell1.addElement(p);
            cell1.setColspan(6);
            table.addCell(cell1);
            p = new Paragraph(String.valueOf((int) Math.floor(prixFinal)), fontEntete);
            cell1 = new PdfPCell();
            p.setAlignment(Element.ALIGN_CENTER);
            cell1.addElement(p);
            //cell1.setColspan(6);
            table.addCell(cell1);
            p = new Paragraph(String.valueOf((int) Math.floor(prixFinal)), fontEntete);
            cell1 = new PdfPCell();
            p.setAlignment(Element.ALIGN_CENTER);
            cell1.addElement(p);
            //cell1.setColspan(6);
            table.addCell(cell1);

            table.completeRow();

            doc.add(table);
            int nombre = affectations.size();
            int prix = (int) Math.floor(prixFinal);
            String prixS = FrenchNumberToWords.convert(prix);
            Chunk arete = new Chunk("Arrêté le présent ordre de sortie à ", bf1);
            String nomc = FrenchNumberToWords.convert(nombre);
            Chunk art = new Chunk(nombre + " (" + nomc.toUpperCase() + ")", bf1b);
            Chunk article = new Chunk(" articles, évalués à la somme de: ", bf1);
            Chunk prixL = new Chunk(prixS.toUpperCase() + " FRANCS CFA", bf1b);
            p = new Paragraph();
            p.add(arete);
            p.add(art);
            p.add(article);
            p.add(prixL);
            doc.add(p);
            doc.add(new Chunk("\n"));

            float relativewidth4[] = {6, 1, 6};
            PdfPTable firstTable;
            firstTable = new PdfPTable(relativewidth4);
            firstTable.setWidthPercentage(100);
            //firstTable.ke
            firstTable.keepRowsTogether(0, firstTable.getLastCompletedRowIndex());
            Calendar c = Calendar.getInstance();
            c.setTime(dateOrdre);
            String moi = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);
            moi = WordUtils.capitalize(moi);
            int annee = c.get(Calendar.YEAR);
            int jour = c.get(Calendar.DATE);
            numer = new Chunk(" Le dépositaire comptable délivrera et portera en sortie dans ses écritures les matièrs et objets désignés ci dessus \n", bf1);
            Chunk date = new Chunk(" A Douala , le ", fontEntete);
            Chunk value = new Chunk(jour + " " + moi + " " + annee + "\n", fontEntete);
            Chunk ordo = new Chunk("L'Odonnateur-Matières", fontEntete);
            p = new Paragraph();
            p.add(numer);
            p.add(date);
            p.add(value);
            p.add(ordo);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell1 = new PdfPCell();
            cell1.addElement(p);
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstTable.addCell(cell1);
            cell1 = new PdfPCell();
            cell1.setBorderColor(BaseColor.WHITE);
            firstTable.addCell(cell1);

            // numer = new Chunk("\n");
            //ordo = new Chunk("\n    DECLARATION DE PRISE EN CHARGE\n", fontEntete);
            numer = new Chunk("     Les matières et objets désignés ci-dessus ont été délivrés et portés en sortie\n", bf1);
            Chunk chef = new Chunk("Le Dépositaire Comptable", fontEntete);
            p = new Paragraph();
           // p.add(ordo);
            p.add(numer);
            p.add(date);
            p.add(value);
            //p.add(ordo);
            p.add(chef);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell1 = new PdfPCell();
            cell1.addElement(p);
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstTable.addCell(cell1);
            doc.add(firstTable);
            if (os.getEtatType() != EtatType.acheve) {
                os.setEtatType(EtatType.acheve);
                ordreSortieDao.update(os);
            }
        } catch (DocumentException | DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
