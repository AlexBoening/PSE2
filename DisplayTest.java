import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DisplayTest {
	public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        
        Button b = new Button(shell, SWT.PUSH);
        b.setText("Hallo SWT");
        b.pack();
        
        shell.setSize(200, 200);
        shell.open();
        
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) 
                display.sleep();
    }

}
