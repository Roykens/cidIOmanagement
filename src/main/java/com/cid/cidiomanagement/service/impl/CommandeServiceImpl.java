package com.cid.cidiomanagement.service.impl;

import com.cid.cidiomanagement.dao.IBonCommandeDao;
import com.cid.cidiomanagement.dao.ICommandeDao;
import com.cid.cidiomanagement.dao.IPrestataireDao;
import com.cid.cidiomanagement.entities.BonCommande;
import com.cid.cidiomanagement.entities.Commande;
import com.cid.cidiomanagement.entities.Prestataire;
import com.cid.cidiomanagement.service.ICommandeService;
import com.cid.cidiomanagement.service.ServiceException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.royken.generic.dao.DataAccessException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.ClassPathResource;
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

    @Override
    public BonCommande saveOrUpdateBon(BonCommande bonCommande) throws ServiceException {

        try {
            if (bonCommande.getId() == null) {
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
                bonCommandeDao.delete(comm);
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
    public void produireOrdreEntree(Long bonCommandeId, OutputStream stream) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void produceHeader(Document doc) throws Exception {
        Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
        // Définition de l'entete du document
        StringBuilder builder = new StringBuilder("REPUBLIQUE DU CAMEROUN\n");
        builder.append("Paix-Travail-Patrie\n");
        builder.append("----------\n");
        builder.append("MINISTERE DES FINANCES\n");
        builder.append("----------\n");
        builder.append("SECRETARIAT GENERAL\n");
        builder.append("----------\n");
        builder.append("CENTRE NATIONAL DE DEVELOPPEMENT DE L'INFORMATIQUE (CENADI)\n");
        builder.append("----------\n");
        builder.append("CENTRE INFORMATIQUE DE DOUALA\n");
        builder.append("----------\n");

        Paragraph frecnch = new Paragraph(new Phrase(builder.toString(), bf12));
        frecnch.setAlignment(Element.ALIGN_CENTER);
        builder = new StringBuilder();
        builder.append("REPUBLIC OF CAMEROON\n");
        builder.append("Peace-Work-Fatherland\n");
        builder.append("----------\n");
        builder.append("MINISTRY OF FINANCES\n");
        builder.append("----------\n");
        builder.append("SECRETARY GENERAL\n");
        builder.append("----------\n");
        builder.append("NATIONAL CENTRE FOR THE DEVELOPMENT OF COMPUTER SERVICES\n");
        builder.append("----------\n");
        builder.append("DOUALA COMPUTER CENTRE\n");
        builder.append("----------\n");

        Paragraph eng = new Paragraph(new Phrase(builder.toString(), bf12));
        eng.setAlignment(Element.ALIGN_CENTER);
//        builder = new StringBuilder();
//        builder.append("B.P. / P.O. Box: 46 Maroua\n");
//       
//        builder.append("Email: institutsupsahel.uma@gmail.com\n");
//        builder.append("Site: http://www.uni-maroua.citi.cm");
//        Paragraph coordonnees = new Paragraph(new Phrase(builder.toString(), bf12));
//        coordonnees.setAlignment(Element.ALIGN_CENTER);
        float widths2[] = {3, 4, 3};
        PdfPTable header = new PdfPTable(widths2);
        header.setWidthPercentage(100);
        PdfPCell cell1;
        cell1 = new PdfPCell(frecnch);
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell1);
        URL url = new ClassPathResource("entete.png").getURL();
        //java.awt.Image img = ImageIO.read(new File("logo4.png"));
        //Image logo = Image.getInstance(img, null);
        Image logo = Image.getInstance(url);
        logo.scalePercent(65f);
        Paragraph p = new Paragraph();
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(new Paragraph(new Chunk(logo, 0, -15, true)));
        PdfPCell cel = new PdfPCell(p);
        cel.setBorderColor(BaseColor.WHITE);
        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
        cel.setVerticalAlignment(Element.ALIGN_CENTER);
        header.addCell(cel);
        cell1 = new PdfPCell(eng);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBorderColor(BaseColor.WHITE);
        header.addCell(cell1);
//        cell1 = new PdfPCell(coordonnees);
//        cell1.setColspan(10);
//        cell1.setBorderColor(BaseColor.WHITE);
//        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//        header.addCell(cell1);
        doc.add(header);

        doc.add(new Chunk("\n\n\n\n"));

    }

    @Override
    public void produceTrash(Long idBon, String objet, OutputStream outputStream) throws ServiceException {
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
            produceHeader(doc);
            produireProcesVerbal(doc);

            doc.newPage();
            produceHeader(doc);
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

            doc.add(new Chunk("\n\n"));
            doc.add(new Chunk(tab + tab + tab + tab));

            doc.add(new Chunk("L'an deux mil quinze et le ........................... du mois de .................. à ..............heures\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk("s'est tenue au Centre Informatique de Douala, une commission composée de:\n", bf12));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("1) ....................................................................... Gestion de crédits ou son  ", bf12));
            doc.add(new Chunk("(Président)\n", fontEntete));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("2) .............................................................................. Comptable matières ", bf12));
            doc.add(new Chunk("(Rapporteur)\n", fontEntete));
            doc.add(new Chunk(tab + tab + tab));
            doc.add(new Chunk("3) .............................................................................. Ingénieur éventuel ", bf12));
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
            doc.add(new Chunk("montant de ...........................................................................................................................\n", bf12));
            doc.add(new Chunk(tab + tab));
            doc.add(new Chunk(".............................................................................................................................................\n", bf12));
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
            doc.add(new Chunk(tab + tab + tab + tab));

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
            Font bf1 = new Font(Font.FontFamily.TIMES_ROMAN, 5);
            Font fontEntete = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            List<Commande> commandes = commandeDao.findByBon(bon);
            for (Commande commande : commandes) {
                System.out.println(commande);
            }

            Chunk ch = new Chunk("BCA N", fontEntete);
            Chunk expo = new Chunk("o", bf1);
            expo.setTextRise(5f);
            doc.add(expo);
            Chunk underline = new Chunk(" /           /MINFI/SG/CENADI/CID", fontEntete);
            underline.setUnderline(0.1f, -2f);
            Paragraph p1 = new Paragraph();
            p1.add(ch);
            p1.add(expo);
            p1.add(underline);
            p1.setAlignment(Element.ALIGN_CENTER);
            doc.add(p1);
            doc.add(new Chunk("\n\n"));
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
            doc.add(new Chunk("Adresse .....", bf12));
            doc.add(new Chunk(prestataire.getAdresse(), bf12));
            doc.add(new Chunk("  BP       ", bf12));
            doc.add(new Chunk("  Douala       ", bf12));
            doc.add(new Chunk("  Tél : ", bf12));
            doc.add(new Chunk(prestataire.getTelephone() + "\n", bf12));
            doc.add(new Chunk("N", bf12));
            doc.add(expo);
            doc.add(new Chunk(" Contribuable : ", bf12));
            doc.add(new Chunk(prestataire.getNoContribuable() + "\n", bf12));

            Chunk underline2 = new Chunk("Objet : ", fontEntete);
            underline.setUnderline(0.1f, -2f);
            Paragraph p3 = new Paragraph(underline2);
            doc.add(p3);
            doc.add(new Chunk(objet, bf12));
            doc.add(new Chunk("\n"));

            float relatiewidth[] = {3, 5, 1, 3, 3};
            PdfPTable products = new PdfPTable(relatiewidth);
            products.setWidthPercentage(100);
            products.addCell(createBonDefaultFHeader("Références", bf12));
            products.addCell(createBonDefaultFHeader("Désignation,", bf12));
            products.addCell(createBonDefaultFHeader("QTE", bf12));
            products.addCell(createBonDefaultFHeader("P.U.", bf12));
            products.addCell(createBonDefaultFHeader("PT", bf12));

            double prixTotal = 0;

            for (Commande commande : commandes) {

                products.addCell(createBonDefaultFHeader(commande.getArticle().getReference(), bf12));
                products.addCell(createBonDefaultFHeader(commande.getArticle().getDesignation(), bf12));
                products.addCell(createBonDefaultFHeader(String.valueOf(commande.getNombre()), bf12));
                products.addCell(createBonDefaultFHeader(commande.getArticle().getPrixUnitaire() + "", bf12));
                double prixTemp = commande.getNombre() * commande.getArticle().getPrixUnitaire();
                prixTotal += prixTemp;
                products.addCell(createBonDefaultFHeader(prixTemp + "", bf12));
            }

            PdfPTable footer = new PdfPTable(relatiewidth);
            PdfPCell cell1 = new PdfPCell();
            
            products.addCell(createFooterCell("Total HT", bf12));
            products.addCell(createFooterValueCell(prixTotal+"", bf12));
            
            double prixtva = prixTotal * 19.25 /100;
            products.addCell(createFooterCell("TVA 19.25%", bf12));
            products.addCell(createFooterValueCell(prixtva+"", bf12));
            products.addCell(createFooterCell("AIR 5.5%", bf12));
            
            double prixAir = prixTotal * 5.5 /100;
            products.addCell(createFooterValueCell(prixAir+"", bf12));
            products.addCell(createFooterCell("Net à percevoir", bf12));
            
            
            products.addCell(createFooterValueCell("    ", bf12));
            products.addCell(createFooterCell("Total TTC", bf12));
            products.addCell(createFooterValueCell("    ", bf12));
            doc.add(products);

            Chunk arete = new Chunk(tab + tab + "Arrête le présente Bon de Commande Administratif à la somme de :\n", bf12);
            doc.add(arete);
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
        } catch (DocumentException ex) {
            Logger.getLogger(CommandeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void produceOrdreEntree(Document doc){
        
    }

    private PdfPCell createBonDefaultFHeader(String content, Font bf) {
        PdfPCell cell = new PdfPCell(new Phrase(content, bf));
        //cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;

    }

    private PdfPCell createFooterCell(String content, Font bf) {

        PdfPCell cell = new PdfPCell(new Phrase(content + ".................................................................................", bf));
        //cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setColspan(4);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setBorderColorTop(BaseColor.WHITE);
        return cell;
    }

    private PdfPCell createFooterValueCell(String content, Font bf) {

        PdfPCell cell = new PdfPCell(new Phrase(content, bf));
        //cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4f);
        cell.setPaddingTop(5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColorBottom(BaseColor.WHITE);
        cell.setBorderColorTop(BaseColor.WHITE);
        return cell;
    }
}
