package classes;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class CustomerClient {
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
		    Button PerformTransaction = new Button(compositeVertical, SWT.PUSH);
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
		    
		    //Events
		    ViewTransaction.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent arg0) {
					Button DepositMoney = new Button(compositeMain, SWT.PUSH);
					 DepositMoney.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
					    DepositMoney.setText("Deposit Money");
					    DepositMoney.setBackground(new Color(display, 31, 78, 121));
					    DepositMoney.setLayoutData(griddataVertical);
				}
			});
		    
		    LogOut.addSelectionListener(new SelectionAdapter() {
		    	public void widgetSelected(SelectionEvent arg0) {
		    		display.dispose();
		    	}
		    });
		    //Events Ende
		    
		    
		    

		    
		    //layout = new GridLayout(2,false);
		    shell.setLayout(layout);
		    /*final Table table = new Table(shell,
		    		SWT.SINGLE | SWT.H_SCROLL |
		    		SWT.V_SCROLL | SWT.BORDER |
		    		SWT.FULL_SELECTION );
		    		// Drei Tabellenspalten erzeugen
		    		final TableColumn col1 = new TableColumn(table,SWT.LEFT);
		    		col1.setText("Spalte 1");
		    		col1.setWidth(80);
		    		final TableColumn col2 = new TableColumn(table,SWT.LEFT);
		    		col2.setText("Spalte 2");
		    		col2.setWidth(80);
		    		final TableColumn col3 = new TableColumn(table,SWT.LEFT);
		    		col3.setText("Spalte 3");
		    		col3.setWidth(80);
		    		// Spaltenköpfe und Trennlinien sichtbar machen
		    		table.setHeaderVisible(true);
		    		table.setLinesVisible(true);
		    		// Zwei Tabellenreihen erzeugen
		    		final TableItem item1 = new TableItem(table,0)
		    		item1.setText(new String[] {"a","b","c"});
		    		final TableItem item2 = new TableItem(table,0);
		    		item2.setText(new String[] {"d","c","e"});
		    		// SelectionListener hinzufügen
		    		table.addSelectionListener(new SelectionAdapter() {
		    		public void widgetDefaultSelected(SelectionEvent e) {
		    		processSelection("Enter gedrückt: ");
		    		}
		    		public void widgetSelected(SelectionEvent e) {
		    		processSelection("Tabellenelement ausgewählt: ");
		    		}
		    		private void processSelection(String message) {
		    		// Ausgewählte Tabellenzeilen holen
		    			 TableItem[] selection = table.getSelection();
		    			// Wegen SWT.SINGLE ist nur eine Zeile ausgewählt
		    			TableItem selectedRow = selection[0];
		    			// Die einzelnen Tabellenelemente für Ausgabe aufbereiten
		    			String s = selectedRow.getText(0)+", "+
		    			selectedRow.getText(1)+", "+selectedRow.getText(2);
		    			System.out.println(message + s);
		    			}
		    			});*/
		    
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
