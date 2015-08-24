package com.cid.cidiomanagement.service.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.net.URL;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class Util {
    
        public static void produceHeader(Document doc) throws Exception {
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
        builder = new StringBuilder();
        
        builder.append(" ");
        Paragraph coordonnees = new Paragraph(new Phrase(builder.toString(), bf12));
        coordonnees.setAlignment(Element.ALIGN_CENTER);
        cell1 = new PdfPCell(coordonnees);
        cell1.setColspan(10);
        cell1.setBorderColor(BaseColor.WHITE);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.addCell(cell1);
        doc.add(header);

        doc.add(new Chunk("\n\n"));

    }
        
        public static PdfPCell createBonDefaultFHeader(String content, Font bf) {
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

    public static PdfPCell createFooterCell(String content, Font bf) {

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

    public static PdfPCell createFooterValueCell(String content, Font bf) {

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
