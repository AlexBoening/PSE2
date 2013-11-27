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
	  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	  
	  public static void main(String[] args) {
		  String[][] transactions = new String[5][5];
	      for (int i=0; i<transactions.length; i++) {
	    	  transactions[0][i] = "Row1 Line"; //+ (i+1);
	    	  transactions[1][i] = "Row2 Line"; //+ (i+1);
	    	  transactions[2][i] = "Row3 Line"; //+ (i+1);
	    	  transactions[3][i] = "Row4 Line"; //+ (i+1);
	    	  transactions[4][i] = "Row5 Line"; //+ (i+1);
	      }
		  print(transactions);
	  }
	  
	  public static void print(String[][] transactions) {
	    try { String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime());
	    	  FILE = "c:/temp/Kontoauszug_" + timeStamp + ".pdf";
		      Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(FILE));
		      document.open();
		      addMetaData(document);
		      // initializing data
		      Bank b = new Bank();
		      Customer c = new Customer();
		      Account a = new Account();
		      AccountType ac = new AccountType();
		      
		      b.setDescription("Icash");
		      b.setBlz(5242345);
		      c.setFirstName("Hans");
		      c.setLastName("Wurst");
		      a.setId(1);
		      ac.setDescription("Tagesgeld");
		      
		      addTitlePage(document, b, c, a, ac);
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

	  private static void addTitlePage(Document document, Bank b, Customer c, Account a, AccountType ac)
	      throws DocumentException {
		    Paragraph preface = new Paragraph();
		    preface.add(new Paragraph("Your Icash bank account e-statment", catFont));
		    preface.add(new Paragraph("created: " + new Date(), smallBold));
		    addEmptyLine(preface, 1);
		    preface.add(new Paragraph("Bank: " + b.getDescription(), subFont));
		    preface.add(new Paragraph("Bank code: " + String.valueOf(b.getBlz())));
		    addEmptyLine(preface, 1);
		    preface.add(new Paragraph("Account details:", subFont));
		    preface.add(new Paragraph("Account owner: " + c.getFirstName() + " " + c.getLastName()));
		    preface.add(new Paragraph("Account number: " + a.getId()));  
		    preface.add(new Paragraph("Account type: " + ac.getDescription()));
		    addEmptyLine(preface, 1);
		    preface.add(new Paragraph("Transactions:", subFont));
		    addEmptyLine(preface, 1);
	    document.add(preface);
	    // document.newPage();
	  }
	  
	  private static void addTable(Document document, String[][] transactions) {
		  PdfPTable table = new PdfPTable(5); // 5 columns
		  PdfPCell header5 = new PdfPCell(new Paragraph("date", smallBold));
          PdfPCell header4 = new PdfPCell(new Paragraph("description", smallBold));
          PdfPCell header2 = new PdfPCell(new Paragraph("outgoing Account", smallBold));
          PdfPCell header3 = new PdfPCell(new Paragraph("incoming Account", smallBold));
		  PdfPCell header1 = new PdfPCell(new Paragraph("Ammount", smallBold));
		  
          table.addCell(header1);
          table.addCell(header2);
          table.addCell(header3);
          table.addCell(header4);
          table.addCell(header5);
          table.setHeaderRows(1);

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
		  } catch(DocumentException e) {			
			e.printStackTrace();
          }	
	  }
	  
	  private static void addEmptyLine(Paragraph paragraph, int number) {
		    for (int i = 0; i < number; i++) {
		      paragraph.add(new Paragraph(" "));
		    }
		  }
}
