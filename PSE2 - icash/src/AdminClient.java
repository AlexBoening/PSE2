

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
			          stackLayoutMain.topControl = compositeMainClient;
			          shell.layout();
			        }
			      });
		    
		    buttonMenuDeactivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	stackLayoutContent.topControl = compositeAccountPage;
		          compositeContent.layout();
		        }
		      });
		    
		    buttonMenuCreateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
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
			          //do something here to deactivate selected account
			        }
			      });
		    
		    buttonActivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to activate selected account
			        }
			      });
		    
		    buttonCreateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to create account
			        }
			      });
		    
		    buttonCreateCustomer.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to create customer
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
		
	}

	private static void fillCompositeCreateAccountPage() {
		
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
			Text CreateAccountTypeBank = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeBank.setLayoutData(griddataText);
			
			Label CustomerLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			CustomerLabel.setText("Customer:");
			CustomerLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeCustomer = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeCustomer.setLayoutData(griddataText);
			
			Label ResponsibleLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			ResponsibleLabel.setText("Responsible:");
			ResponsibleLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeResponsible = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeResponsible.setLayoutData(griddataText);
			
			Label AccountTypeLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			AccountTypeLabel.setText("AccountType:");
			AccountTypeLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeAccountType = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeAccountType.setLayoutData(griddataText);
			
			Label ActiveLabel = new Label(compositeCreateAccountPage, SWT.NONE);
			ActiveLabel.setText("Active:");
			ActiveLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeActive = new Text(compositeCreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeActive.setLayoutData(griddataText);
			
		    CaptionCreateAccountPage.pack();
		    
		    Label SepPerform2 = new Label(compositeCreateAccountPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform2.setBackground(new Color(display,255,255,255));
		    SepPerform2.setLayoutData(CreateAccountCompositeData);
		    
		    buttonCreateAccount = new Button(compositeCreateAccountPage, SWT.PUSH);
		    buttonCreateAccount.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    buttonCreateAccount.setText("Create Account");
		    buttonCreateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonCreateAccount.setLayoutData(griddataButton);
		
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
		    CaptionViewPage.pack();
		    
		    buttonDeactivateAccount = new Button(compositeAccountPage, SWT.PUSH);
		    buttonDeactivateAccount.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    buttonDeactivateAccount.setText("Deacitvate Account");
		    buttonDeactivateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonDeactivateAccount.setLayoutData(griddataButton);
		    
		    buttonActivateAccount = new Button(compositeAccountPage, SWT.PUSH);
		    buttonActivateAccount.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    buttonActivateAccount.setText("Acitvate Account");
		    buttonActivateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonActivateAccount.setLayoutData(griddataButton);
	}

	private static void fillCompositeWelcomePage() {
		 
		 GridData WelcomeCompositeData = new GridData(GridData.BEGINNING, GridData.FILL,true, false);
		    WelcomeCompositeData.horizontalSpan = 2;
		    Label CaptionWelcomePage = new Label(compositeWelcomePage, SWT.NONE);
		    CaptionWelcomePage.setText("Welcome to the Adminstration Frontend!");
		    CaptionWelcomePage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionWelcomePage.setLayoutData(WelcomeCompositeData);
		
	}

	

	private static void fillCompositeMainClient() {

		//Header
	    Label LabelHorizontal = new Label(compositeHeader,SWT.NONE);
	    LabelHorizontal.setText("iCash - Administration");
	    LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
	    LabelHorizontal.setBackground(new Color(display, 200,200,200));
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
		 
		    
		    final GridData griddataCaption = new GridData(GridData.FILL, GridData.FILL,true, false);
			    griddataCaption.heightHint=35;
			    griddataCaption.horizontalSpan=5;
		    
		    final GridData griddataDescription = new GridData(GridData.FILL, GridData.FILL,false, false);
		    	griddataDescription.horizontalSpan=2;
		    
		    final GridData griddataTexts = new GridData(GridData.FILL, GridData.FILL,false, false);
		    	griddataTexts.horizontalSpan=3;
		    
		    final GridData griddataLoginButton = new GridData(GridData.FILL, GridData.CENTER,false, false);
			    griddataLoginButton.horizontalSpan=1;
			    griddataLoginButton.heightHint = 35;
		    
			//imageLogo = new Image(compositeLogin)
			    
		    Label LoginCaption = new Label(compositeLogin,SWT.NONE);
			    LoginCaption.setText("iCash - Administration");
			    LoginCaption.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
			    LoginCaption.setLayoutData(griddataCaption);
		    
		    Label ServerLabel = new Label(compositeLogin, SWT.NONE);
			    ServerLabel.setText("Server:");
			    ServerLabel.setLayoutData(griddataDescription);
		    
		    Text ServerText = new Text(compositeLogin,SWT.BORDER);
		    	ServerText.setLayoutData(griddataTexts);
		    
		    Label UserLabel = new Label(compositeLogin, SWT.NONE);
			    UserLabel.setText("Username:");
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
	      
    	shell.setLayout(stackLayoutMain);
	}
	}
