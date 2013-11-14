

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
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
	
	static GridData griddataWindow, griddataHeader, griddataNavigation, griddataContent, griddataLogoutButton;
	
	static Composite compositeLogin, compositeMainClient, compositeHeader, compositeNavigation, compositeContent, compositeWelcomePage;
	
	static Button loginButton, buttonLogout, buttonMenuDeactivateAccount, buttonMenuCreateAccount, buttonMenuCreateCustomer;
	
	
	 public static void main(String[] args) {
		 
		 	initializeShell();
		 	
		 	initializeGridData();
		 	
		 	initializeComposites();
		 	
		 	fillCompositeLogin();
		 	
		 	fillCompositeMainClient();
		 	
		 	fillCompositeWelcomePage();
		 	
		 	
		 	
		 	//set WelcomePage to be the first thing to see
		 	stackLayoutContent.topControl = compositeWelcomePage;
	        compositeContent.layout();
		 	
		 	
		 	
		 	
		 	
		 	
		 	
		 	
		 	
		 	
		 	
//		 	final Display display = new Display();
		 	
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
//		    GridData griddataWindow = new GridData(GridData.FILL, GridData.CENTER,true, true);
		    
		    //griddataWindow.heightHint=500;
		    //griddataWindow.widthHint=5000;
		    //
		    
		    
//		    final Composite compositeLogin = new Composite(shell,0);
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
//		    final Composite compositeMainClient = new Composite(shell, 0);
		 	
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
//		    GridData griddataHeader = new GridData(GridData.FILL, GridData.CENTER,true, false);
		 	
		    
//		    GridData griddataNavigation = new GridData();
		 	
		    
//		    GridData griddataContent = new GridData(GridData.FILL, GridData.CENTER,true, false);
		 	
		    
//		    final Composite compositeHeader = new Composite(compositeMainClient, 0);
		 	
		    
//		    GridLayout layoutCompositeHorizontal = new GridLayout(2, false);
		    
//		    compositeHorizontal.setLayout(layoutCompositeHorizontal);
		    
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
//		    final Composite compositeNavigation = new Composite(compositeMainClient, 0);
		 	
		    
//		    GridLayout layoutCompositeVertical = new GridLayout(1, false);
		    
		    
		    
//		    compositeVertical.setLayout(layoutCompositeVertical);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
//		    final Composite compositeContent = new Composite(compositeMainClient, 0);
		 	
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
//		    final Composite compositeWelcomePage = new Composite(compositeContent, SWT.NONE);
		 	
		    //compositeMain.setLayoutData(griddataMain);
//		    GridLayout WelcomeComposite = new GridLayout(2, false);
		    
//		    WelcomePage.setLayout(WelcomeComposite);
		    
		    
	        
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite compositeAccountPage = new Composite(compositeContent, SWT.NONE);
		    compositeAccountPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
//		    GridLayout ViewComposite = new GridLayout(2, false);
		    GridData ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
//		    AccountPage.setLayout(ViewComposite);
		    compositeAccountPage.setLayout(layoutMainClient);
		    ViewCompositeData.horizontalSpan = 2;
		    Label CaptionViewPage = new Label(compositeAccountPage, SWT.NONE);
		    CaptionViewPage.setText("All Accounts");
		    CaptionViewPage.setLayoutData(ViewCompositeData);
		    final Table table = new Table(compositeAccountPage,
		    		SWT.SINGLE | SWT.H_SCROLL |
		    		SWT.V_SCROLL | SWT.BORDER |
		    		SWT.FULL_SELECTION | SWT.FILL_EVEN_ODD);
		    		// Drei Tabellenspalten erzeugen
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
		    		// Spaltenköpfe und Trennlinien sichtbar machen
		    		table.setHeaderVisible(true);
		    		table.setLinesVisible(true);
		    		
		    		ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, true);
		    		ViewCompositeData.horizontalSpan=2;
		    		table.setLayoutData(ViewCompositeData);
		    CaptionViewPage.pack();
		    
		    final GridData griddataButton = new GridData(GridData.BEGINNING, GridData.BEGINNING,true, false);
		    griddataButton.horizontalAlignment = GridData.BEGINNING;
		    griddataButton.widthHint = 110;
		    griddataButton.horizontalSpan=1;
		    
		    final Button DeactivateAccountButton = new Button(compositeAccountPage, SWT.PUSH);
		    DeactivateAccountButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    DeactivateAccountButton.setText("Deacitvate Account");
		    DeactivateAccountButton.setBackground(new Color(display, 31, 78, 121));
		    DeactivateAccountButton.setLayoutData(griddataButton);
		    final Button ActivateAccountButton = new Button(compositeAccountPage, SWT.PUSH);
		    ActivateAccountButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    ActivateAccountButton.setText("Acitvate Account");
		    ActivateAccountButton.setBackground(new Color(display, 31, 78, 121));
		    ActivateAccountButton.setLayoutData(griddataButton);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite CreateAccountPage = new Composite(compositeContent, SWT.NONE);
		    CreateAccountPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
