import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class Program {
	public static void main(String[] args) {
		try{
			Program window = new Program();
			window.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private Shell shell;
	
	public void open(){
		
		Display display = new Display();
        
        createHome(display);
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) 
                display.sleep();
	}
	
	public void createHome(Display display){
	
		shell = new Shell(display);
		shell.setSize(700, 500);
		GridLayout gridlayout1 = new GridLayout();
		gridlayout1.numColumns=2;
		shell.setText("Welcome to iCash!");
		shell.setLayout(gridlayout1);
	}
}
