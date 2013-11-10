package classes;


import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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
		    Display display = new Display();
		    Shell shell = new Shell(display);
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
		    LabelHorizontal.setText("ICash");
		    LabelHorizontal.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		    LabelHorizontal.setBackground(new Color(display, 200,200,200));
		    Button LogOut = new Button(compositeHorizontal, SWT.PUSH);
		    GridData griddataHorizontal = new GridData(GridData.FILL, GridData.CENTER, true, false);
		    griddataHorizontal.horizontalAlignment = GridData.END;
		    LogOut.setLayoutData(griddataHorizontal);
		    LogOut.setText("Log out!");
		    compositeHorizontal.setLayout(layoutCompositeHorizontal);
		    
		    final Composite compositeVertical = new Composite(shell, 0);
		    compositeVertical.setBackground(new Color(display,200,200,200));
		    compositeVertical.setBounds(100,200,200,200);
		    compositeVertical.setLayoutData(griddatavertical);
		    
		    GridLayout layoutCompositeVertical = new GridLayout(1, false);
		    GridData griddataVertical = new GridData(GridData.FILL, GridData.FILL,true, false);
		    griddataVertical.verticalAlignment = GridData.CENTER;
		    Button DeactivateAccount = new Button(compositeVertical, SWT.PUSH);
		    DeactivateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    DeactivateAccount.setText("Deactivate Account");
		    DeactivateAccount.setLayoutData(griddataVertical);
		    Label label1 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    Button CreateAccount = new Button(compositeVertical, SWT.PUSH);
		    CreateAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    CreateAccount.setText("Create Account");
		    CreateAccount.setLayoutData(griddataVertical);
		    Label label2 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    Button CreateCustomer = new Button(compositeVertical, SWT.PUSH);
		    CreateCustomer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		    CreateCustomer.setText("Create Customer");
		    CreateCustomer.setLayoutData(griddataVertical);
		    Label label3 = new Label(compositeVertical, SWT.SEPARATOR | SWT.HORIZONTAL);
		    compositeVertical.setLayout(layoutCompositeVertical);
		    
		    
		    
		    final Composite compositeMain = new Composite(shell, 0);
		    compositeMain.setBackground(new Color(display,255,255,255));
		    compositeMain.setLayoutData(griddataMain);
		    

		    
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
}