//		    GridLayout CreateAccountComposite = new GridLayout(2, false);
		    GridData CreateAccountCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    
		    CreateAccountCompositeData.horizontalSpan = 2;
//		    CreateAccountPage.setLayout(CreateAccountComposite);
		    CreateAccountPage.setLayout(layoutMainClient);
		    Label CaptionCreateAccountPage = new Label(CreateAccountPage, SWT.NONE);
		    CaptionCreateAccountPage.setText("Create an Account");
		    CaptionCreateAccountPage.setLayoutData(CreateAccountCompositeData);
		    
		    Label SepPerform1 = new Label(CreateAccountPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform1.setBackground(new Color(display,255,255,255));
		    SepPerform1.setLayoutData(CreateAccountCompositeData);
		    
		    GridData griddataLabel = new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 1,1);
		    //griddataLabel.horizontalSpan=1;
		    griddataLabel.widthHint=90;
		    GridData griddataText = new GridData(GridData.FILL, GridData.CENTER, true, false, 1,1);
		    //griddataText.horizontalSpan=1;
		    //griddataText.widthHint= 400;
		    
		    Label BankLabel = new Label(CreateAccountPage,SWT.NONE);
		    BankLabel.setText("Bank:");
		    BankLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeBank = new Text(CreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeBank.setLayoutData(griddataText);
			
			Label CustomerLabel = new Label(CreateAccountPage, SWT.NONE);
			CustomerLabel.setText("Customer:");
			CustomerLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeCustomer = new Text(CreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeCustomer.setLayoutData(griddataText);
			
			Label ResponsibleLabel = new Label(CreateAccountPage, SWT.NONE);
			ResponsibleLabel.setText("Responsible:");
			ResponsibleLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeResponsible = new Text(CreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeResponsible.setLayoutData(griddataText);
			
			Label AccountTypeLabel = new Label(CreateAccountPage, SWT.NONE);
			AccountTypeLabel.setText("AccountType:");
			AccountTypeLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeAccountType = new Text(CreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeAccountType.setLayoutData(griddataText);
			
			Label ActiveLabel = new Label(CreateAccountPage, SWT.NONE);
			ActiveLabel.setText("Active:");
			ActiveLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeActive = new Text(CreateAccountPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeActive.setLayoutData(griddataText);
			
		    CaptionCreateAccountPage.pack();
		    
		    Label SepPerform2 = new Label(CreateAccountPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform2.setBackground(new Color(display,255,255,255));
		    SepPerform2.setLayoutData(CreateAccountCompositeData);
		    
		    final Button CreateAccountButton = new Button(CreateAccountPage, SWT.PUSH);
		    CreateAccountButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    CreateAccountButton.setText("Create Account");
		    CreateAccountButton.setBackground(new Color(display, 31, 78, 121));
		    CreateAccountButton.setLayoutData(griddataButton);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite CreateCustomerPage = new Composite(compositeContent, SWT.NONE);
		    CreateCustomerPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
//		    GridLayout CreateCustomerComposite = new GridLayout(2, false);
		    GridData CreateCustomerCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
//		    CreateCustomerPage.setLayout(CreateCustomerComposite);
		    CreateCustomerPage.setLayout(layoutMainClient);
		    CreateCustomerCompositeData.horizontalSpan = 2;
		    Label CaptionCreateCustomerPage = new Label(CreateCustomerPage, SWT.NONE);
		    CaptionCreateCustomerPage.setText("Create a new Customer");
		    CaptionCreateCustomerPage.setLayoutData(CreateCustomerCompositeData);
		    
		    Label SepPerform3 = new Label(CreateCustomerPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform3.setBackground(new Color(display,255,255,255));
		    SepPerform3.setLayoutData(CreateCustomerCompositeData);
		    
		    Label FirstnameLabel = new Label(CreateCustomerPage,SWT.NONE);
		    FirstnameLabel.setText("Firstname:");
		    FirstnameLabel.setLayoutData(griddataLabel);
			Text CreateCustomerTypeFirstname = new Text(CreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			CreateCustomerTypeFirstname.setLayoutData(griddataText);
			
			Label LastnameLabel = new Label(CreateCustomerPage, SWT.NONE);
			LastnameLabel.setText("Lastname:");
			LastnameLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeLastname = new Text(CreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeLastname.setLayoutData(griddataText);
			
			Label InitPasswordLabel = new Label(CreateCustomerPage, SWT.NONE);
			InitPasswordLabel.setText("Initial Password:");
			InitPasswordLabel.setLayoutData(griddataLabel);
			Text CreateAccountTypeInitPassword = new Text(CreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			CreateAccountTypeInitPassword.setLayoutData(griddataText);
		    
		    CaptionCreateCustomerPage.pack();
		    
		    Label SepPerform4 = new Label(CreateCustomerPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform4.setBackground(new Color(display,255,255,255));
		    SepPerform4.setLayoutData(CreateCustomerCompositeData);
		    
		    final Button CreateCustomerButton = new Button(CreateCustomerPage, SWT.PUSH);
		    CreateCustomerButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    CreateCustomerButton.setText("Create Customer");
		    CreateCustomerButton.setBackground(new Color(display, 31, 78, 121));
		    CreateCustomerButton.setLayoutData(griddataButton);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    //Events
		    
		    loginButton.addListener(SWT.Selection, new Listener() {
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
		        	stackLayoutContent.topControl = CreateAccountPage;
			          compositeContent.layout();
			        }
			      });
		    
		    buttonMenuCreateCustomer.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		        	stackLayoutContent.topControl = CreateCustomerPage;
			          compositeContent.layout();
			        }
			      });
		    
		    buttonLogout.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent arg0) {
		    		display.dispose();
		    	}
		    });
		    
		    DeactivateAccountButton.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to deactivate selected account
			          //stacklayout.topControl = DepositPage;
			          //compositeMain.layout();
			        }
			      });
		    
		    ActivateAccountButton.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to activate selected account
			          //stacklayout.topControl = DepositPage;
			          //compositeMain.layout();
			        }
			      });
		    
		    CreateAccountButton.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to create account
			          //stacklayout.topControl = DepositPage;
			          //compositeMain.layout();
			        }
			      });
		    
		    CreateCustomerButton.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          //do something here to create customer
			          //stacklayout.topControl = DepositPage;
			          //compositeMain.layout();
			        }
			      });
		    
		    //Events Ende
		    
		    
		    //shell.setLayout(layout);
		    stackLayoutMain.topControl=compositeLogin;
		    //compositeWindow.layout();
		    //shell.layout();
		    
		    shell.pack();
		    shell.open();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    display.dispose();
}

	 private static void fillCompositeWelcomePage() {
		 
		 GridData WelcomeCompositeData = new GridData(GridData.BEGINNING, GridData.FILL,true, false);
		    WelcomeCompositeData.horizontalSpan = 2;
		    Label CaptionWelcomePage = new Label(compositeWelcomePage, SWT.NONE);
		    CaptionWelcomePage.setText("Welcome to the Adminstration Frontend!");
		    CaptionWelcomePage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionWelcomePage.setLayoutData(WelcomeCompositeData);
		
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
	}

	private static void fillCompositeMainClient() {

		//Header
	    Label LabelHorizontal = new Label(compositeHeader,SWT.NONE);
	    LabelHorizontal.setText("iCash - Administration");
	    LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
	    LabelHorizontal.setBackground(new Color(display, 200,200,200));
//		    final Button buttonLogout = new Button(compositeHeader, SWT.PUSH);
	    GridData griddataLogoutButton = new GridData(GridData.FILL, GridData.CENTER, true, false);
	    griddataLogoutButton.horizontalAlignment = GridData.END;
	    buttonLogout = new Button(compositeHeader, SWT.PUSH);
	    buttonLogout.setLayoutData(griddataLogoutButton);
	    buttonLogout.setBackground(new Color(display, 31, 78, 121));
	    buttonLogout.setText("Log out!");
	    
	    //Navigation
	    final GridData griddataMenuContent = new GridData(GridData.FILL, GridData.CENTER,true, false);
//	    griddataVertical.verticalAlignment = GridData.CENTER;
	    
//	    final Button buttonDeactivateAccount = new Button(compositeNavigation, SWT.PUSH);
	    buttonMenuDeactivateAccount = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuDeactivateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuDeactivateAccount.setText("Deactivate Account");
		    buttonMenuDeactivateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuDeactivateAccount.setLayoutData(griddataMenuContent);
	    
	    Label placeholder4 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder4.setBackground(new Color(display, 200,200,200));
		    placeholder4.setLayoutData(griddataMenuContent);
	    
//	    final Button buttonCreateAccount = new Button(compositeNavigation, SWT.PUSH);
	    buttonMenuCreateAccount = new Button(compositeNavigation, SWT.PUSH);
		    buttonMenuCreateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    buttonMenuCreateAccount.setText("Create Account");
		    buttonMenuCreateAccount.setBackground(new Color(display, 31, 78, 121));
		    buttonMenuCreateAccount.setLayoutData(griddataMenuContent);
	    
	    Label placeholder5 = new Label(compositeNavigation, SWT.SEPARATOR | SWT.HORIZONTAL);
		    placeholder5.setBackground(new Color(display, 200,200,200));
		    placeholder5.setLayoutData(griddataMenuContent);
	    
//	    final Button buttonCreateCustomer = new Button(compositeNavigation, SWT.PUSH);
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
	      
    	shell.setLayout(stackLayoutMain);
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
		    
//		    final Button loginButton = new Button(compositeLogin,SWT.PUSH);
		    loginButton = new Button(compositeLogin,SWT.PUSH);
			    loginButton.setText("Login NOW!");
			    loginButton.setLayoutData(griddataLoginButton);
		    
		    compositeLogin.pack();
	 }
}