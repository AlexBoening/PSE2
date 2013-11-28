

import java.io.InputStream;

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
import org.eclipse.swt.widgets.Combo;
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
import org.json.*;

import classes.*;

import java.sql.*;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.representation.Form;

public class AdminClient {
		
	static Display display;
	static Shell shell;
	
	static GridLayout layoutMainClient, layoutLogin, layoutOneColumn;

	static StackLayout stackLayoutMain, stackLayoutContent;
	
	static GridData griddataWindow, griddataHeader, griddataNavigation, griddataContent, griddataLogoutButton, griddataButton
					, griddataLabel, griddataText;
	
	static Composite compositeLogin, compositeMainClient, compositeHeader, compositeNavigation, compositeContent, compositeWelcomePage, compositeAccountPage
					,compositeCreateAccountPage, compositeCreateCustomerPage;
	
	static Button buttonLogin, buttonLogout, buttonMenuDeactivateAccount, buttonMenuCreateAccount, buttonMenuCreateCustomer, buttonDeactivateAccount
					, buttonActivateAccount, buttonCreateAccount, buttonCreateCustomer;
	
	static Image imageLogo, imageTablePull;
	
	private static boolean securityMode = true;
	private static Administrator admin;
	private static String password;
	private static String server;
	private static Bank[] b;
	private static Customer[] c;
	private static Administrator[] a; 
	private static AccountType[] at;
	
