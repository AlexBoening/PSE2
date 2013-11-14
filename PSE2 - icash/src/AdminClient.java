

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
		
	 public static void main(String[] args) {
		 final Display display = new Display();
		    final Shell shell = new Shell(display);
		    GridLayout layout = new GridLayout(2, false);
		    GridLayout Loginlayout = new GridLayout(5,false);
		    final StackLayout stackLoginLayout = new StackLayout();
		    shell.setLayout(stackLoginLayout);
		    //shell.setLayout(layout);

		    //
		    //
		    //
		    //
		    //
		    //
		    
		    GridData griddataWindow = new GridData(GridData.FILL, GridData.CENTER,true, true);
		    griddataWindow.horizontalAlignment=GridData.FILL;
		    griddataWindow.verticalAlignment=GridData.FILL;
		    griddataWindow.horizontalSpan=5;
		    griddataWindow.heightHint=300;
		    griddataWindow.widthHint=300;
		    
		    //
		    
		    
		    final Composite compositeLogin = new Composite(shell,0);
		    compositeLogin.setBackground(new Color(display,200,200,200));
		    compositeLogin.setLayoutData(griddataWindow);
		    compositeLogin.setLayout(Loginlayout);
		    
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
		    
		    final Button loginButton = new Button(compositeLogin,SWT.PUSH);
		    loginButton.setText("Login NOW!");
		    loginButton.setLayoutData(griddataLoginButton);
		    
		    
		    final Composite compositeWindow = new Composite(shell, 0);
		    compositeWindow.setBackground(new Color(display,255,255,255));
		    compositeWindow.setLayoutData(griddataWindow);
		    compositeWindow.setLayout(layout);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    GridData griddatahorizontal = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddatahorizontal.horizontalSpan = 2;
		    
		    GridData griddatavertical = new GridData();
		    griddatavertical.verticalAlignment = GridData.FILL;
		    griddatavertical.grabExcessVerticalSpace = true;
		    
		    GridData griddataMain = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddataMain.verticalAlignment = GridData.FILL;
		    
		    final Composite compositeHorizontal = new Composite(compositeWindow, 0);
		    compositeHorizontal.setBackground(new Color(display,200,200,200));	
		    compositeHorizontal.setLayoutData(griddatahorizontal);
		    
		    GridLayout layoutCompositeHorizontal = new GridLayout(2, false);
		    Label LabelHorizontal = new Label(compositeHorizontal,SWT.NONE);
		    LabelHorizontal.setText("iCash - Administration");
		    LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    LabelHorizontal.setBackground(new Color(display, 200,200,200));
		    final Button LogOut = new Button(compositeHorizontal, SWT.PUSH);
		    GridData griddataHorizontal = new GridData(GridData.FILL, GridData.CENTER, true, false);
		    griddataHorizontal.horizontalAlignment = GridData.END;
		    LogOut.setLayoutData(griddataHorizontal);
		    LogOut.setBackground(new Color(display, 31, 78, 121));
		    LogOut.setText("Log out!");
		    compositeHorizontal.setLayout(layoutCompositeHorizontal);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite compositeVertical = new Composite(compositeWindow, 0);
		    compositeVertical.setBackground(new Color(display,200,200,200));
		    compositeVertical.setBounds(100,200,200,200);
		    compositeVertical.setLayoutData(griddatavertical);
		    ((GridData)compositeVertical.getLayoutData()).widthHint=150;
		    
		    GridLayout layoutCompositeVertical = new GridLayout(1, false);
		    final GridData griddataVertical = new GridData(GridData.FILL, GridData.FILL,true, false);
		    griddataVertical.verticalAlignment = GridData.CENTER;
		    
		    final Button DeactivateAccount = new Button(compositeVertical, SWT.PUSH);
		    DeactivateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    DeactivateAccount.setText("Deactivate Account");
		    DeactivateAccount.setBackground(new Color(display, 31, 78, 121));
		    DeactivateAccount.setLayoutData(griddataVertical);
		    Label label1 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label1.setBackground(new Color(display, 200,200,200));
		    
		    final Button CreateAccount = new Button(compositeVertical, SWT.PUSH);
		    CreateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    CreateAccount.setText("Create Account");
		    CreateAccount.setBackground(new Color(display, 31, 78, 121));
		    CreateAccount.setLayoutData(griddataVertical);
		    label1.setLayoutData(griddataVertical);
		    Label label2 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label2.setBackground(new Color(display, 200,200,200));
		    
		    final Button CreateCustomer = new Button(compositeVertical, SWT.PUSH);
		    CreateCustomer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    CreateCustomer.setText("Create Customer");
		    CreateCustomer.setBackground(new Color(display, 31, 78, 121));
		    CreateCustomer.setLayoutData(griddataVertical);
		    label2.setLayoutData(griddataVertical);
		    Label label3 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label3.setBackground(new Color(display, 200,200,200));
		    compositeVertical.setLayout(layoutCompositeVertical);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite compositeMain = new Composite(compositeWindow, 0);
		    compositeMain.setBackground(new Color(display,255,255,255));
		    compositeMain.setLayoutData(griddataMain);
		    final StackLayout stacklayout = new StackLayout();
		    compositeMain.setLayout(stacklayout);
		    
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite WelcomePage = new Composite(compositeMain, SWT.NONE);
		    WelcomePage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout WelcomeComposite = new GridLayout(2, false);
		    GridData WelcomeCompositeData = new GridData(GridData.BEGINNING, GridData.FILL,true, false);
		    WelcomePage.setLayout(WelcomeComposite);
		    WelcomeCompositeData.horizontalSpan = 2;
		    Label CaptionWelcomePage = new Label(WelcomePage, SWT.NONE);
		    CaptionWelcomePage.setText("Welcome to the Adminstration Frontend!");
		    CaptionWelcomePage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionWelcomePage.setLayoutData(WelcomeCompositeData);
		    stacklayout.topControl = WelcomePage;
	        compositeMain.layout();
	        
		    //
		    //
		    //
		    //
		    //
		    //
		    
		    final Composite AccountPage = new Composite(compositeMain, SWT.NONE);
		    AccountPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout ViewComposite = new GridLayout(2, false);
		    GridData ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    AccountPage.setLayout(ViewComposite);
		    ViewCompositeData.horizontalSpan = 2;
		    Label CaptionViewPage = new Label(AccountPage, SWT.NONE);
		    CaptionViewPage.setText("All Accounts");
		    CaptionViewPage.setLayoutData(ViewCompositeData);
		    final Table table = new Table(AccountPage,
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
		    
		    final Button DeactivateAccountButton = new Button(AccountPage, SWT.PUSH);
		    DeactivateAccountButton.setLayoutData(new GridData(SWT.NONE, SWT.NONE, true, true));
		    DeactivateAccountButton.setText("Deacitvate Account");
		    DeactivateAccountButton.setBackground(new Color(display, 31, 78, 121));
		    DeactivateAccountButton.setLayoutData(griddataButton);
		    final Button ActivateAccountButton = new Button(AccountPage, SWT.PUSH);
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
		    
		    final Composite CreateAccountPage = new Composite(compositeMain, SWT.NONE);
		    CreateAccountPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout CreateAccountComposite = new GridLayout(2, false);
		    GridData CreateAccountCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    CreateAccountPage.setLayout(CreateAccountComposite);
		    CreateAccountCompositeData.horizontalSpan = 2;
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
		    
		    final Composite CreateCustomerPage = new Composite(compositeMain, SWT.NONE);
		    CreateCustomerPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout CreateCustomerComposite = new GridLayout(2, false);
		    GridData CreateCustomerCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    CreateCustomerPage.setLayout(CreateCustomerComposite);
		    CreateCustomerCompositeData.horizontalSpan = 2;
		    Label CaptionCreateCustomerPage = new Label(CreateCustomerPage, SWT.NONE);
		    CaptionCreateCustomerPage.setText("Create a new Customer");
		    CaptionCreateCustomerPage.setLayoutData(CreateCustomerCompositeData);
		    
		    
		    new Label(CreateCustomerPage, SWT.NONE).setText("Firstname:");
			Text CreateCustomerTypeFirstname = new Text(CreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			new Label(CreateCustomerPage, SWT.NONE).setText("Secondname:");
			Text CreateCustomerTypeSecondname = new Text(CreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			new Label(CreateCustomerPage, SWT.NONE).setText("Initial Password:");
			Text CreateCustomerTypePassword = new Text(CreateCustomerPage, SWT.SINGLE | SWT.BORDER);
			//connectData.horizontalSpan = 2;
			//connectText.setLayoutData(connectData);
		    CaptionCreateAccountPage.pack();
		    
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
			          stackLoginLayout.topControl = compositeWindow;
			          shell.layout();
			        }
			      });
		    
		    DeactivateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		          stacklayout.topControl = AccountPage;
		          compositeMain.layout();
		        }
		      });
		    
		    CreateAccount.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          stacklayout.topControl = CreateAccountPage;
			          compositeMain.layout();
			        }
			      });
		    
		    CreateCustomer.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          stacklayout.topControl = CreateCustomerPage;
			          compositeMain.layout();
			        }
			      });
		    
		    LogOut.addSelectionListener(new SelectionAdapter() {
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
		    stackLoginLayout.topControl=compositeLogin;
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
	
}