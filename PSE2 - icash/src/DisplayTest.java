import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class DisplayTest {
	public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        
        Button b = new Button(shell, SWT.PUSH);
        b.setText("Hallo SWT");
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