	 public static void main(String[] args) {
		 
		 	initializeShell();
		 	
		 	initializeGridData();
		 	
		 	initializeComposites();
		 	
		 	fillCompositeLogin();
		 	
		 	fillCompositeMainClient();
		 	
		 	fillCompositeWelcomePage();
		 	
		 	fillCompositeAccountPage();
		 	
		 	fillCompositeCreateAccountPage();
		 	
		 	fillCompositeCreateCustomerPage();
		 	
		 	//set WelcomePage to be the first thing to see
		 	stackLayoutMain.topControl=compositeLogin;
		 	stackLayoutContent.topControl = compositeWelcomePage;
	        compositeContent.layout();
		 	
		    //Events   
	        
		    buttonLogin.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {

	        	  server = ((Text)event.widget.getData("server")).getText();
	        	  password = ((Text)event.widget.getData("password")).getText();
	        	  int adminId = Convert.toInt(((Text)event.widget.getData("user")).getText());
	        	  admin  = getAdmin(adminId);
	        	  if (admin != null) {
			          stackLayoutMain.topControl = compositeMainClient;
			          shell.layout();
	        	  }	        	  
			    }
			   });
		    
		    buttonMenuDeactivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {		   
		    	    compositeAccountPage = new Composite(compositeContent, SWT.NONE);
				    compositeAccountPage.setBackground(new Color(display,255,255,255));
				    compositeAccountPage.setLayout(layoutMainClient);
				    fillCompositeAccountPage();
		        	stackLayoutContent.topControl = compositeAccountPage;
		            compositeContent.layout();
		        }
		      });
		    
		    buttonMenuCreateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	compositeCreateAccountPage = new Composite(compositeContent, SWT.NONE);
				    compositeCreateAccountPage.setBackground(new Color(display,255,255,255));
				    compositeCreateAccountPage.setLayout(layoutMainClient);
				    fillCompositeCreateAccountPage();
		        	stackLayoutContent.topControl = compositeCreateAccountPage;
			        compositeContent.layout();
			    }
			});
		    
		    buttonMenuCreateCustomer.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	stackLayoutContent.topControl = compositeCreateCustomerPage;
			          compositeContent.layout();
			        }
			      });
		    
		    buttonLogout.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent arg0) {
		    		display.dispose();
		    	}
		    });
		    
		    buttonDeactivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	// doSomething
			    }
			      });
		    
		    buttonActivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	// doSomething
		        }
			      });
		    
		    buttonCreateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	try {
		        	int bank = b[((Combo)event.widget.getData("bank")).getSelectionIndex()].getId();
		        	int customer = c[((Combo)event.widget.getData("customer")).getSelectionIndex()].getId();
		        	int administrator = a[((Combo)event.widget.getData("admin")).getSelectionIndex()].getId();
		        	int accountType = at[((Combo)event.widget.getData("accountType")).getSelectionIndex()].getId();
		        	int accountId = AdminClient.createAccount(admin.getId(), bank, customer, administrator, accountType);
		        	if (accountId != 0) {
		        		System.out.println("new AccountId = " + accountId);
		        	} else {
		        		System.out.println("no new AccountId available at this point");
		        	}
		        	}
		        	catch (ArrayIndexOutOfBoundsException e) {
		        		System.out.println("Please complete your selection!");
		        	}
		        }
	        });
		    
		    buttonCreateCustomer.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {		        		        	
		        	String firstName = (((Text)event.widget.getData("firstName")).getText());
		        	String lastName  = (((Text)event.widget.getData("lastName")).getText());
		        	String password  = (((Text)event.widget.getData("password")).getText());
		        	if (!(firstName.isEmpty() || lastName.isEmpty() || password.isEmpty())) {
		        	int customerId = AdminClient.createCustomer(admin.getId(), firstName, lastName, password);
		        		if (customerId != 0) {
		        			System.out.println("new CustomerId = " + customerId);
		        		} else {
		        			System.out.println("no new CustomerId available at this point");
		        		}
		        	}
		        	else
		        		System.out.println("Please fill in all required fields!");
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

	 private static void fillCompositeCreateCustomerPage() {
		
		 GridData CreateCustomerCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		 
		    CreateCustomerCompositeData.horizontalSpan = 2;
		    Label CaptionCreateCustomerPage = new Label(compositeCreateCustomerPage, SWT.NONE);
		    CaptionCreateCustomerPage.setText("Create a new Customer");
		    CaptionCreateCustomerPage.setLayoutData(CreateCustomerCompositeData);
		    
		    Label SepPerform3 = new Label(compositeCreateCustomerPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform3.setBackground(new Color(display,255,255,255));
		    SepPerform3.setLayoutData(CreateCustomerCompositeData);
		    
		    Label FirstnameLabel = new Label(compositeCreateCustomerPage,SWT.NONE);
		    FirstnameLabel.setText("Firstname:");
		    FirstnameLabel.setLayoutData(griddataLabel);
			Text CreateCustomerTypeFirstname = new Text(compositeCreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			//CreateCustomerTypeFirstname.setText("Jonas");
			CreateCustomerTypeFirstname.setLayoutData(griddataText);
			
			Label LastnameLabel = new Label(compositeCreateCustomerPage, SWT.NONE);
			LastnameLabel.setText("Lastname:");
			LastnameLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeLastname = new Text(compositeCreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeLastname.setLayoutData(griddataText);
			
			Label InitPasswordLabel = new Label(compositeCreateCustomerPage, SWT.NONE);
			InitPasswordLabel.setText("Initial Password:");
			InitPasswordLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeInitPassword = new Text(compositeCreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeInitPassword.setLayoutData(griddataText);
		    
			CaptionCreateCustomerPage.pack();
			
			Label SepPerform4 = new Label(compositeCreateCustomerPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform4.setBackground(new Color(display,255,255,255));
		    SepPerform4.setLayoutData(CreateCustomerCompositeData);

		    buttonCreateCustomer = new Button(compositeCreateCustomerPage, SWT.PUSH);
		    buttonCreateCustomer.setText("Create Customer");
		    buttonCreateCustomer.setBackground(new Color(display, 31, 78, 121));
		    buttonCreateCustomer.setLayoutData(griddataButton);
			buttonCreateCustomer.setData("firstName",CreateCustomerTypeFirstname);
		    buttonCreateCustomer.setData("lastName",CreateAccountTypeLastname);
		    buttonCreateCustomer.setData("password",CreateAccountTypeInitPassword);	    
	}

	private static void fillCompositeCreateAccountPage() {
		
		if (admin != null)
			getData(admin.getId());
		
		 GridData CreateAccountCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    
		    CreateAccountCompositeData.horizontalSpan = 2;
		    
		    Label CaptionCreateAccountPage = new Label(compositeCreateAccountPage, SWT.NONE);
		    CaptionCreateAccountPage.setText("Create an Account");
		    CaptionCreateAccountPage.setLayoutData(CreateAccountCompositeData);
		    
		    Label SepPerform1 = new Label(compositeCreateAccountPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform1.setBackground(new Color(display,255,255,255));
		    SepPerform1.setLayoutData(CreateAccountCompositeData);
		    
		    Label BankLabel = new Label(compositeCreateAccountPage,SWT.NONE);
		    BankLabel.setText("Bank:");
		    BankLabel.setLayoutData(griddataLabel);
		    Combo CreateAccountBank = new Combo(compositeCreateAccountPage, SWT.READ_ONLY);
		    CreateAccountBank.setLayoutData(griddataText);
		    if (b != null) {
		    	String bank[] = new String[b.length];
		    	for (int i=0; i<b.length; i++) {
		    		bank[i] = b[i].getDescription();
		    	}
		    	CreateAccountBank.setItems(bank);
		    }
			//Text CreateAccountTypeBank = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			//CreateAccountTypeBank.setLayoutData(griddataText);
			
			Label CustomerLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			CustomerLabel.setText("Customer:");
			CustomerLabel.setLayoutData(griddataLabel);
		    Combo CreateAccountCustomer = new Combo(compositeCreateAccountPage, SWT.READ_ONLY);
		    CreateAccountCustomer.setLayoutData(griddataText);
		    if (c != null) {
		    	String customer[] = new String[c.length];
		    	for (int i=0; i<c.length; i++) {
		    		customer[i] = c[i].getFirstName() + " " + c[i].getLastName();
		    	}
		    	CreateAccountCustomer.setItems(customer);
		    }
			//Text CreateAccountTypeCustomer = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			//CreateAccountTypeCustomer.setLayoutData(griddataText);
			
			Label AdministratorLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			AdministratorLabel.setText("Administrator:");
			AdministratorLabel.setLayoutData(griddataLabel);
		    Combo CreateAccountAdministrator = new Combo(compositeCreateAccountPage, SWT.READ_ONLY);
		    CreateAccountAdministrator.setLayoutData(griddataText);
		    if (a != null) {
		    	String admin[] = new String[a.length];
		    	for (int i=0; i<a.length; i++) {
		    		admin[i] = a[i].getFirstName() + " " + a[i].getLastName();
		    	}
		    	CreateAccountAdministrator.setItems(admin);
		    }
			//Text CreateAccountTypeResponsible = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			//CreateAccountTypeResponsible.setLayoutData(griddataText);
			
			Label AccountTypeLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			AccountTypeLabel.setText("AccountType:");
			AccountTypeLabel.setLayoutData(griddataLabel);
		    Combo CreateAccountAccountType = new Combo(compositeCreateAccountPage, SWT.READ_ONLY);
		    CreateAccountAccountType.setLayoutData(griddataText);
		    if (at != null) {
		    	String accountType[] = new String[at.length];
		    	for (int i=0; i<at.length; i++) {
		    		accountType[i] = at[i].getDescription();
		    	}
		    	CreateAccountAccountType.setItems(accountType);
		    }
			//Text CreateAccountTypeAccountType = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			//CreateAccountTypeAccountType.setLayoutData(griddataText);
			
			Label ActiveLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			ActiveLabel.setText("Active:");
			ActiveLabel.setLayoutData(griddataLabel);
			Button CreateAccountActive = new Button(compositeCreateAccountPage, SWT.CHECK);
			CreateAccountActive.setText("active");
			CreateAccountActive.setLayoutData(griddataText);
			//Text CreateAccountTypeActive = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			//CreateAccountTypeActive.setLayoutData(griddataText);
			
		    CaptionCreateAccountPage.pack();
		    
		    Label SepPerform2 = new Label(compositeCreateAccountPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform2.setBackground(new Color(display,255,255,255));
		    SepPerform2.setLayoutData(CreateAccountCompositeData);
		    
		    buttonCreateAccount = new Button(compositeCreateAccountPage, SWT.PUSH);
		    buttonCreateAccount.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    buttonCreateAccount.setText("Create Account");
		    buttonCreateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonCreateAccount.setLayoutData(griddataButton);
		    buttonCreateAccount.setData("bank",CreateAccountBank);
		    buttonCreateAccount.setData("customer",CreateAccountCustomer);
		    buttonCreateAccount.setData("admin",CreateAccountAdministrator);
    		buttonCreateAccount.setData("accountType",CreateAccountAccountType);
    		buttonCreateAccount.setData("active",CreateAccountActive);
    		
    		buttonCreateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	try {
		        	int bank = b[((Combo)event.widget.getData("bank")).getSelectionIndex()].getId();
		        	int customer = c[((Combo)event.widget.getData("customer")).getSelectionIndex()].getId();
		        	int administrator = a[((Combo)event.widget.getData("admin")).getSelectionIndex()].getId();
		        	int accountType = at[((Combo)event.widget.getData("accountType")).getSelectionIndex()].getId();
		        	//String active = ((Text)event.widget.getData("active")).getText();
		        	int accountId = createAccount(admin.getId(), bank, customer, administrator, accountType);
		        	if (accountId != 0) {
		        		System.out.println("new AccountId = " + accountId);
		        	} else {
		        		System.out.println("no new AccountId available at this point");
		        	}
		        	}
		        	catch (ArrayIndexOutOfBoundsException e) {
		        		System.out.println("Please complete your selection!");
		        	}
		        }
	        });
	}

	private static void fillCompositeAccountPage() {
		 
		 GridData ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    
		    ViewCompositeData.horizontalSpan = 2;
		    Label CaptionViewPage = new Label(compositeAccountPage, SWT.NONE);
		    CaptionViewPage.setText("All Accounts");
		    CaptionViewPage.setLayoutData(ViewCompositeData);
		    final Table table = new Table(compositeAccountPage,
		    		SWT.SINGLE | SWT.H_SCROLL |
		    		SWT.V_SCROLL | SWT.BORDER |
		    		SWT.FULL_SELECTION | SWT.FILL_EVEN_ODD);

		    		final TableColumn col1 = new TableColumn(table,SWT.LEFT);
		    		col1.setText("ID");
		    		col1.setWidth(80);
		    		final TableColumn col2 = new TableColumn(table,SWT.LEFT);
		    		col2.setText("Bank");
		    		col2.setWidth(80);
		    		final TableColumn col3 = new TableColumn(table,SWT.LEFT);
		    		col3.setText("Customer");
		    		col3.setWidth(80);
		    		final TableColumn col4 = new TableColumn(table,SWT.LEFT);
		    		col4.setText("Responsible");
		    		col4.setWidth(80);
		    		final TableColumn col5 = new TableColumn(table,SWT.LEFT);
		    		col5.setText("Account Type");
		    		col5.setWidth(100);
		    		final TableColumn col6 = new TableColumn(table,SWT.LEFT);
		    		col6.setText("Active");
		    		col6.setWidth(80);
		    		
		    		table.setHeaderVisible(true);
		    		table.setLinesVisible(true);
		    		
		    		ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, true);
		    		ViewCompositeData.horizontalSpan=2;
		    		table.setLayoutData(ViewCompositeData);
		    		
		    		// Fill in the Customer data
		    		if (admin != null)
		    		try {
				    admin = getAdmin(admin.getId());
    				Account[] acc = new Account[admin.getAccounts().size()];
    				admin.getAccounts().toArray(acc);
    				
		    		for (int i=0; i<acc.length; i++) {
    					TableItem item = new TableItem(table, SWT.NONE);
    					String[] column = new String[6];
    					column[0] = "" + acc[i].getId();
    					column[1] = acc[i].getBank().getDescription();
    					column[2] = acc[i].getCustomer().getFirstName() + " " +
    								acc[i].getCustomer().getLastName();
    					column[3] = acc[i].getAdministrator().getFirstName() + " " +
    								acc[i].getAdministrator().getLastName();
    					column[4] = acc[i].getAccountType().getDescription();
    					if (acc[i].isFlagActive())
    						column[5] = "X";
    					else
    						column[5] = " ";
    					item.setText(column);
    				}
		    		}
		    		catch (SQLException e) {
		    			// Statuszeile auf Status 500 setzen
		    		}
		    		
		    CaptionViewPage.pack();
		    
		    buttonDeactivateAccount = new Button(compositeAccountPage, SWT.PUSH);
		    buttonDeactivateAccount.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    buttonDeactivateAccount.setText("Deacitvate Account");
		    buttonDeactivateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonDeactivateAccount.setLayoutData(griddataButton);
		    buttonDeactivateAccount.setData("customers", table);
		    
		    buttonActivateAccount = new Button(compositeAccountPage, SWT.PUSH);
		    buttonActivateAccount.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    buttonActivateAccount.setText("Acitvate Account");
		    buttonActivateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonActivateAccount.setLayoutData(griddataButton);
		    buttonActivateAccount.setData("customers", table);
		    
		    buttonDeactivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        		Table t = (Table)event.widget.getData("customers");
		        		int[] index = t.getSelectionIndices();
		        		if (index.length != 1)
		        			System.out.println("Please mark exactly one row!");
		        		else {
		        			TableItem i = t.getItem(index[0]);
		        			int idAccount = Convert.toInt(i.getText(0));
		        			int status = setActive(admin.getId(), false, idAccount);
		        			if (status == 200) 
		        				i.setText(5, " ");
		        		}
			        }
			      });
		    
		    buttonActivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	Table t = (Table)event.widget.getData("customers");
	        		int[] index = t.getSelectionIndices();
	        		if (index.length != 1)
	        			System.out.println("Please mark exactly one row!");
	        		else {
	        			TableItem i = t.getItem(index[0]);
	        			int idAccount = Convert.toInt(i.getText(0));
	        			int status = setActive(admin.getId(), true, idAccount);
	        			if (status == 200) 
	        				i.setText(5, "X");
	        		}
			        }
			      });
	}

	private static void fillCompositeWelcomePage() {
		 
		 GridData WelcomeCompositeData = new GridData(GridData.BEGINNING, GridData.FILL,true, false);
		    WelcomeCompositeData.horizontalSpan = 2;
		    Label CaptionWelcomePage = new Label(compositeWelcomePage, SWT.NONE);
		    CaptionWelcomePage.setText("Welcome to the Adminstration Frontend!");
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
	    //LabelHorizontal.setText("iCash - Administration");
	    LabelHorizontal.setBackgroundImage(imageLogo);
	    LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
	    LabelHorizontal.setLayoutData(griddataCaption);
	    GridData griddataLogoutButton = new GridData(GridData.END, GridData.CENTER, true, false);
	    buttonLogout = new Button(compositeHeader, SWT.PUSH);
	    buttonLogout.setLayoutData(griddataLogoutButton);
	    buttonLogout.setBackground(new Color(display, 31, 78, 121));
	    buttonLogout.setText("Log out!");
	    
	    //Navigation
	    final GridData griddataMenuContent = new GridData(GridData.FILL, GridData.CENTER,true, false);
	    
	    buttonMenuDeactivateAccount = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuDeactivateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuDeactivateAccount.setText("Deactivate Account");
		    buttonMenuDeactivateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuDeactivateAccount.setLayoutData(griddataMenuContent);
	    
	    Label placeholder4 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder4.setBackground(new Color(display, 200,200,200));
		    placeholder4.setLayoutData(griddataMenuContent);
	    
	    buttonMenuCreateAccount = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuCreateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuCreateAccount.setText("Create Account");
		    buttonMenuCreateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuCreateAccount.setLayoutData(griddataMenuContent);
	    
	    Label placeholder5 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder5.setBackground(new Color(display, 200,200,200));
		    placeholder5.setLayoutData(griddataMenuContent);
	    
	    buttonMenuCreateCustomer = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuCreateCustomer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuCreateCustomer.setText("Create Customer");
		    buttonMenuCreateCustomer.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuCreateCustomer.setLayoutData(griddataMenuContent);
	    
	    Label placeholder6 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder6.setBackground(new Color(display, 200,200,200));
		    placeholder6.setLayoutData(griddataMenuContent);
	    
	    Label placeholder7 = new Label(compositeNavigation, SWT.NONE | SWT.HORIZONTAL);
		    placeholder7.setBackground(new Color(display, 200,200,200));
		    placeholder7.setLayoutData(griddataMenuContent);
	    
	    Label placeholder8 = new Label(compositeNavigation, SWT.NONE | SWT.HORIZONTAL);
		    placeholder8.setBackground(new Color(display, 200,200,200));
		    placeholder8.setLayoutData(griddataMenuContent);
	    
	}

	

	private static void fillCompositeLogin()
	 {
		 
		    
		    final GridData griddataCaption = new GridData(GridData.FILL, GridData.FILL,false, false);
			    griddataCaption.heightHint=58;
			    griddataCaption.horizontalSpan=5;
			    griddataCaption.widthHint=625;
		    
		    final GridData griddataDescription = new GridData(GridData.FILL, GridData.FILL,false, false);
		    	griddataDescription.horizontalSpan=2;
		    
		    final GridData griddataTexts = new GridData(GridData.FILL, GridData.FILL,false, false);
		    	griddataTexts.horizontalSpan=3;
		    
		    final GridData griddataLoginButton = new GridData(GridData.FILL, GridData.CENTER,false, false);
			    griddataLoginButton.horizontalSpan=1;
			    griddataLoginButton.heightHint = 35;
		    
			
			//compositeLogin.setBackgroundImage(imageLogo);    
		    Label LoginCaption = new Label(compositeLogin,SWT.NONE);
			    //LoginCaption.setText("iCash - Administration");
			    LoginCaption.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
			    LoginCaption.setBackgroundImage(imageLogo);
			    LoginCaption.setLayoutData(griddataCaption);
		    
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
		    
	    compositeAccountPage = new Composite(compositeContent, SWT.NONE);
		    compositeAccountPage.setBackground(new Color(display,255,255,255));
		    compositeAccountPage.setLayout(layoutMainClient);
		    
	    compositeCreateAccountPage = new Composite(compositeContent, SWT.NONE);
		    compositeCreateAccountPage.setBackground(new Color(display,255,255,255));
		    compositeCreateAccountPage.setLayout(layoutMainClient);
		    
	    compositeCreateCustomerPage = new Composite(compositeContent, SWT.NONE);
		    compositeCreateCustomerPage.setBackground(new Color(display,255,255,255));
		    compositeCreateCustomerPage.setLayout(layoutMainClient);
	}
	
	private static void initializeGridData() {

		griddataWindow = new GridData(GridData.FILL, GridData.FILL,true, true);
		    griddataWindow.horizontalSpan=5;
		
	    griddataHeader = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddataHeader.horizontalSpan = 2;
		    
	    griddataNavigation = new GridData();
		    griddataNavigation.verticalAlignment = GridData.FILL;
		    griddataNavigation.grabExcessVerticalSpace = true;
		
	    griddataContent = new GridData(GridData.FILL, GridData.FILL,true, false);
		    
	    griddataButton = new GridData(GridData.BEGINNING, GridData.BEGINNING,true, false);
		    griddataButton.widthHint = 110;
		    griddataButton.horizontalSpan=1;
		    
	    griddataLabel = new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 1,1);
		    griddataLabel.widthHint=90;
		    
	    griddataText = new GridData(GridData.FILL, GridData.CENTER, true, false, 1,1);
		
	}
	
	private static void initializeShell() {
		
		display = new Display();
	 	shell = new Shell(display);
	    layoutMainClient = new GridLayout(2, false);
	    layoutLogin = new GridLayout(5,false);
	    stackLayoutMain = new StackLayout();
	    stackLayoutContent = new StackLayout();
	    layoutOneColumn = new GridLayout(1,false);
	    
	    final InputStream stream = AdminClient.class.getResourceAsStream("iCash - Logo.png");
	    final InputStream stream2 = AdminClient.class.getResourceAsStream("TablePull.png");
	    imageLogo = new Image(Display.getDefault(), stream);
	    
//	    imageLogo = new Image(display, ".\\src\\iCash - Logo.png");
	    imageTablePull = new Image(display, stream2);  
	    
    	shell.setLayout(stackLayoutMain);
	}
	

