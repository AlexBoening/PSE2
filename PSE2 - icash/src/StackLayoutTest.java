

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class StackLayoutTest {
	
	static int pageNum = -1;
	
	 public static void main(String[] args) {
		    final Display display = new Display();
		    final Shell shell = new Shell(display);
		    GridLayout layout = new GridLayout(2, false);
		    shell.setLayout(layout);
		    
		    GridData griddatahorizontal = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddatahorizontal.horizontalSpan = 2;
		    
		    GridData griddatavertical = new GridData();
		    griddatavertical.verticalAlignment = GridData.FILL;
		    griddatavertical.grabExcessVerticalSpace = true;
		    
		    GridData griddataMain = new GridData(GridData.FILL, GridData.CENTER,true, false);
		    griddataMain.verticalAlignment = GridData.FILL;
		    
		    final Composite compositeHorizontal = new Composite(shell, 0);
		    compositeHorizontal.setBackground(new Color(display,200,200,200));	
		    compositeHorizontal.setLayoutData(griddatahorizontal);
		    
		    GridLayout layoutCompositeHorizontal = new GridLayout(2, false);
		    Label LabelHorizontal = new Label(compositeHorizontal,SWT.NONE);
		    LabelHorizontal.setText("iCash");
		    LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    LabelHorizontal.setBackground(new Color(display, 200,200,200));
		    final Button LogOut = new Button(compositeHorizontal, SWT.PUSH);
		    GridData griddataHorizontal = new GridData(GridData.FILL, GridData.CENTER, true, false);
		    griddataHorizontal.horizontalAlignment = GridData.END;
		    LogOut.setLayoutData(griddataHorizontal);
		    LogOut.setBackground(new Color(display, 31, 78, 121));
		    LogOut.setText("Log out!");
		    compositeHorizontal.setLayout(layoutCompositeHorizontal);
		    
		    final Composite compositeVertical = new Composite(shell, 0);
		    compositeVertical.setBackground(new Color(display,200,200,200));
		    compositeVertical.setBounds(100,200,200,200);
		    compositeVertical.setLayoutData(griddatavertical);
		    ((GridData)compositeVertical.getLayoutData()).widthHint=150;
		    
		    GridLayout layoutCompositeVertical = new GridLayout(1, false);
		    final GridData griddataVertical = new GridData(GridData.FILL, GridData.FILL,true, false);
		    griddataVertical.verticalAlignment = GridData.CENTER;
		    final Button ViewTransaction = new Button(compositeVertical, SWT.PUSH);
		    ViewTransaction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    ViewTransaction.setText("View Transaction");
		    ViewTransaction.setBackground(new Color(display, 31, 78, 121));
		    ViewTransaction.setLayoutData(griddataVertical);
		    Label label1 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label1.setBackground(new Color(display, 200,200,200));
		    final Button PerformTransaction = new Button(compositeVertical, SWT.PUSH);
		    PerformTransaction.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    PerformTransaction.setText("Perform Transaction");
		    PerformTransaction.setBackground(new Color(display, 31, 78, 121));
		    PerformTransaction.setLayoutData(griddataVertical);
		    label1.setLayoutData(griddataVertical);
		    Label label2 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label2.setBackground(new Color(display, 200,200,200));
		    Button DepositMoney = new Button(compositeVertical, SWT.PUSH);
		    DepositMoney.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    DepositMoney.setText("Deposit Money");
		    DepositMoney.setBackground(new Color(display, 31, 78, 121));
		    DepositMoney.setLayoutData(griddataVertical);
		    label2.setLayoutData(griddataVertical);
		    Label label3 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label3.setBackground(new Color(display, 200,200,200));
		    compositeVertical.setLayout(layoutCompositeVertical);
		    Button WithdrawMoney = new Button(compositeVertical, SWT.PUSH);
		    WithdrawMoney.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    WithdrawMoney.setText("Withdraw Money");
		    WithdrawMoney.setBackground(new Color(display, 31, 78, 121));
		    WithdrawMoney.setLayoutData(griddataVertical);
		    label3.setLayoutData(griddataVertical);
		    Label label4 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    label4.setBackground(new Color(display, 200,200,200));
		    label4.setLayoutData(griddataVertical);
		    Label CurrentBalanceText = new Label(compositeVertical, SWT.END);
		    CurrentBalanceText.setBackground(new Color(display, 200,200,200));
		    griddataVertical.verticalAlignment = GridData.FILL;
		    CurrentBalanceText.setText("Current Balance");
		    CurrentBalanceText.setLayoutData(new GridData(SWT.FILL, SWT.END, true, true));
		    Label CurrentBalance = new Label(compositeVertical, SWT.FILL);
		    CurrentBalance.setBackground(new Color(display, 70,200,230));
		    griddataVertical.verticalAlignment = GridData.FILL;
		    //CurrentBalance.setText("Current Balance");
		    CurrentBalance.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		    
		    
		    final Composite compositeMain = new Composite(shell, 0);
		    compositeMain.setBackground(new Color(display,255,255,255));
		    compositeMain.setLayoutData(griddataMain);
		    final StackLayout stacklayout = new StackLayout();
		    compositeMain.setLayout(stacklayout);
		    
		    final Composite ViewPage = new Composite(compositeMain, SWT.NONE);
		    ViewPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout ViewComposite = new GridLayout(2, false);
		    GridData ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    ViewPage.setLayout(ViewComposite);
		    ViewCompositeData.horizontalSpan = 2;
		    ViewCompositeData.horizontalAlignment = GridData.CENTER;
		    Label CaptionViewPage = new Label(ViewPage, SWT.NONE);
		    CaptionViewPage.setText("View your Transaction!");
		    CaptionViewPage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionViewPage.setBackground(new Color(display, 255,255,255));
		    CaptionViewPage.setLayoutData(ViewCompositeData);
		    
		    /*ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    ViewCompositeData.horizontalSpan = 2;
		    Label SepView1 = new Label(ViewPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepView1.setBackground(new Color(display,255,255,255));
		    SepView1.setLayoutData(ViewCompositeData);*/
		    
		    final Table table = new Table(ViewPage,
		    		SWT.SINGLE | SWT.H_SCROLL |
		    		SWT.V_SCROLL | SWT.BORDER |
		    		SWT.FULL_SELECTION );
		    		// Drei Tabellenspalten erzeugen
		    		final TableColumn col1 = new TableColumn(table,SWT.LEFT);
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
		    		
		    		ViewCompositeData = new GridData(GridData.FILL, GridData.FILL,true, true);
		    		table.setLayoutData(ViewCompositeData);
		    		
		    CaptionViewPage.pack();

		    // create the second page's content
		    final Composite PerformPage = new Composite(compositeMain, SWT.NONE);
		    PerformPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout PerformComposite = new GridLayout(2, false);
		    GridData PerformCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    PerformPage.setLayout(PerformComposite);
		    PerformCompositeData.horizontalSpan = 2;
		    PerformCompositeData.horizontalAlignment = GridData.CENTER;
		    Label CaptionPerformPage = new Label(PerformPage, SWT.NONE);
		    CaptionPerformPage.setText("Perform your Transaction!");
		    CaptionPerformPage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionPerformPage.setBackground(new Color(display, 255,255,255));
		    CaptionPerformPage.setLayoutData(PerformCompositeData);
		    
		    PerformCompositeData.horizontalAlignment = GridData.BEGINNING;
		    Label SepPerform1 = new Label(PerformPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepPerform1.setBackground(new Color(display,255,255,255));
		    SepPerform1.setLayoutData(PerformCompositeData);
		    
		    new Label(PerformPage, SWT.NONE).setText("To Account:");
			Text ToAccountPerform = new Text(PerformPage, SWT.SINGLE | SWT.BORDER);
			new Label(PerformPage, SWT.NONE).setText("BLZ:");
			Text BLZPerform = new Text(PerformPage, SWT.SINGLE | SWT.BORDER);
			new Label(PerformPage, SWT.NONE).setText("Amount:");
			Text AmountPerform = new Text(PerformPage, SWT.SINGLE | SWT.BORDER);
			new Label(PerformPage, SWT.NONE).setText("Description:");
			Text DescriptionPerform = new Text(PerformPage, SWT.SINGLE | SWT.BORDER);
			
			Label SepPerform2 = new Label(PerformPage, SWT.SEPARATOR | SWT.HORIZONTAL);
			PerformCompositeData.horizontalSpan = 2;
			SepPerform2.setBackground(new Color(display,255,255,255));
		    SepPerform2.setLayoutData(PerformCompositeData);
			
			final Button ButtonCommitPerform = new Button(PerformPage, SWT.PUSH);
			ButtonCommitPerform.setText("Commit");
		    CaptionPerformPage.pack();
		    
		    final Composite DepositPage = new Composite(compositeMain, SWT.NONE);
		    DepositPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout DepositComposite = new GridLayout(2, false);
		    GridData DepositCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    DepositPage.setLayout(DepositComposite);
		    DepositCompositeData.horizontalSpan = 2;
		    DepositCompositeData.horizontalAlignment = GridData.CENTER;
		    Label CaptionDepositPage = new Label(DepositPage, SWT.NONE);
		    CaptionDepositPage.setText("Desposit your money to the best bank out there!");
		    CaptionDepositPage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionDepositPage.setBackground(new Color(display, 255,255,255));
		    CaptionDepositPage.setLayoutData(PerformCompositeData);
		    
		    /*DepositCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    DepositCompositeData.horizontalSpan = 2;
		    DepositCompositeData.horizontalAlignment = GridData.BEGINNING;
		    Label SepDeposit1 = new Label(DepositPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepDeposit1.setBackground(new Color(display,255,255,255));
		    SepDeposit1.setLayoutData(DepositCompositeData);*/
		    
		    new Label(DepositPage, SWT.NONE).setText("Amount:");
			Text AmountDeposit = new Text(DepositPage, SWT.SINGLE | SWT.BORDER);
			//GridData connectData = new GridData(GridData.FILL, GridData.CENTER, true, false);
			new Label(DepositPage, SWT.NONE).setText("Description:");
			Text DescriptionDeposit = new Text(DepositPage, SWT.SINGLE | SWT.BORDER);
			
		    /*Label SepDeposit2 = new Label(DepositPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepDeposit2.setBackground(new Color(display,255,255,255));
		    SepDeposit2.setLayoutData(DepositCompositeData);*/
			
			final Button ButtonCommitDeposit = new Button(DepositPage, SWT.PUSH);
			ButtonCommitDeposit.setText("Deposit");
						
			CaptionDepositPage.pack();
			

		    // create the second page's content
		    final Composite WithdrawPage = new Composite(compositeMain, SWT.NONE);
		    WithdrawPage.setBackground(new Color(display,255,255,255));
		    //compositeMain.setLayoutData(griddataMain);
		    GridLayout WithdrawComposite = new GridLayout(2, false);
		    GridData WithdrawCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    WithdrawPage.setLayout(WithdrawComposite);
		    WithdrawCompositeData.horizontalSpan = 2;
		    WithdrawCompositeData.horizontalAlignment = SWT.CENTER;
		    Label CaptionWithdrawPage = new Label(WithdrawPage, SWT.NONE);
		    CaptionWithdrawPage.setText("Where are you goining with your money?!");
		    CaptionWithdrawPage.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    CaptionWithdrawPage.setBackground(new Color(display, 255,255,255));
		    CaptionWithdrawPage.setLayoutData(WithdrawCompositeData);
		    
		   /* WithdrawCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    WithdrawCompositeData.horizontalSpan = 2;
		    Label SepWithdraw1 = new Label(WithdrawPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepWithdraw1.setBackground(new Color(display,255,255,255));
		    SepWithdraw1.setLayoutData(WithdrawCompositeData);*/
		    
		    new Label(WithdrawPage, SWT.NONE).setText("Amount:");
			Text AmountWithdraw = new Text(WithdrawPage, SWT.SINGLE | SWT.BORDER);
			//GridData connectData = new GridData(GridData.FILL, GridData.CENTER, true, false);
			new Label(WithdrawPage, SWT.NONE).setText("Description:");
			Text DescriptionWithdraw = new Text(WithdrawPage, SWT.SINGLE | SWT.BORDER);
			
		   /* WithdrawCompositeData = new GridData(GridData.FILL, GridData.FILL,true, false);
		    WithdrawCompositeData.horizontalSpan = 2;
		    Label SepWithdraw2 = new Label(WithdrawPage, SWT.SEPARATOR | SWT.HORIZONTAL);
		    SepWithdraw2.setBackground(new Color(display,255,255,255));
		    SepWithdraw2.setLayoutData(WithdrawCompositeData);*/
			
			final Button ButtonCommitWithdraw = new Button(WithdrawPage, SWT.PUSH);
			ButtonCommitWithdraw.setText("Withdraw");
			CaptionWithdrawPage.pack();
		    
		    
		    //Events
		    ViewTransaction.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		          pageNum = ++pageNum % 2;
		          //stacklayout.topControl = pageNum == 0 ? page0 : page1;
		          stacklayout.topControl = ViewPage;
		          compositeMain.layout();
		        }
		      });
		    
		    PerformTransaction.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          pageNum = ++pageNum % 2;
			          //stacklayout.topControl = pageNum == 0 ? page0 : page1;
			          stacklayout.topControl = PerformPage;
			          compositeMain.layout();
			        }
			      });
		    
		    DepositMoney.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
		          pageNum = ++pageNum % 2;
		          //stacklayout.topControl = pageNum == 0 ? page0 : page1;
		          stacklayout.topControl = DepositPage;
		          compositeMain.layout();
		        }
		      });
		    
		    WithdrawMoney.addListener(SWT.Selection, new Listener() {
		        public void handleEvent(Event event) {
			          pageNum = ++pageNum % 2;
			          //stacklayout.topControl = pageNum == 0 ? page0 : page1;
			          stacklayout.topControl = WithdrawPage;
			          compositeMain.layout();
			        }
			      });
		    
		    LogOut.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent arg0) {
		    		display.dispose();
		    	}
		    });

		    shell.setLayout(layout);

		    
		    shell.pack();
		    shell.open();
		    while (!shell.isDisposed()) {
		      if (!display.readAndDispatch()) {
		        display.sleep();
		      }
		    }
		    display.dispose();
}
	 
	 public static Composite ViewTransactions(Shell shell, Display display, Composite compositeMain, GridData griddataVertical){
		 //View Transactions here
		 Composite CompositeTransaction = compositeMain;
		 Button DepositMoney = new Button(CompositeTransaction, SWT.PUSH);
		 DepositMoney.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    DepositMoney.setText("Deposit Money");
		    DepositMoney.setBackground(new Color(display, 31, 78, 121));
		    DepositMoney.setLayoutData(griddataVertical);
		 return CompositeTransaction;
	 }
}
