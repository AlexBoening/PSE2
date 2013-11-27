package classes;


import java.sql.*;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import classes.Account;
import classes.Customer;
import classes.Transaction;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;
import classes.*;

public class CustomerClient {
		
	static Display display;
	static Shell shell;
	
	static GridLayout layoutMainClient, layoutLogin, layoutOneColumn;

	static StackLayout stackLayoutMain, stackLayoutContent;
	
	static GridData griddataWindow, griddataHeader, griddataNavigation, griddataContent, griddataLogoutButton, griddataButton
					, griddataLabel, griddataText;
	
	static Composite compositeLogin, compositeMainClient, compositeHeader, compositeNavigation, compositeContent, compositeWelcomePage, compositeViewTransaction
					,compositePerformTransaction, compositeDepositPage,compositeWithdrawPage;
	
	static Button buttonLogin, buttonLogout, buttonMenuViewTransaction, buttonCommitViewTransaction, buttonMenuPerformPage, buttonMenuDepositPage
					, buttonMenuWithdrawPage, buttonCommitWithdraw, buttonCommitDeposit, buttonCommitPerformTransaction;
	
	static Image imageLogo, imageTablePull;
	
	private static boolean securityMode = true;
	private static int customerId;
	private static int accountId;
	private static String password;
	private static String server;
	private static Label CurrentBalance;
	private static Label LabelStatusLine;
		   
	
/*    GridData Captiondata = new GridData(GridData.FILL, GridData.FILL,true, false);
    Captiondata.horizontalSpan = 2;
    Captiondata.horizontalAlignment = GridData.CENTER;
		    
    GridData Textdata = new GridData(GridData.BEGINNING, GridData.CENTER,false, false);
	Textdata.widthHint = 400;
	Textdata.verticalIndent= 15;
	
    GridData Labeldata = new GridData(GridData.BEGINNING, GridData.CENTER,false, false);
    Labeldata.widthHint = 150;
    Labeldata.verticalIndent= 15;
    		    
    GridData Separatordata = new GridData(GridData.FILL, GridData.FILL,true, false);
    Separatordata.horizontalSpan = 2;
    Separatordata.verticalIndent= 15;*/
	