// Methoden zur Datenübertragung an die RestResource
	
	public static Administrator getAdmin(int id) {
		String GETString;
		if (securityMode) {
			GETString = server + "/rest/s/getAdmin" + "?adminID=" + id + "&passwortHash=" + password; 
		} else {
			GETString = server + "/rest/getAdmin" + "?adminID=" + id;
		}
		
		if (!GETString.isEmpty()) {
			ClientResponse cr = Client.create().resource( GETString ).get( ClientResponse.class );	
			if (cr.getStatus() == 200) {
				JSONObject jo = new JSONObject(cr.getEntity(String.class));
				
				Administrator admin = new Administrator();
				admin.setId(jo.getInt("id"));
				admin.setFirstName(jo.getString("firstName"));
				admin.setLastName(jo.getString("lastName"));
				
				JSONArray ja = jo.getJSONArray("accounts");
				
				for (int i=0; i<ja.length(); i++) {
					Account acc = new Account();
					JSONObject currentAccount = ja.getJSONObject(i);
					
					acc.setId(currentAccount.getInt("id"));
					
					JSONObject bank = currentAccount.getJSONObject("bank");
					Bank b = new Bank();
					acc.setBank(b);				
					b.setId(bank.getInt("id"));
					b.setDescription(bank.getString("description"));
					
					JSONObject customer = currentAccount.getJSONObject("customer");
					Customer c = new Customer();
					acc.setCustomer(c);
					c.setId(customer.getInt("id"));
					c.setFirstName(customer.getString("firstName"));
					c.setLastName(customer.getString("lastName"));
					
					acc.setAdministrator(admin);
					
					JSONObject accountType = currentAccount.getJSONObject("accountType");
					AccountType at = new AccountType();
					acc.setAccountType(at);
					at.setId(accountType.getInt("id"));
					at.setDescription(accountType.getString("description"));
					
					acc.setFlagActive(currentAccount.getBoolean("active"));
					
                    admin.add(acc);
				}
				return admin;
			}
		}
		return null;
	}
	
	public static int createCustomer(int idLogin, String firstName, String lastName, String password) {
	
		String GETString = server + "/rest/s/createCustomer";
		
		Form f = new Form();
		f.add("firstName", firstName);
		f.add("lastName", lastName);
		f.add("password", password);
		f.add("adminIdLogin", idLogin);
		f.add("passwortHash", password);
		
		ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
		if (cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			int id = jo.getInt("id");
			return id;
		}
		else
			return 0;
	}
	
	public static int createAccount(int idLogin, int bankId, int customerId, int adminId, int accountTypeId) {

		String GETString = server + "/rest/s/createAccount";
		
		Form f = new Form();
		f.add("bankId", bankId);
		f.add("customerId", customerId);
		f.add("adminId", adminId);
		f.add("accountTypeId", accountTypeId);
		f.add("adminIdLogin", idLogin);
		f.add("passwortHash", password);
		
		ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
		if (cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			return jo.getInt("id");
		}
		return 0;
	}
	
	public static int createAccountType(int idLogin, String interestRate, String description) {
		
		String GETString = server + "/rest/s/createAccountType";
		
		Form f = new Form();
		f.add("interestRate", Convert.toDouble(interestRate));
		f.add("description", description);
		f.add("adminIdLogin", idLogin);
		f.add("passwortHash", password);
		
		ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
		if (cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			return jo.getInt("id");
		}
		return 0;
	}
	
	public static int createBank(int idLogin, String description, int blz) {
		
		String GETString = server + "/rest/s/createBank";
		
		Form f = new Form();
		f.add("description", description);
		f.add("blz", blz);
		f.add("idLogin", idLogin);
		f.add("passwortHash", password);
		
		ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
		if (cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			return jo.getInt("id");
		}
		return 0;
	}
	
	public static void getData(int id) {
		
		String GETString = server + "/rest/s/getData" + "?adminID=" + id + "&passwortHash=" + password; 
		
		ClientResponse cr = Client.create().resource( GETString ).get( ClientResponse.class );
		
		if (cr.getStatus() == 200) {
			JSONObject jo = new JSONObject(cr.getEntity(String.class));
			
			JSONArray banks = jo.getJSONArray("banks");
			JSONArray customers = jo.getJSONArray("customers");
			JSONArray admins = jo.getJSONArray("admins");
			JSONArray accountTypes = jo.getJSONArray("accountTypes");
			
			b = new Bank[banks.length()];
			for (int i=0; i<banks.length(); i++) {
				JSONObject job = banks.getJSONObject(i);
				Bank bank = new Bank();
				bank.setId(job.getInt("id"));
				bank.setDescription(job.getString("description"));
				b[i] = bank;
			}
			
			c = new Customer[customers.length()];
			for (int i=0; i<customers.length(); i++) {
				JSONObject joc = customers.getJSONObject(i);
				Customer customer = new Customer();
				customer.setId(joc.getInt("id"));
				customer.setFirstName(joc.getString("firstName"));
				customer.setLastName(joc.getString("lastName"));
				c[i] = customer;
			}
			
			a = new Administrator[admins.length()];
			for (int i=0; i<admins.length(); i++) {
				JSONObject joa = admins.getJSONObject(i);
				Administrator admin = new Administrator();
				admin.setId(joa.getInt("id"));
				admin.setFirstName(joa.getString("firstName"));
				admin.setLastName(joa.getString("lastName"));
				a[i] = admin;
			}
			
			at = new AccountType[accountTypes.length()];
			for (int i=0; i<accountTypes.length(); i++) {
				JSONObject joat = accountTypes.getJSONObject(i);
				AccountType accountType = new AccountType();
				accountType.setId(joat.getInt("id"));
				accountType.setDescription(joat.getString("description"));
				at[i] = accountType;
			}
		}
	}
	
	public static int setActive(int idLogin, boolean active, int idAccount) {
		
		String GETString = server + "/rest/s/setActive";
		
		Form f = new Form();
		f.add("active", active);
		f.add("idAccount", idAccount);
		f.add("idLogin", idLogin);
		f.add("passwortHash", password);
		
		ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
		return cr.getStatus();
	}
	
	public static int payInterests(int idLogin, Bank b) {
		String GETString = server + "rest/s/payInterests";
		
		Form f = new Form();
		f.add("idBank", b.getId());
		f.add("idLogin", idLogin);
		f.add("passwortHash", password);
		
		ClientResponse cr = Client.create().resource( GETString ).type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post( ClientResponse.class, f );
		return cr.getStatus();
	}
}