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
	  
	  public static void print() {
	    try { String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime());
	    	  FILE = "c:/temp/Kontoauszug_" + timeStamp + ".pdf";
		      Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(FILE));
		      document.open();
		      addMetaData(document);
		      addTitlePage(document);
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
	    document.newPage();
	  }
}
