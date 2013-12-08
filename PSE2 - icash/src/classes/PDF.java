package classes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.sql.*;


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
	  private static Font catFont;
	  private static Font subFont;
	  private static Font smallBold;
	  
	  public static void print(Account a, Bank b, Customer c, AccountType at) {
	    try { 
	    	  catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	  	      subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	  	      smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	    	  String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss").format(Calendar.getInstance().getTime());
		      File file = new File("Kontoauszug_" + timeStamp + ".pdf"); 
	    	  Document document = new Document();
		      PdfWriter.getInstance(document, new FileOutputStream(file));
		      document.open();
		      addMetaData(document);
		      
		      Transaction[] t = new Transaction[a.getTransactions().size()];
		      a.getTransactions().toArray(t);
		      
		      addTitlePage(document, b, c, a, at);
		      addTable(document, t, a);
		      document.close();
		      Desktop.getDesktop().open(file);
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
	  
	  private static void addTable(Document document, Transaction[] t, Account a) {
		  PdfPTable table = new PdfPTable(5); // 5 columns
		  PdfPCell header5 = new PdfPCell(new Paragraph("date", smallBold));
          PdfPCell header4 = new PdfPCell(new Paragraph("description", smallBold));
          PdfPCell header2 = new PdfPCell(new Paragraph("outgoing Account", smallBold));
          PdfPCell header3 = new PdfPCell(new Paragraph("incoming Account", smallBold));
		  PdfPCell header1 = new PdfPCell(new Paragraph("Amount", smallBold));
		  
          table.addCell(header1);
          table.addCell(header2);
          table.addCell(header3);
          table.addCell(header4);
          table.addCell(header5);
          table.setHeaderRows(1);

		  for (int i=0; i<t.length; i++) {
			  
			PdfPCell cell1, cell2, cell3, cell4, cell5;
			cell5 = new PdfPCell(new Paragraph(t[i].getDate().toString()));
          	cell4 = new PdfPCell(new Paragraph(t[i].getDescription()));
          	try {
          		cell3 = new PdfPCell(new Paragraph("" + t[i].getOutgoingAccount().getId()));
          	}
          	catch (SQLException e) {
          		cell3 = new PdfPCell(new Paragraph("<?>"));
          	}
          	try {
          		cell2 = new PdfPCell(new Paragraph("" + t[i].getIncomingAccount().getId()));
          	}
          	catch (SQLException e) {
          		cell2 = new PdfPCell(new Paragraph("<?>"));
          	}
          	cell1 = new PdfPCell(new Paragraph(Convert.toEuro(t[i].getAmount())));

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
