import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;


public class DisplayTest2 {
	public static void main(String[] args) {
        Display display = Display.getDefault();
        createContents();
        Shell shell = new Shell(display);
        shell.setSize(750, 300);
		shell.setText("Bank Client 0.1");

		shell.setLayout(new FillLayout());
        Composite mainComposite = new Composite(shell, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(mainComposite);
        
        final Button c = new Button(mainComposite, SWT.PUSH);
        c.setText("Dieser Button war invisible");
        c.setVisible(false);
        
        final Button b = new Button(mainComposite, SWT.PUSH);
        b.setText("Hallo SWT");
        b.setVisible(true);
        
        c.addSelectionListener(new SelectionListener() {

  	      public void widgetSelected(SelectionEvent event) {
  	        b.setVisible(true);
  	      }
  	
  	      public void widgetDefaultSelected(SelectionEvent event) {
  	    	  b.setVisible(true);
  	      }
  	    });

        b.addSelectionListener(new SelectionListener() {

	      public void widgetSelected(SelectionEvent event) {
	        c.setVisible(true);
	      }
	
	      public void widgetDefaultSelected(SelectionEvent event) {
	    	  c.setVisible(true);
	      }
	    });
        c.pack();
        b.pack();
        /*      Label label1 = new Label(shell, SWT.BORDER);
        
        label1.setText("kleiner Test");
        label1.setSize(200,200);
        label1.setBackground(new Color(display,200,100,50));*/
        
                
        shell.setSize(500, 500);
        shell.open();
        
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) 
                display.sleep();
        
    }
}
