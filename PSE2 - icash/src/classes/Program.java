package classes;
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
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.jface.layout.GridLayoutFactory;

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
        shell.setText("Welcome to iCash!");
        shell.open();
        
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) 
                display.sleep();
	}
	
	public void createHome(Display display){
		
		shell = new Shell(display);
		shell.setSize(750, 300);
		
		//Composite mainComp = new Composite(shell,SWT.NONE);
		GridLayout gridlayout = new GridLayout();
		gridlayout.numColumns = 3;
		shell.setLayout(gridlayout);
		
		//Placeholder
		GridData PlaceholderData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		PlaceholderData.horizontalSpan=3;
		PlaceholderData.horizontalAlignment = GridData.CENTER;
		
		Label spaceholder = new Label(shell, SWT.NONE);
		spaceholder.setLayoutData(PlaceholderData);
		//Placeholder
		
		
		Label welcomeLabel = new Label(shell, SWT.NONE);
		GridData welcomeData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		welcomeData.horizontalSpan=3;
		welcomeData.horizontalAlignment = GridData.CENTER;
		welcomeLabel.setLayoutData(welcomeData);
		welcomeLabel.setText("Welcome to the Adminstration Frontend!");
		welcomeLabel.setFont(new Font(null, "Tahoma",20, SWT.BOLD));
		
		//Placeholder
		Label spaceholder2 = new Label(shell, SWT.NONE);
		spaceholder2.setLayoutData(PlaceholderData);
		//Placeholder
		
		
		new Label(shell, SWT.NONE).setText("Connect to:");
		Text connectText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData connectData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		connectData.horizontalSpan = 2;
		connectText.setLayoutData(connectData);
		
		new Label(shell, SWT.NONE).setText("Username:");
		Text userText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData userData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		userData.horizontalSpan = 2;
		userText.setLayoutData(userData);
		
		new Label(shell, SWT.NONE).setText("Passwort:");
		Text pwText = new Text(shell, SWT.SINGLE | SWT.BORDER);
		GridData pwData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		pwData.horizontalSpan = 2;
		pwText.setLayoutData(pwData);
		
		Button LogInBTN = new Button(shell, SWT.BORDER);
		GridData loginData = new GridData(GridData.CENTER, GridData.CENTER,true, false);
		loginData.horizontalAlignment = GridData.CENTER;
		loginData.horizontalSpan=3;
		LogInBTN.setText("Log in");
		LogInBTN.setLayoutData(loginData);
		
		//shell.pack();
		
		//shell = new Shell(display);
		//GridLayout layout = new GridLayout(1, false);

	    // set the layout to the shell
	    //shell.setLayout(layout);
	    //Label labelWelcomeAdmin = new Label(shell, SWT.NONE);
	    //labelWelcomeAdmin.setText("Welcome to the Administration Frontend!");
	    //Text textWelcomeAdmin = new Text(shell, SWT.NONE);
		//textWelcomeAdmin.setEditable(false);
		//textWelcomeAdmin.setText("Welcome to the Administration Frontend");
	    //labelWelcomeAdmin.setHeight(16);
	    
	    // set the layout for the Group
		//layout = new GridLayout(2,false);
		//Group groupHomeAdmin = new Group(shell, SWT.NONE);
		//groupHomeAdmin.setLayout(layout);
		//groupHomeAdmin.setText("Build up your Connection");

		//Label labelHomeConnectAdmin = new Label(groupHomeAdmin, SWT.NONE);
		//labelHomeConnectAdmin.setText("Connect To:");
		//Text textHomeConnectAdmin = new Text(groupHomeAdmin, SWT.NONE);
		//textHomeConnectAdmin.setText("Put in the Server Connection!");		    

		//Label labelHomeUserAdmin  = new Label(groupHomeAdmin, SWT.NONE);
		//labelHomeUserAdmin.setText("Username:");
		//Text textHomeUserAdmin = new Text(groupHomeAdmin, SWT.NONE);
		//textHomeUserAdmin.setText("Write down your Username!");
	    
		//Label labelHomePasswordAdmin  = new Label(groupHomeAdmin, SWT.NONE);
		//labelHomePasswordAdmin.setText("Password:");
		//Text textHomePasswordAdmin = new Text(groupHomeAdmin, SWT.NONE);
		//textHomePasswordAdmin.setText("Write down your Password!");
	}
}
