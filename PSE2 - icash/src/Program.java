import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
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
	
	private Shell shell = new Shell();
	
	public void open(){
		
		Display display = Display.getDefault();
        
        createHome(display);
        shell.open();
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) 
                display.sleep();
	}
	
	public void createHome(Display display){
		shell = new Shell(display);
		GridLayout layout = new GridLayout(1, false);

	    // set the layout to the shell
	    shell.setLayout(layout);
	    Label labelWelcomeAdmin = new Label(shell, SWT.NONE);
	    labelWelcomeAdmin.setText("Welcome to the Administration Frontend!");
	    //labelWelcomeAdmin.setHeight(16);
	    
	    // set the layout for the Group
	    layout = new GridLayout(2,false);
	    Group groupHomeAdmin = new Group(shell, SWT.NONE);
	    groupHomeAdmin.setLayout(layout);
	    groupHomeAdmin.setText("Build up your Connection");

	    Label labelHomeConnectAdmin = new Label(groupHomeAdmin, SWT.NONE);
	    labelHomeConnectAdmin.setText("Connect To:");
	    Text textHomeConnectAdmin = new Text(groupHomeAdmin, SWT.NONE);
	    textHomeConnectAdmin.setText("Put in the Server Connection!");		    

	    Label labelHomeUserAdmin  = new Label(groupHomeAdmin, SWT.NONE);
	    labelHomeUserAdmin.setText("Username:");
	    Text textHomeUserAdmin = new Text(groupHomeAdmin, SWT.NONE);
	    textHomeUserAdmin.setText("Write down your Username!");
	    
	    Label labelHomePasswordAdmin  = new Label(groupHomeAdmin, SWT.NONE);
	    labelHomePasswordAdmin.setText("Password:");
	    Text textHomePasswordAdmin = new Text(groupHomeAdmin, SWT.NONE);
	    textHomePasswordAdmin.setText("Write down your Password!");
	}
}
