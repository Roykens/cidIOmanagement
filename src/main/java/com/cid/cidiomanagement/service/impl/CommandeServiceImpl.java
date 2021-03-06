package com.cid.cidiomanagement.service.impl;

import co.royken.convert.FrenchNumberToWords;
import com.cid.cidiomanagement.dao.IArticleDao;
import com.cid.cidiomanagement.dao.IBonCommandeDao;
import com.cid.cidiomanagement.dao.ICommandeDao;
import com.cid.cidiomanagement.dao.IPrestataireDao;
import com.cid.cidiomanagement.entities.Article;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.EtatType;
import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.ICommandeService;
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
 * @author royken
 */
@Transactional(readOnly = false)
public class CommandeServiceImpl implements ICommandeService {

    private ICommandeDao commandeDao;
    private IBonCommandeDao bonCommandeDao;
    private IPrestataireDao prestataireDao;
    private IArticleDao articleDao;

    private final String tab = "    ";

    public ICommandeDao getCommandeDao() {
        return commandeDao;
    }

    public void setCommandeDao(ICommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    public IBonCommandeDao getBonCommandeDao() {
        return bonCommandeDao;
    }

    public void setBonCommandeDao(IBonCommandeDao bonCommandeDao) {
        this.bonCommandeDao = bonCommandeDao;
    }

    public IPrestataireDao getPrestataireDao() {
        return prestataireDao;
    }

    public void setPrestataireDao(IPrestataireDao prestataireDao) {
        this.prestataireDao = prestataireDao;
    }

    public IArticleDao getArticleDao() {
        return articleDao;
    }

    public void setArticleDao(IArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public BonCommande saveOrUpdateBon(BonCommande bonCommande) throws ServiceException {

        try {
            if (bonCommande.getId() == null) {
                bonCommande.setEtat(EtatType.Encour);
                return bonCommandeDao.create(bonCommande);
            } else {
                return bonCommandeDao.update(bonCommande);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<BonCommande> findAllCommande() throws ServiceException {
        try {
            return bonCommandeDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deleteCommande(Long id) throws ServiceException {
        try {
            BonCommande comm = bonCommandeDao.findById(id);
            if (comm != null) {
                comm.setActive(false);
                bonCommandeDao.update(comm);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public BonCommande findBonById(Long id) throws ServiceException {
        try {
            return bonCommandeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Commande saveOrUpdateCommande(Commande commande) throws ServiceException {
        try {
            if (commande.getId() == null) {
                return commandeDao.create(commande);
            } else {
                return commandeDao.update(commande);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Commande findCommandeById(Long id) throws ServiceException {
        try {
            return commandeDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Commande> findAllByBon(Long id) throws ServiceException {
        try {
            BonCommande bonCommande = bonCommandeDao.findById(id);
            if (bonCommande != null) {
                return commandeDao.findByBon(bonCommande);
            } else {
                return null;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void produireBonCommande(Long bonCommandeId, OutputStream stream) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void produireOrdreEntree(Long bonCommandeId, OutputStream stream, String noFacture, Date dateFacture, int noOrdre, int noChapitre) throws ServiceException {
        try {
            BonCommande bc = bonCommandeDao.findById(bonCommandeId);
            Document doc = new Document();
            PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4);
            doc.open();
            Util.produceHeader(doc);
            produceOrdreEntree(bc, doc, noFacture, dateFacture, noOrdre, noChapitre);
            doc.close();

        } catch (DataAccessException | DocumentException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void produceBonCommande(Long idBon, String objet, OutputStream outputStream) throws ServiceException {
        try {
            BonCommande bon = bonCommandeDao.findById(idBon);
            if (bon == null) {
                throw new ServiceException("Service not found");
            }

            Prestataire p = bon.getPrestataire();
            if (p == null) {
                throw new ServiceException("Service not found");
            }
            Document doc = new Document();
            PdfWriter.getInstance(doc, outputStream);
            doc.setPageSize(PageSize.A4);
            doc.open();
            Util.produceHeader(doc);
            produireProcesVerbal(doc);
            doc.newPage();
            Util.produceHeader(doc);
            produceBon(doc, bon, objet, p);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produireProcesVerbal(Document doc) {
        try {
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 5);
            Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            doc.add(new Chunk("\n\n\n\n"));

            Chunk underline = new Chunk("PROCES VERBAL DE RECEPTION", fontEntete);
            underline.setUnderline(0.1f, -2f);
            Paragraph p1 = new Paragraph(underline);
            p1.setAlignment(Element.ALIGN_CENTER);
            doc.add(p1);

            doc.add(new Chunk("\n"));
            doc.add(new Chunk(tab + tab + tab + tab));

            doc.add(new Chunk("L'an deux mil quinze et le ........................... du mois de .................. à ..............heures\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk("s'est tenue au Centre Informatique de Douala, une commission composée de:\n\n", bf12));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("1) .......................................................................... Gestion de crédits ou son  ", bf12));
            doc.add(new Chunk("(Président)\n", fontEntete));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("2) .............................................................................. Comptable matières ", bf12));
            doc.add(new Chunk("(Rapporteur)\n", fontEntete));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("3) .................................................................................. Ingénieur éventuel ", bf12));
            doc.add(new Chunk("(Rapporteur)\n", fontEntete));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("4) ................................................................. Fournisseur et son représentant ", bf12));
            doc.add(new Chunk("(Membre)\n", fontEntete));
            doc.add(new Chunk(tab + tab + tab + tab));
            doc.add(new Chunk("En vue de la réception des fournitures objet du bon de commande administratif\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk("N ", bf12));
            Chunk expo = new Chunk("o", bf1);
            expo.setTextRise(5f);
            doc.add(expo);
            doc.add(new Chunk(" ............................. et de la facture ...................... N", bf12));
            // Chunk expo = new Chunk("o");
            //expo.setTextRise(5f);
            doc.add(expo);
            doc.add(new Chunk(" ..........................du ................. d'un\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk("montant de .............................................................................................................................\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk(".................................................................................................................................................\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk("Du prestataire (raison sociale) ..............................................................................................\n\n", bf12));

            doc.add(new Chunk(tab + tab + tab + tab));
            doc.add(new Chunk("Après vérifications des quantités et des spécifications techniques, la commission a\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk("déclaré lesdites prestations conformes aux règles de l'art et par conséquent recevables.\n", bf12));
            doc.add(new Chunk("\n\n"));

            doc.add(new Chunk("Le Gestionnaire de crédits", fontEntete));
            doc.add(new Chunk(tab + tab + tab + tab));

            doc.add(new Chunk("Le Comptable-matières", fontEntete));
            doc.add(new Chunk(tab + tab + tab + tab + tab + tab));

            doc.add(new Chunk("Le Fournisseur", fontEntete));

        } catch (DocumentException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceBon(Document doc, BonCommande bon, String objet, Prestataire prestataire) throws ServiceException, DataAccessException {
        System.out.println("\nLes données =============================================\n\n");
        System.out.println(bon);
        System.out.println(prestataire);
        System.out.println("Les données =============================================\n");
        try {
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 5);
            Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            List<Commande> commandes = commandeDao.findByBon(bon);
//            for (Commande commande : commandes) {
//                System.out.println(commande);
//            }

            Calendar cal = Calendar.getInstance();
            cal.setTime(bon.getDateCommande());
            int annee = cal.get(Calendar.YEAR);
            String toto = String.valueOf(annee);
            String ann = toto.substring(1, toto.length());
            Chunk ch = new Chunk("BCA N", fontEntete);
            Chunk expo = new Chunk("o", bf1);
            Chunk chc = new Chunk(ann, fontEntete);
            expo.setTextRise(5f);
            doc.add(expo);
            Chunk underline = new Chunk(" /           /MINFI/SG/CENADI/CID", fontEntete);
            underline.setUnderline(0.1f, -2f);
            Paragraph p1 = new Paragraph();
            p1.add(ch);
            p1.add(expo);
            p1.add(chc);
            p1.add(underline);
            p1.setAlignment(Element.ALIGN_CENTER);
            doc.add(p1);
            doc.add(new Chunk("\n"));
            Chunk ch2 = new Chunk("BON DE COMMANDE ADMINISTRATIF", fontEntete);
            Paragraph p = new Paragraph(ch2);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);

            Chunk ch3 = new Chunk("Pour les matières et objets ci-après : \n", fontEntete);
            Paragraph p2 = new Paragraph(ch3);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p2);
            doc.add(new Chunk("\n"));
            doc.add(new Chunk("Nom ou raison Sociale du Prestataire : ", bf12));
            doc.add(new Chunk(prestataire.getNom() + "\n", bf12));
            doc.add(new Chunk("Adresse . ", bf12));
            doc.add(new Chunk(prestataire.getAdresse(), bf12));
            //doc.add(new Chunk("  BP       ", bf12));
            //doc.add(new Chunk("  Douala       ", bf12));
            doc.add(new Chunk("  Tél : ", bf12));
            doc.add(new Chunk(prestataire.getTelephone() + "\n", bf12));
            doc.add(new Chunk("N", bf12));
            doc.add(expo);
            doc.add(new Chunk(" Contribuable : ", bf12));
            doc.add(new Chunk(prestataire.getNoContribuable() + "\n", bf12));

            Chunk underline2 = new Chunk("Objet : ", fontEntete);
            underline2.setUnderline(0.1f, -2f);
            Paragraph p3 = new Paragraph(underline2);
            //p3.add(underline2);
            p3.add(new Chunk(bon.getObjet(), bf12));
            doc.add(p3);
            doc.add(new Chunk("\n"));

            float relatiewidth[] = {3, 5, 1, 3, 3};
            PdfPTable products = new PdfPTable(relatiewidth);
            products.setWidthPercentage(100);
            products.addCell(Util.createBonDefaultFHeader("REFERENCES", bf12));
            products.addCell(Util.createBonDefaultFHeader("DESIGNATION", bf12));
            products.addCell(Util.createBonDefaultFHeader("QTE", bf12));
            products.addCell(Util.createBonDefaultFHeader("P.U.", bf12));
            products.addCell(Util.createBonDefaultFHeader("PT", bf12));

            double prixTotal = 0;

            for (Commande commande : commandes) {

                products.addCell(Util.createBonDefaultFHeader(commande.getArticle().getReference(), bf10));
                products.addCell(Util.createBonDefaultFHeader(commande.getArticle().getDesignation(), bf10));
                products.addCell(Util.createBonDefaultFHeader(String.valueOf(commande.getNombre()), bf10));
                products.addCell(Util.createBonDefaultFHeader(commande.getPrixArticle() + "", bf10));
                double prixTemp = commande.getNombre() * commande.getPrixArticle();
                prixTotal += prixTemp;
                products.addCell(Util.createBonDefaultFHeader((int)Math.floor(prixTemp) + "", bf10));
            }

            PdfPTable footer = new PdfPTable(relatiewidth);
            PdfPCell cell1 = new PdfPCell();

            Chunk to = new Chunk("Total HT .................................................................................", bf10);
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthBottom(0.5f);
            // cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setColspan(4);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            //cell1.setBorderColorLeft(BaseColor.BLACK);

            // products.addCell(createFooterCell("Total HT", bf12));
            products.addCell(cell1);
            to = new Chunk((int) Math.floor(prixTotal) + "", bf10);
            
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthBottom(0.5f);
            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            products.addCell(cell1);

            double prixtva = prixTotal * 19.25 / 100;

            to = new Chunk("TVA 19.25% .................................................................................", bf10);
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthTop(0.5f);

            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setColspan(4);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            //cell1.setBorderColorLeft(BaseColor.BLACK);
            cell1.setBorderColorTop(BaseColor.WHITE);

            //products.addCell(createFooterCell("TVA 19.25%", bf12));
            products.addCell(cell1);

            to = new Chunk((int) Math.floor(prixtva) + "", bf10);
            
            
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthBottom(0.5f);
            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setBorderColorTop(BaseColor.WHITE);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            products.addCell(cell1);

            //    products.addCell(createFooterValueCell(prixtva+"", bf12));
            String air = "AIR " + bon.getPrestataire().getAir() + "% .................................................................................";

            to = new Chunk(air, bf10);
            
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthTop(0.5f);
            // cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setColspan(4);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            //cell1.setBorderColorLeft(BaseColor.BLACK);
            cell1.setBorderColorTop(BaseColor.WHITE);

            products.addCell(cell1);

            double prixAir = prixTotal * bon.getPrestataire().getAir() / 100;

            to = new Chunk((int) Math.floor(prixAir) + "", bf10);
            
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthBottom(0.5f);
            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setBorderColorTop(BaseColor.WHITE);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            products.addCell(cell1);

            //products.addCell(createFooterValueCell(prixAir+"", bf12));
            to = new Chunk("Net à percevoir .................................................................................", bf10);
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthTop(0.5f);
            // cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setColspan(4);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            //cell1.setBorderColorLeft(BaseColor.BLACK);
            cell1.setBorderColorTop(BaseColor.WHITE);

            products.addCell(cell1);

            to = new Chunk((int) Math.floor((prixTotal - prixAir)) + "", bf10);
            
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthBottom(0.5f);
            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setBorderColorBottom(BaseColor.WHITE);
            cell1.setBorderColorTop(BaseColor.WHITE);
            products.addCell(cell1);

            //  products.addCell(createFooterValueCell((prixTotal - prixAir)+"", bf12));
            to = new Chunk("Total TTC .................................................................................", bf10);
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthTop(0.5f);
            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setColspan(4);
            //cell1.setBorderColorBottom(BaseColor.WHITE);
            cell1.setBorderColorTop(BaseColor.WHITE);
            // cell1.setBorderColorLeft(BaseColor.WHITE);

            products.addCell(cell1);

            to = new Chunk((int) Math.floor((prixTotal + prixtva)) + "", bf10);
            cell1 = new PdfPCell();
            cell1.addElement(to);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setBorderWidthBottom(0.5f);
            //cell1.setPaddingBottom(4f);
            //cell1.setPaddingTop(5f);
            cell1.setBorderColorTop(BaseColor.WHITE);
            products.addCell(cell1);
            // products.addCell(createFooterValueCell((prixTotal + prixtva)+"", bf12));
            doc.add(products);

            Chunk arete = new Chunk(tab + tab + "Arrête le présente Bon de Commande Administratif à la somme de : ", bf12);
            doc.add(arete);
            // String montant = String.valueOf(prixTotal + prixtva);
            int entier = (int) Math.floor(prixtva + prixTotal);
            int decimal = (int) Math.floor(((prixtva + prixTotal) - entier) * 100.0f);
            String montant = FrenchNumberToWords.convert(entier);
//            if (decimal > 0) {
//                montant += ", " + FrenchNumberToWords.convert(decimal);
//            }
            Chunk mont = new Chunk(montant + " Francs CFA", fontEntete);
            doc.add(mont);
            doc.add(new Chunk("\n"));
            Chunk delai = new Chunk(tab + tab + "Délai de livraison : ", fontEntete);
            doc.add(delai);
            doc.add(new Chunk(" Quatorze jours à partir de la date de signature\n", bf12));
            doc.add(new Chunk("\n\n"));
            Chunk fa = new Chunk("Fait à ................, le", bf12);
            Chunk date = new Chunk("                                     ", bf12);
            date.setUnderline(0.1f, -2f);
            Paragraph par = new Paragraph();
            par.add(fa);
            par.add(date);
            par.setAlignment(Element.ALIGN_RIGHT);
            doc.add(par);
            doc.add(new Chunk("\n"));

            doc.add(new Chunk(tab + tab + " Le Fournisseur                             ", fontEntete));
            doc.add(new Chunk(tab + tab + tab + tab + tab + "                                                        L'Ordonnateur", fontEntete));

//            
//            cell1.addElement(new Chunk("Total HT ......................................", bf1));
//            cell1.setColspan(4);
//            cell1.setBorderColorBottom(BaseColor.WHITE);
//            footer.addCell(cell1);
//            PdfPCell cell2 = new PdfPCell();
//            cell2.addElement(new Chunk("    ", bf1));
//            cell2.setBorderColorBottom(BaseColor.WHITE);
//            footer.addCell(cell2);
            if (bon.getEtat() != EtatType.acheve) {
                bon.setEtat(EtatType.acheve);
                bonCommandeDao.update(bon);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceOrdreEntree(BonCommande bc, Document doc, String noFacture, Date dateFacture, int noOrdre, int noChapitre) {
        try {
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
            Font bf12i = new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC);
            Font bf10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
            Font bf1b = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
            Font fontBig = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
            Font fontBig2 = new Font(Font.FontFamily.TIMES_ROMAN, 14);

            float relativewidth[] = {4, 2, 6};
            PdfPTable firstTable = new PdfPTable(relativewidth);
            firstTable.setWidthPercentage(100);
            Phrase post = new Phrase("POSTE COMPTABLE ........................................\n", fontEntete);
            Phrase postEng = new Phrase("ACCOUNTING POST\n .................................................................", bf10);
            Phrase ph1 = new Phrase();
            ph1.add(post);
            ph1.add(postEng);
            PdfPCell cell1 = new PdfPCell(ph1);
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.setColspan(2);
            //cell1.setPaddingBottom(2f);
            cell1.setPaddingTop(2f);
            firstTable.addCell(cell1);

            Phrase code = new Phrase("CODE POSTE COMPTABLE\n", fontEntete);
            Phrase codeEng = new Phrase("ACCOUNTING POST CODE\n", bf10);
            Phrase codeA = new Phrase("CODE ANNEXE...............................\n", fontEntete);
            Phrase codeAe = new Phrase("ANNEXE CODE", bf10);
            ph1 = new Phrase();
            ph1.add(code);
            ph1.add(codeEng);
            ph1.add(new Phrase("\n"));
            ph1.add(codeA);
            ph1.add(codeAe);
            cell1 = new PdfPCell(ph1);
            cell1.setRowspan(3);
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            //cell1.setPaddingBottom(2f);
            cell1.setPaddingTop(2f);
            firstTable.addCell(cell1);

            Phrase serv = new Phrase("SERVICE COMPTABLE AUPRES DE ..............\n", fontEntete);
            Phrase serve = new Phrase("ACCOUNTING SERVICE TO \n ...................................................................", bf10);

            ph1 = new Phrase();
            ph1.add(serv);
            ph1.add(serve);
            cell1 = new PdfPCell(ph1);
            cell1.setBorderColor(BaseColor.WHITE);
            //cell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            cell1.setColspan(2);
            //cell1.setPaddingBottom(2f);
            cell1.setPaddingTop(2f);
            firstTable.addCell(cell1);

            Phrase num = new Phrase("Numéro d'ordre au journal d'annexe : ..................\n", fontEntete);
            Phrase nume = new Phrase("Order number in the journal of the store ", bf10);
            Chunk underline = new Chunk("               ", fontEntete);
            underline.setUnderline(0.2f, -2f);
            ph1 = new Phrase();
            ph1.add(num);
            ph1.add(nume);
            ph1.add(underline);
            cell1 = new PdfPCell();
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.addElement(ph1);
            cell1.setColspan(2);
            // cell1.setPaddingBottom(2f);
            cell1.setPaddingTop(2f);
            firstTable.addCell(cell1);
            doc.add(firstTable);
            float relativewidth2[] = {5, 9};
            PdfPTable or = new PdfPTable(relativewidth2);
            or.setWidthPercentage(100);
            cell1 = new PdfPCell();
            cell1.setBorderColor(BaseColor.WHITE);
            or.addCell(cell1);
            Chunk ordre = new Chunk("ORDRE D'ENTREE N", fontBig);
            Chunk expo = new Chunk("o", fontEntete);
            expo.setTextRise(5f);
            Chunk un = new Chunk(String.valueOf(noOrdre) + "                       \n", fontBig);
            Chunk in = new Chunk("INCOMING ORDER No.", fontBig2);
            Chunk under = new Chunk("                            \n", fontBig);
            under.setUnderline(0.5f, -2f);
            Phrase ph = new Phrase();
            ph.add(ordre);
            ph.add(expo);
            ph.add(un);
            ph.add(in);
            ph.add(under);
            cell1 = new PdfPCell();
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.addElement(ph);
            or.addCell(cell1);
            doc.add(or);
            StringBuilder stb = new StringBuilder();
            stb.append("EXERCICE ...................................................................\n")
                    .append("CHAPITRE " + String.valueOf(noChapitre) + "                        DU BUDGET\n")
                    .append("IMPUTATION BUDGETAIRE : ..................................\n")
                    .append("........................................................................................\n")
                    .append("........................................................................................\n")
                    .append("........................................................................................\n");
            Paragraph exer = new Paragraph(stb.toString(), bf10);
            exer.setAlignment(Element.ALIGN_CENTER);
            doc.add(exer);
            Calendar ca = Calendar.getInstance();
            ca.setTime(dateFacture);
            String month = ca.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);
            month = WordUtils.capitalize(month);
            int year = ca.get(Calendar.YEAR);
            int day = ca.get(Calendar.DATE);
            Chunk seront = new Chunk("Seront portés en entrée, dans les écritures comptables du Chef de Poste Comptable de la comptabilité matières, les matières et objets ci-dessous désignés provenant de la Facture No " + noFacture + " du " + day + " " + month + " " + year, bf10);
            doc.add(seront);

            float relativewidth3[] = {1, 2, 7, 2, 3, 3, 4, 4, 3};
            PdfPTable table = new PdfPTable(relativewidth3);
            table.setWidthPercentage(100);
            //table.setHeaderRows(2);
            PdfPCell cell = new PdfPCell();
            Paragraph par = new Paragraph("NUMEROS", bf12);
            par.setAlignment(Element.ALIGN_CENTER);
            Chunk numer = new Chunk();
            cell.addElement(par);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("DESIGNATION DES MATIERES ET OBJETS\n", bf12);
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

            numer = new Chunk("ESPECES\nDES\nUNITES\n", bf12);
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

            numer = new Chunk("QUANTITES\n", bf12);
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

            numer = new Chunk("PRIX DE L'UNITE\n", bf12);
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

            numer = new Chunk("VALEURS", bf12);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("NUMERO DE LA PIECE\n JUSTIFICATIVE\n DE SORTIE", bf12);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRowspan(2);
            cell.setRotation(90);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("DE FOLIO\n DU GRAND LIVRE JOURNAL", bf12);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRotation(90);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("D'ORDRE\n DE LA CLASSE\n DE NOMENCLATURE\n SOMMAIRE", bf12);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setRotation(90);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("PARTIELLES", bf12);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            numer = new Chunk("PAR CLASSE DE LA NOMENCLATURE SOMMAIRE", bf12);
            cell = new PdfPCell();
            p = new Paragraph(numer);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            List<Commande> commandes = commandeDao.findByBon(bc);
            if (bc.getEtat() == EtatType.Encour) {
                for (Commande commande : commandes) {
                    Article art = commande.getArticle();
                    int qteI = art.getQuantite();
                    int qteF = commande.getNombre();
                    art.setQuantite(qteI + qteF);
                    articleDao.update(art);
                }
            }
            Set<String> cat = new TreeSet<>();
            cat = getCategories(commandes);
            Map<String, List<Commande>> data = transform(commandes);
            double prixFinal = 0.0;
            int index = 1;
            for (String cat1 : cat) {
                double prixTemp = 0.0;
                List<Commande> temp = data.get(cat1);
                Collections.sort(temp, new Comparator<Commande>() {

                    @Override
                    public int compare(Commande t, Commande t1) {
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
                    table.addCell(Util.createDotedValueCell(String.valueOf(temp.get(i).getPrixArticle()), bf12));
                    table.addCell(Util.createDotedValueCell(String.valueOf(nombrCommande * temp.get(i).getPrixArticle()), bf12));
                    prixTemp += temp.get(i).getNombre() * temp.get(i).getPrixArticle();
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
            int nombre = commandes.size();
            int prix = (int) Math.floor(prixFinal);
            String prixS = FrenchNumberToWords.convert(prix);
            Chunk arete = new Chunk("Arrêté le présent ordre d'entrée à ", bf1);
            String nomc = FrenchNumberToWords.convert(nombre);
            Chunk art = new Chunk(nombre + " (" + nomc.toUpperCase() + ")", bf1b);
            Chunk article = new Chunk(" article(s), évalué(s) à la somme de: ", bf1);
            Chunk prixL = new Chunk(prixS.toUpperCase() + " FRANCS CFA", bf1b);
            p = new Paragraph();
            p.add(arete);
            p.add(art);
            p.add(article);
            p.add(prixL);
            doc.add(p);
            doc.add(new Chunk("\n"));

            float relativewidth4[] = {6, 1, 6};
            firstTable = new PdfPTable(relativewidth4);
            firstTable.setWidthPercentage(100);
            firstTable.keepRowsTogether(0, firstTable.getLastCompletedRowIndex());
            Calendar c = Calendar.getInstance();
            String moi = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.FRANCE);
            moi = WordUtils.capitalize(moi);
            int annee = c.get(Calendar.YEAR);
            int jour = c.get(Calendar.DATE);
            numer = new Chunk(" Le chef de poste comptable de la comptabilité matières prendra en charge les matièrs et objets désignés dans le tableau, dont j'ai vérifié la concordance avec la Facture No " + noFacture + " du " + day + " " + month + " " + year + "\n", bf1);
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
            ordo = new Chunk("\n    DECLARATION DE PRISE EN CHARGE\n", fontEntete);
            numer = new Chunk("     Le Chef de poste comptable de la comptabilité matières déclare avoir pris en charge et mis en approvisionnement les matières et objets dans le tableau ci-dessus.\n", bf1);
            Chunk chef = new Chunk("Le Chef de poste Comptable", fontEntete);
            p = new Paragraph();
            p.add(ordo);
            p.add(numer);
            p.add(date);
            p.add(value);
            p.add(ordo);
            p.add(chef);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            cell1 = new PdfPCell();
            cell1.addElement(p);
            cell1.setBorderColor(BaseColor.WHITE);
            cell1.setVerticalAlignment(Element.ALIGN_CENTER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            firstTable.addCell(cell1);
            doc.add(firstTable);
            if (bc.getEtat() != EtatType.acheve) {
                bc.setEtat(EtatType.acheve);
                bonCommandeDao.update(bc);
            }
        } catch (DocumentException | DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Set<String> getCategories(List<Commande> commandes) {
        Set<String> result = new HashSet<>();
        for (Commande commande : commandes) {
            result.add(commande.getCategorie());
        }
        return result;
    }

    private static Map<String, List<Commande>> transform(List<Commande> commandes) {

        Map<String, List<Commande>> result = new HashMap<>();
        Set<String> cat = getCategories(commandes);
        for (String cat1 : cat) {
            List<Commande> temp = new ArrayList<>();
            for (Commande com : commandes) {
                if (com.getCategorie().equals(cat1)) {
                    temp.add(com);
                }
            }
            result.put(cat1, temp);
        }
        return result;
    }

    @Override
    public void deleteBonCommande(Long idBon) throws ServiceException {
        try {
            BonCommande bc = bonCommandeDao.findById(idBon);
            if (bc != null) {
                if (bc.getEtat() != EtatType.acheve) {
                    bc.setActive(false);
                    bonCommandeDao.update(bc);
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
