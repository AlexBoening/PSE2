package classes;

import java.io.FileOutputStream;
import java.util.Date;


import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PDF {

	  private static String FILE;
	  private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	  
	  public static void print(String[][] transactions) {
	    try { String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime());
	    	  FILE = "c:/temp/Kontoauszug_" + timeStamp + ".pdf";
		      Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(FILE));
		      document.open();
		      addMetaData(document);
		      addTitlePage(document);
		      addTable(document, transactions);
		      document.close();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
	  }

	 private static void addMetaData(Document document) {
	    document.addTitle("Kontoauszug");
	    document.addSubject("Kontoauszug");
	    document.addKeywords("Java, PDF, iText, Kontoauszug");
	    document.addAuthor("iCash");
	    document.addCreator("iCash");
	  }

	  private static void addTitlePage(Document document)
	      throws DocumentException {
	    Paragraph preface = new Paragraph();
	    preface.add(new Paragraph("Diest ist ein elektronischer Kontoauszug von ihrem Konto bei iCash!", catFont));
	    document.add(preface);
	    // document.newPage();
	  }
	  
	  private static void addTable(Document document, String[][] transactions) {
		  PdfPTable table = new PdfPTable(5); // 3 columns.

		  for (int i=0; i<transactions.length; i++) {
          PdfPCell cell1 = new PdfPCell(new Paragraph(transactions[0][i]));
          PdfPCell cell2 = new PdfPCell(new Paragraph(transactions[1][i]));
          PdfPCell cell3 = new PdfPCell(new Paragraph(transactions[2][i]));
          PdfPCell cell4 = new PdfPCell(new Paragraph(transactions[3][i]));
          PdfPCell cell5 = new PdfPCell(new Paragraph(transactions[4][i]));

          table.addCell(cell1);
          table.addCell(cell2);
          table.addCell(cell3);
          table.addCell(cell4);
          table.addCell(cell5);
		  }

          try {
			document.add(table);
          } catch (DocumentException e) {			
			e.printStackTrace();
          }
          //document.newPage();
	  }
}