	 public static void main(String[] args) {
		 
		 	initializeShell();
		 	
		 	initializeGridData();
		 	
		 	initializeComposites();
		 	
		 	fillCompositeLogin();
		 	
		 	fillCompositeMainClient();
		 	
		 	fillCompositeWelcomePage();
		 	
		 	fillcompositeViewTransaction();
		 	
		 	fillcompositePerformTransaction();
		 	
		 	fillcompositeDepositPage();
		 	
		 	fillcompositeWithdrawPage();
		 	
		 	//set WelcomePage to be the first thing to see
		 	stackLayoutMain.topControl=compositeLogin;
		 	stackLayoutContent.topControl = compositeWelcomePage;
	        compositeContent.layout();
		 	
		    //Events
		    
	        
	        
		    buttonLogin.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	  server = ((Text)event.widget.getData("server")).getText();
		        	  password = ((Text)event.widget.getData("password")).getText();
		        	  accountId = Convert.toInt(((Text)event.widget.getData("user")).getText());
		        	  customerId = getCustomer(accountId);
		        	  if (customerId != 0) {
		        		  CurrentBalance.setText(getBalance(accountId));
		        		  stackLayoutMain.topControl = compositeMainClient;
			          	shell.layout();
		        	  }
			          //compositeNavigation.layout();
			        }
			      });
		    
		    buttonMenuViewTransaction.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		    	    compositeViewTransaction = new Composite(compositeContent, SWT.NONE);
				    compositeViewTransaction.setBackground(new Color(display,255,255,255));
				    compositeViewTransaction.setLayout(layoutMainClient);
		        	fillcompositeViewTransaction();
		        	stackLayoutContent.topControl = compositeViewTransaction;
		        	compositeContent.layout();
		        }
		      });
		    
		    buttonMenuPerformPage.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	stackLayoutContent.topControl = compositePerformTransaction;
			          compositeContent.layout();
			        }
			      });
		    
		    buttonMenuDepositPage.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	stackLayoutContent.topControl = compositeDepositPage;
			          compositeContent.layout();
			        }
			      });
		    
		    buttonMenuWithdrawPage.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	stackLayoutContent.topControl = compositeWithdrawPage;
			          compositeContent.layout();
			        }
			      });
		    
		    buttonLogout.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent arg0) {
		    		display.dispose();
		    	}
		    });
		    
		   // buttonDeactivateAccount.addListener(SWT.Selection, new Listener() {
		       // public void handleEvent(Event event) {
			          //do something here to deactivate selected account
			     //   }
			    //  });
		    
		    buttonCommitPerformTransaction.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	  int toAccount = Convert.toInt(((Text)event.widget.getData("toAccount")).getText());
		        	  String amount = ((Text)event.widget.getData("amount")).getText();
		        	  String description = ((Text)event.widget.getData("description")).getText();
		        	  transferMoney(accountId, toAccount, amount, description);
		        	  CurrentBalance.setText(getBalance(accountId));
		        }
			});
		    
		    buttonCommitDeposit.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	  String amount = ((Text)event.widget.getData("amount")).getText();
		        	  String description = ((Text)event.widget.getData("description")).getText();
		        	  transferMoney(getBankAccount(accountId), accountId, amount, description);
		        	  CurrentBalance.setText(getBalance(accountId));
			        }
			});
		    
		    buttonCommitWithdraw.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	  String amount = ((Text)event.widget.getData("amount")).getText();
		        	  String description = ((Text)event.widget.getData("description")).getText();
		        	  transferMoney(accountId, getBankAccount(accountId), amount, description);
		        	  CurrentBalance.setText(getBalance(accountId));
			    }
			});
		    
		    //Events
		    
		    shell.pack();
		    shell.open();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    display.dispose();
}

	 private static void fillcompositeDepositPage() {
		
		 GridData DepositCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);		    
		 DepositCompositeData.horizontalSpan = 2;
		    Label DepositCaptionLabel = new Label(compositeDepositPage, SWT.NONE);
		    DepositCaptionLabel.setText("Deposit your Money");
		    DepositCaptionLabel.setLayoutData(DepositCompositeData);
		    
		    Label SepPerform3 = new Label(compositeDepositPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform3.setBackground(new Color(display,255,255,255));
		    SepPerform3.setLayoutData(DepositCompositeData);
		    
		    Label DepositAmountLabel = new Label(compositeDepositPage,SWT.NONE);
		    DepositAmountLabel.setText("Amount:");
		    DepositAmountLabel.setLayoutData(griddataLabel);
			Text DepositAmountText = new Text(compositeDepositPage, SWT.SINGLE | SWT.BORDER);
			DepositAmountText.setLayoutData(griddataText);
			
			Label DepositDescriptionLabel = new Label(compositeDepositPage, SWT.NONE);
			DepositDescriptionLabel.setText("Description:");
			DepositDescriptionLabel.setLayoutData(griddataLabel);
			Text DepositDescriptionText = new Text(compositeDepositPage, SWT.SINGLE | SWT.BORDER);
			DepositDescriptionText.setLayoutData(griddataText);			
		    
			DepositCaptionLabel.pack();
		    
		    Label SepPerform4 = new Label(compositeDepositPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform4.setBackground(new Color(display,255,255,255));
		    SepPerform4.setLayoutData(DepositCompositeData);
		    
		    buttonCommitDeposit = new Button(compositeDepositPage, SWT.PUSH);
		    buttonCommitDeposit.setText("Commit");
		    buttonCommitDeposit.setBackground(new Color(display, 31, 78, 121));
		    buttonCommitDeposit.setLayoutData(griddataButton);
		    buttonCommitDeposit.setData("amount", DepositAmountText);
		    buttonCommitDeposit.setData("description", DepositDescriptionText);
	}

	private static void fillcompositeViewTransaction() {
		
		 GridData CreateAccountCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    
		    CreateAccountCompositeData.horizontalSpan = 2;
		    
		    Label PerformTransactionCaptionLabel = new Label(compositeViewTransaction, SWT.NONE);
		    PerformTransactionCaptionLabel.setText("View Your Transaction");
		    PerformTransactionCaptionLabel.setLayoutData(CreateAccountCompositeData);
		    
		    Label SepPerform1 = new Label(compositeViewTransaction, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform1.setBackground(new Color(display,255,255,255));
		    SepPerform1.setLayoutData(CreateAccountCompositeData);
		    
		    final Table table = new Table(compositeViewTransaction,
		    		SWT.SINGLE | SWT.H_SCROLL |
		    		SWT.V_SCROLL | SWT.BORDER |
		    		SWT.FULL_SELECTION );
		    		// Drei Tabellenspalten erzeugen
		    		final TableColumn col1 = new TableColumn(table,SWT.RIGHT);
		    		col1.setText("Amount");
		    		col1.setWidth(100);
		    		final TableColumn col2 = new TableColumn(table,SWT.LEFT);
		    		col2.setText("Sender");
		    		col2.setWidth(100);
		    		final TableColumn col3 = new TableColumn(table,SWT.LEFT);
		    		col3.setText("Receiver");
		    		col3.setWidth(100);
		    		final TableColumn col4 = new TableColumn(table,SWT.LEFT);
		    		col4.setText("Description");
		    		col4.setWidth(150);
		    		final TableColumn col5 = new TableColumn(table,SWT.LEFT);
		    		col5.setText("Date");
		    		col5.setWidth(100);
		    		// Spaltenköpfe und Trennlinien sichtbar machen
		    		table.setHeaderVisible(true);
		    		table.setLinesVisible(true);
		    		
		    		GridData ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, true);
		    		table.setLayoutData(ViewCompositeData);
		    		
		    		if (accountId == 0)
		    			return;
		    		Account a = getAccount(accountId);
		    		if (a != null) 
		    			try {
		    				Transaction[] t = new Transaction[a.getTransactions().size()];
		    				a.getTransactions().toArray(t);
		    		
		    				for (int i=0; i<t.length; i++) {
		    					TableItem item = new TableItem(table, SWT.NONE);
		    					String[] column = new String[5];
		    					column[0] = classes.Convert.toEuro(t[i].getAmount());
		    					column[1] = t[i].getOutgoingAccount().getCustomer().getFirstName() + " " +
		    								t[i].getOutgoingAccount().getCustomer().getLastName();
		    					column[2] = t[i].getIncomingAccount().getCustomer().getFirstName() + " " +
		    								t[i].getIncomingAccount().getCustomer().getLastName();
		    					column[3] = t[i].getDescription();
		    					column[4] = t[i].getDate().toString();
		    					item.setText(column);
		    				}
		    			}
		    			catch (SQLException e) {
		    				// ToDo: Fehlerbehandlung!
		    			}
		    			else {
		    				// Fehlermeldung: Account nicht gefunden!
		    			}
		    		compositeViewTransaction.pack();
	}

	private static void fillcompositePerformTransaction() {
		 
		 GridData ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);

		    
		    ViewCompositeData.horizontalSpan = 2;
		    Label PerformTransactionCaptionLabel = new Label(compositePerformTransaction, SWT.NONE);
		    PerformTransactionCaptionLabel.setText("Perform your Transaction");
		    PerformTransactionCaptionLabel.setLayoutData(ViewCompositeData);
		   
		    PerformTransactionCaptionLabel.pack();
		    	    
		    Label SepPerform3 = new Label(compositePerformTransaction, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform3.setBackground(new Color(display,255,255,255));
		    SepPerform3.setLayoutData(ViewCompositeData);
		    
		    Label PerformTransactionToAccountLabel = new Label(compositePerformTransaction,SWT.NONE);
		    PerformTransactionToAccountLabel.setText("To Acc.:");
		    PerformTransactionToAccountLabel.setLayoutData(griddataLabel);
			Text PerformTransactionToAccountText = new Text(compositePerformTransaction, SWT.SINGLE | SWT.BORDER);
			PerformTransactionToAccountText.setLayoutData(griddataText);
			
			Label PerformTransactionBlzLabel = new Label(compositePerformTransaction, SWT.NONE);
			PerformTransactionBlzLabel.setText("BLZ:");
			PerformTransactionBlzLabel.setLayoutData(griddataLabel);
			Text PerformTransactionBlzText = new Text(compositePerformTransaction, SWT.SINGLE | SWT.BORDER);
			PerformTransactionBlzText.setLayoutData(griddataText);		
			
			Label PerformTransactionAmountLabel = new Label(compositePerformTransaction, SWT.NONE);
			PerformTransactionAmountLabel.setText("Amount:");
			PerformTransactionAmountLabel.setLayoutData(griddataLabel);
			Text PerformTransactionAmountText = new Text(compositePerformTransaction, SWT.SINGLE | SWT.BORDER);
			PerformTransactionAmountText.setLayoutData(griddataText);	
			
			Label PerformTransactionDescriptionLabel = new Label(compositePerformTransaction, SWT.NONE);
			PerformTransactionDescriptionLabel.setText("Description:");
			PerformTransactionDescriptionLabel.setLayoutData(griddataLabel);
			Text PerformTransactionDescriptionText = new Text(compositePerformTransaction, SWT.SINGLE | SWT.BORDER);
			PerformTransactionDescriptionText.setLayoutData(griddataText);	
		    
			PerformTransactionCaptionLabel.pack();
		    
		    Label SepPerform4 = new Label(compositePerformTransaction, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform4.setBackground(new Color(display,255,255,255));
		    SepPerform4.setLayoutData(ViewCompositeData);
		    
		    buttonCommitPerformTransaction = new Button(compositePerformTransaction, SWT.PUSH);
		    buttonCommitPerformTransaction.setText("Commit");
		    buttonCommitPerformTransaction.setBackground(new Color(display, 31, 78, 121));
		    buttonCommitPerformTransaction.setLayoutData(griddataButton);	
		    buttonCommitPerformTransaction.setData("toAccount", PerformTransactionToAccountText);
		    buttonCommitPerformTransaction.setData("blz", PerformTransactionBlzText);
		    buttonCommitPerformTransaction.setData("amount", PerformTransactionAmountText);
		    buttonCommitPerformTransaction.setData("description", PerformTransactionDescriptionText);
		    
	}
	
	private static void fillcompositeWithdrawPage() {
		 GridData CreateCustomerCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    
		    CreateCustomerCompositeData.horizontalSpan = 2;
		    Label WithdrawCaptionLabel = new Label(compositeWithdrawPage, SWT.NONE);
		    WithdrawCaptionLabel.setText("Withdraw your Money");
		    WithdrawCaptionLabel.setLayoutData(CreateCustomerCompositeData);
		    
		    Label SepPerform3 = new Label(compositeWithdrawPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform3.setBackground(new Color(display,255,255,255));
		    SepPerform3.setLayoutData(CreateCustomerCompositeData);
		    
		    Label WithdrawAmountLabel = new Label(compositeWithdrawPage,SWT.NONE);
		    WithdrawAmountLabel.setText("Amount:");
		    WithdrawAmountLabel.setLayoutData(griddataLabel);
			Text WithdrawAmountText = new Text(compositeWithdrawPage, SWT.SINGLE | SWT.BORDER);
			WithdrawAmountText.setLayoutData(griddataText);
			
			Label WithdrawDescriptionLabel = new Label(compositeWithdrawPage, SWT.NONE);
			WithdrawDescriptionLabel.setText("Description:");
			WithdrawDescriptionLabel.setLayoutData(griddataLabel);
			Text WithdrawDescriptionText = new Text(compositeWithdrawPage, SWT.SINGLE | SWT.BORDER);
			WithdrawDescriptionText.setLayoutData(griddataText);			
		    
			WithdrawCaptionLabel.pack();
		    
		    Label SepPerform4 = new Label(compositeWithdrawPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform4.setBackground(new Color(display,255,255,255));
		    SepPerform4.setLayoutData(CreateCustomerCompositeData);
		    
		    buttonCommitWithdraw = new Button(compositeWithdrawPage, SWT.PUSH);
		    buttonCommitWithdraw.setText("Commit");
		    buttonCommitWithdraw.setBackground(new Color(display, 31, 78, 121));
		    buttonCommitWithdraw.setLayoutData(griddataButton);
		    buttonCommitWithdraw.setData("amount", WithdrawAmountText);
		    buttonCommitWithdraw.setData("description", WithdrawDescriptionText);
	}

	private static void fillCompositeWelcomePage() {
		 
		 GridData WelcomeCompositeData = new GridData(GridData.BEGINNING, GridData.FILL,true, false);
		    WelcomeCompositeData.horizontalSpan = 2;
		    Label CaptionWelcomePage = new Label(compositeWelcomePage, SWT.NONE);
		    CaptionWelcomePage.setText("Welcome to ICash!");
		    CaptionWelcomePage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionWelcomePage.setLayoutData(WelcomeCompositeData);
		
		    Label labelForImage = new Label(compositeWelcomePage, SWT.None);
		    final GridData griddataCaption = new GridData(GridData.FILL, GridData.FILL,false, false);
		    griddataCaption.heightHint=236;
		    griddataCaption.widthHint=310;
		    
		    labelForImage.setBackgroundImage(imageTablePull);
		    labelForImage.setLayoutData(griddataCaption);
		    
	}

	private static void fillCompositeMainClient() {

		//Header
		final GridData griddataCaption = new GridData(GridData.FILL, GridData.FILL,false, false);
	    griddataCaption.heightHint=58;
	    griddataCaption.widthHint=625;
		
	    Label LabelHorizontal = new Label(compositeHeader,SWT.NONE);
	    LabelHorizontal.setBackgroundImage(imageLogo);
	    //LabelHorizontal.setText("iCash - The Best Bank");
	    //LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
	    //LabelHorizontal.setBackground(new Color(display, 200,200,200));
	    LabelHorizontal.setLayoutData(griddataCaption);
	    
	    //Logout Button
	    GridData griddataLogoutButton = new GridData(GridData.FILL, GridData.CENTER, true, false);
	    griddataLogoutButton.horizontalAlignment = GridData.END;
	    buttonLogout = new Button(compositeHeader, SWT.PUSH);
	    buttonLogout.setLayoutData(griddataLogoutButton);
	    buttonLogout.setBackground(new Color(display, 31, 78, 121));
	    buttonLogout.setText("Log out!");
	    
	    //Status Line
	    final GridData griddataStatusLine = new GridData(GridData.FILL, GridData.FILL,false, false);
	    griddataCaption.heightHint=58;
	    griddataCaption.widthHint=625;
	    
	    LabelStatusLine = new Label(compositeHeader, SWT.HORIZONTAL);
	    LabelStatusLine.setBackground(new Color(display, 200,200,200));
	    //LabelStatusLine.setText("Here could be the status code");
	    LabelStatusLine.setLayoutData(griddataStatusLine);

	    //Navigation
	    final GridData griddataMenuContent = new GridData(GridData.FILL, GridData.CENTER,true, false);
	    
	    buttonMenuViewTransaction = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuViewTransaction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuViewTransaction.setText("View Transaction");
		    buttonMenuViewTransaction.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuViewTransaction.setLayoutData(griddataMenuContent);
	    
	    Label placeholder4 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder4.setBackground(new Color(display, 200,200,200));
		    placeholder4.setLayoutData(griddataMenuContent);
	    
	    buttonMenuPerformPage = new Button(compositeNavigation, SWT.PUSH);
	    buttonMenuPerformPage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    buttonMenuPerformPage.setText("Perform Transaction");
	    buttonMenuPerformPage.setBackground(new Color(display, 31, 78, 121));
	    buttonMenuPerformPage.setLayoutData(griddataMenuContent);
	    
	    Label placeholder5 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder5.setBackground(new Color(display, 200,200,200));
		    placeholder5.setLayoutData(griddataMenuContent);
	    
//	    final Button buttonCreateCustomer = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuDepositPage = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuDepositPage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuDepositPage.setText("Deposit");
		    buttonMenuDepositPage.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuDepositPage.setLayoutData(griddataMenuContent);
	    
	    Label placeholder6 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder6.setBackground(new Color(display, 200,200,200));
		    placeholder6.setLayoutData(griddataMenuContent);
		    
		    buttonMenuWithdrawPage = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuWithdrawPage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuWithdrawPage.setText("Withdraw");
		    buttonMenuWithdrawPage.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuWithdrawPage.setLayoutData(griddataMenuContent);
	    
	    Label placeholder7 = new Label(compositeNavigation, SWT.NONE | SWT.HORIZONTAL);
		    placeholder7.setBackground(new Color(display, 200,200,200));
		    placeholder7.setLayoutData(griddataMenuContent);
	    
	    Label placeholder8 = new Label(compositeNavigation, SWT.NONE | SWT.HORIZONTAL);
		    placeholder8.setBackground(new Color(display, 200,200,200));
		    placeholder8.setLayoutData(griddataMenuContent);
		    
		    Label CurrentBalanceText = new Label(compositeNavigation, SWT.END);
		    CurrentBalanceText.setBackground(new Color(display, 200,200,200));
		    griddataMenuContent.verticalAlignment = GridData.FILL;
		    CurrentBalanceText.setText("Current Balance");
		    CurrentBalanceText.setLayoutData(new GridData(SWT.FILL, SWT.END, true, true));
		    
		    //GridData test  = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		    
		    CurrentBalance = new Label(compositeNavigation, SWT.FILL);
		    CurrentBalance.setBackground(new Color(display, 70,200,230));
		    CurrentBalance.setAlignment(SWT.HORIZONTAL);
		    griddataMenuContent.verticalAlignment = GridData.FILL;
		    if (accountId != 0)
		        CurrentBalance.setText(getBalance(accountId));
		    CurrentBalance.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	}

	private static void fillCompositeLogin()
	 {
		 
		    
		    final GridData griddataCaption = new GridData(GridData.FILL, GridData.FILL,false, false);
			    griddataCaption.heightHint=58;
			    griddataCaption.widthHint=625;
			    griddataCaption.horizontalSpan=5;
		    
		    final GridData griddataDescription = new GridData(GridData.FILL, GridData.FILL,false, false);
		    	griddataDescription.horizontalSpan=2;
		    
		    final GridData griddataTexts = new GridData(GridData.FILL, GridData.FILL,false, false);
		    	griddataTexts.horizontalSpan=3;
		    
		    final GridData griddataLoginButton = new GridData(GridData.FILL, GridData.CENTER,false, false);
			    griddataLoginButton.horizontalSpan=1;
			    griddataLoginButton.heightHint = 35;
		    
			final GridData griddataStatusLine = new GridData(GridData.FILL, GridData.FILL, false, false);
				griddataStatusLine.heightHint = 30;
				griddataStatusLine.widthHint = 625;
				griddataStatusLine.horizontalSpan = 5;
				
		    Label LoginCaption = new Label(compositeLogin,SWT.NONE);
		    LoginCaption.setBackgroundImage(imageLogo);
			    //LoginCaption.setText("iCash - The Best Bank");
			    //LoginCaption.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
			    LoginCaption.setLayoutData(griddataCaption);
		    
			LabelStatusLine = new Label(compositeLogin,SWT.NONE);
			LabelStatusLine.setLayoutData(griddataStatusLine);
			//LabelStatusLine.setText("Here could be the status code");
			
		    Label ServerLabel = new Label(compositeLogin, SWT.NONE);
			    ServerLabel.setText("Server:");
			    ServerLabel.setLayoutData(griddataDescription);
		    
		    Text ServerText = new Text(compositeLogin,SWT.BORDER);
		    	ServerText.setText("http://localhost:9998");
		    	ServerText.setLayoutData(griddataTexts);
		    
		    Label UserLabel = new Label(compositeLogin, SWT.NONE);
			    UserLabel.setText("Account:");
			    UserLabel.setLayoutData(griddataDescription);
		    
		    Text UserText = new Text(compositeLogin,SWT.BORDER);
		    	UserText.setLayoutData(griddataTexts);
		    
		    Label PasswortLabel = new Label(compositeLogin, SWT.NONE);
			    PasswortLabel.setText("Password:");
			    PasswortLabel.setLayoutData(griddataDescription);
		    
		    Text PasswordText = new Text(compositeLogin,SWT.BORDER);
		    	PasswordText.setLayoutData(griddataTexts);
		    
		    Label placeholder1 = new Label(compositeLogin,SWT.NONE);
			    placeholder1.setBackground(new Color(display,200,200,200));
			    placeholder1.setText("");
			    placeholder1.setLayoutData(griddataLoginButton);
		    
		    Label placeholder2 = new Label(compositeLogin,SWT.NONE);
			    placeholder2.setText("");
			    placeholder2.setBackground(new Color(display,200,200,200));
			    placeholder2.setLayoutData(griddataLoginButton);
		    
		    Label placeholder3 = new Label(compositeLogin,SWT.NONE);
			    placeholder3.setText("");
			    placeholder3.setBackground(new Color(display,200,200,200));
			    placeholder3.setLayoutData(griddataLoginButton);
		    
//		    final Button loginButton = new Button(compositeLogin,SWT.PUSH);
		    buttonLogin = new Button(compositeLogin,SWT.PUSH);
			buttonLogin.setText("Login NOW!");
			buttonLogin.setLayoutData(griddataLoginButton);
		    buttonLogin.setData("server", ServerText);
		    buttonLogin.setData("user", UserText);
		    buttonLogin.setData("password", PasswordText);
		    compositeLogin.pack();
	 }
	
	private static void initializeComposites() {
		 
		 compositeLogin = new Composite(shell,0);
		    compositeLogin.setBackground(new Color(display,200,200,200));
		    compositeLogin.setLayoutData(griddataWindow);
		    compositeLogin.setLayout(layoutLogin);
		 
		 compositeMainClient = new Composite(shell, 0);
		    compositeMainClient.setBackground(new Color(display,255,255,255));
		    compositeMainClient.setLayoutData(griddataWindow);
		    compositeMainClient.setLayout(layoutMainClient);
		    
	    compositeHeader = new Composite(compositeMainClient, 0);
		    compositeHeader.setBackground(new Color(display,200,200,200));	
		    compositeHeader.setLayoutData(griddataHeader);
		    compositeHeader.setLayout(layoutMainClient);
		
	    compositeNavigation = new Composite(compositeMainClient, 0);
		    compositeNavigation.setBackground(new Color(display,200,200,200));
		    compositeNavigation.setBounds(100,200,200,200);
		    compositeNavigation.setLayoutData(griddataNavigation);
		    ((GridData)compositeNavigation.getLayoutData()).widthHint=150;
		    compositeNavigation.setLayout(layoutOneColumn);
		    
	    compositeContent = new Composite(compositeMainClient, 0);
		    compositeContent.setBackground(new Color(display,255,255,255));
		    compositeContent.setLayoutData(griddataContent);
		    compositeContent.setLayout(stackLayoutContent);
		    
	    compositeWelcomePage = new Composite(compositeContent, SWT.NONE);
		    compositeWelcomePage.setBackground(new Color(display,255,255,255));
		    compositeWelcomePage.setLayout(layoutMainClient);
		    
	    compositeViewTransaction = new Composite(compositeContent, SWT.NONE);
		    compositeViewTransaction.setBackground(new Color(display,255,255,255));
		    compositeViewTransaction.setLayout(layoutMainClient);
		    
	    compositePerformTransaction = new Composite(compositeContent, SWT.NONE);
		    compositePerformTransaction.setBackground(new Color(display,255,255,255));
		    compositePerformTransaction.setLayout(layoutMainClient);
		    
	    compositeDepositPage = new Composite(compositeContent, SWT.NONE);
		    compositeDepositPage.setBackground(new Color(display,255,255,255));
		    compositeDepositPage.setLayout(layoutMainClient);
		    
		compositeWithdrawPage = new Composite(compositeContent, SWT.NONE);
			compositeWithdrawPage.setBackground(new Color(display,255,255,255));
			compositeWithdrawPage.setLayout(layoutMainClient);
	}
	
	private static void initializeGridData() {

		griddataWindow = new GridData(GridData.FILL, GridData.CENTER,true, true);
		    griddataWindow.horizontalAlignment=GridData.FILL;
		    griddataWindow.verticalAlignment=GridData.FILL;
		    griddataWindow.horizontalSpan=5;
		
	    griddataHeader = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddataHeader.horizontalSpan = 2;
		    
	    griddataNavigation = new GridData();
		    griddataNavigation.verticalAlignment = GridData.FILL;
		    griddataNavigation.grabExcessVerticalSpace = true;
		
	    griddataContent = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddataContent.verticalAlignment = GridData.FILL; 
		    
	    griddataButton = new GridData(GridData.BEGINNING, GridData.BEGINNING,true, false);
		    griddataButton.horizontalAlignment = GridData.BEGINNING;
		    griddataButton.widthHint = 110;
		    griddataButton.horizontalSpan=1;
		    
	    griddataLabel = new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 1,1);
		    //griddataLabel.horizontalSpan=1;
		    griddataLabel.widthHint=90;
//		    GridData griddataText = new GridData(GridData.FILL, GridData.CENTER, true, false, 1,1);
	    griddataText = new GridData(GridData.FILL, GridData.CENTER, true, false, 1,1);
		
	}
	
	private static void initializeShell() {
		
		display = new Display();
//		    final Shell shell = new Shell(display);
	 	shell = new Shell(display);
	    layoutMainClient = new GridLayout(2, false);
	    layoutLogin = new GridLayout(5,false);
//		    final StackLayout stackLoginLayout = new StackLayout();
	    stackLayoutMain = new StackLayout();
	    stackLayoutContent = new StackLayout();
	    layoutOneColumn = new GridLayout(1,false);
	    imageLogo = new Image(display, ".\\src\\iCash - Logo.png");
	    imageTablePull = new Image(display, ".\\src\\TablePull.png");  
    	shell.setLayout(stackLayoutMain);
	}

	

private static void kalkuliereSpaltenbreite(Table tabelle, int minBreite)
	{
		if(tabelle == null || tabelle.getColumns().length == 0)
			return;
		
		int breite = tabelle.getSize().x - tabelle.getBorderWidth() * 2;
		if(tabelle.getVerticalBar() != null && tabelle.getVerticalBar().isVisible())
		{
			breite -= tabelle.getVerticalBar().getSize().x;
		}
		
		if(breite > minBreite)
		{
			int aktuelleBreite = 0;
			int neueBreite = 0;
			
			for(TableColumn spalte : tabelle.getColumns())
			{
				aktuelleBreite += spalte.getWidth();
			}
			
			for(int i = 0; i < tabelle.getColumns().length - 1; i++)
			{
				int spaltenBreite = (int)(tabelle.getColumn(i).getWidth() / (float)aktuelleBreite * breite);
				
				tabelle.getColumn(i).setWidth(spaltenBreite > 10 ? spaltenBreite : 10);
				neueBreite += tabelle.getColumn(i).getWidth();
			}
			
			tabelle.getColumn(tabelle.getColumns().length - 1).setWidth(breite - neueBreite);
		}
	}

//------------ Business Logic ----------------------------//

public static Account getAccount(int number) {
	
	String GETString = server + "/rest/getAccount?number=" + number;
	if (securityMode) 
		GETString = server + "/rest/s/getAccount?number=" + number + "&kundenID=" + customerId + "&passwortHash=" + password; 
	ClientResponse cr = Client.create().resource( GETString ).get( ClientResponse.class );
    if (cr.hasEntity() && cr.getStatus() == 200) {
	JSONObject jo = new JSONObject(cr.getEntity(String.class));
    Account a = new Account();
    a.setId(jo.getInt("number"));
    Customer c = new Customer();
    c.setFirstName(jo.getString("owner").split(" ")[0]);
    c.setLastName(jo.getString("owner").split(" ")[1]);
    a.setCustomer(c);
    
    JSONArray ja = jo.getJSONArray("transactions");
    
    for (int i=0; i<ja.length(); i++) {
    	Transaction t = new Transaction();
    	JSONObject transaction = ja.getJSONObject(i);
    	t.setAmount(classes.Convert.toCent((transaction.getString("amount"))));
    	t.setDate(Date.valueOf(transaction.getString("transactionDate").substring(0, 10)));
    	t.setDescription(transaction.getString("reference"));
    	
    	Account incomingAccount = new Account();
    	JSONObject receiver = transaction.getJSONObject("receiver");
    	incomingAccount.setId(receiver.getInt("number"));
    	Customer c_in = new Customer();
    	c_in.setFirstName(receiver.getString("owner").split(" ")[0]);
    	c_in.setLastName(receiver.getString("owner").split(" ")[1]);
    	incomingAccount.setCustomer(c_in);
    	t.setIncomingAccount(incomingAccount);
    	
    	Account outgoingAccount = new Account();
    	JSONObject sender = transaction.getJSONObject("sender");
    	outgoingAccount.setId(sender.getInt("number"));
    	Customer c_out = new Customer();
    	c_out.setFirstName(sender.getString("owner").split(" ")[0]);
    	c_out.setLastName(sender.getString("owner").split(" ")[1]);
    	outgoingAccount.setCustomer(c_out);
    	t.setOutgoingAccount(outgoingAccount);
    	
    	a.add(t);
    }
    LabelStatusLine.setText(getMessage(200));
    return a;
    }
    else if (cr.hasEntity())
		LabelStatusLine.setText(getMessage(cr.getStatus()));
	else
		LabelStatusLine.setText(getMessage(500));
    return null;
}

public static void transferMoney(int senderNumber, int receiverNumber, String amount, String reference) {
	
	String GETString = server + "/rest/transferMoney";
	Form f = new Form();
	f.add("senderNumber", senderNumber);
	f.add("receiverNumber", receiverNumber);
	f.add("amount", amount);
	f.add("reference", reference);
	if (securityMode) {
		f.add("kundenID", customerId);
		f.add("passwortHash", password);
		GETString = server + "/rest/s/transferMoney";
	}
	
	ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
	LabelStatusLine.setText(getMessage(cr.getStatus()));
}

	public static int getBankAccount(int id) {
		String GETString = server + "/rest/getBankAccount" + "?account=" + id;
		if (securityMode) {
			GETString = server + "/rest/s/getBankAccount" + "?account=" + id + "&kundenID=" + customerId + "&passwortHash=" + password; 
		}
		ClientResponse cr = Client.create().resource( GETString ).get( ClientResponse.class );
		if (cr.hasEntity() && cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			LabelStatusLine.setText(getMessage(200));
			return jo.getInt("bankAccount");
		}
		else if (cr.hasEntity())
			LabelStatusLine.setText(getMessage(cr.getStatus()));
		else
			LabelStatusLine.setText(getMessage(500));
		return 0;
	}
	
	public static String getBalance(int id) {
		
		String GETString = server + "/rest/getBalance" + "?account=" + id;
		if (securityMode) {
			GETString = server + "/rest/s/getBalance" + "?account=" + id + "&kundenID=" + customerId + "&passwortHash=" + password; 
		}
		
		ClientResponse cr = Client.create().resource( GETString ).get( ClientResponse.class );
		if (cr.hasEntity() && cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			String balance = jo.getString("balance");
			//LabelStatusLine.setText(getMessage(200));
			return balance;
		}
			else if (cr.hasEntity())
				LabelStatusLine.setText(getMessage(cr.getStatus()));
			else
				LabelStatusLine.setText(getMessage(500));
		return "";
	}
	
	public static int getCustomer(int id) {
		
		String GETString = server + "/rest/getCustomer" + "?account=" + id;
		if (securityMode) {
			GETString = server + "/rest/s/getCustomer" + "?account=" + id + "&passwortHash=" + password; 
		}
		
		ClientResponse cr = Client.create().resource( GETString ).get( ClientResponse.class );
		
		if (cr.hasEntity() && cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			int customer = jo.getInt("customer");
			LabelStatusLine.setText(getMessage(200));
			return customer;
		}
		else if (cr.hasEntity())
			LabelStatusLine.setText(getMessage(cr.getStatus()));
		else
			LabelStatusLine.setText(getMessage(500));
		return 0;
	}
	
	public static String getMessage(int statusCode) {
		switch (statusCode) {
			case 200: return "OK: Your request was processed successfully!";
			case 400: return "BAD REQUEST: The data format might be incorrect!";
			case 403: return "FORBIDDEN: Your login data might be incorrect!";
			case 404: return "NOT FOUND: The requested account was not found!";
			case 412: return "PRECONDITION FAILED: Your balance is insufficient!";
			case 500: return "INTERNAL SERVER ERROR: Please try again later!";
			default:  return "Unknown Response!";
		}
	}
}