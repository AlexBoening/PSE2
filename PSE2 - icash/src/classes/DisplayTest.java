package classes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DisplayTest {
	public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
//Test        
        Label labelHomeWelcomeAdmin = new Label(shell, SWT.BORDER);
        labelHomeWelcomeAdmin.setText("Welcome to the Administration Frontend");
        labelHomeWelcomeAdmin.setSize(600,30);
        labelHomeWelcomeAdmin.setBackground(new Color(display,200,100,50));
        labelHomeWelcomeAdmin.setLocation(40, 40);
        
        
        Label labelHomeConnectAdmin = new Label(shell, SWT.BORDER);
        labelHomeConnectAdmin.setText("Connect to:");
        labelHomeConnectAdmin.setSize(70,30);
        labelHomeConnectAdmin.setBackground(new Color(display,200,100,50));
        labelHomeConnectAdmin.setLocation(40, 200);
        
        Text textHomeConnectAdmin = new Text(shell, SWT.BORDER);
        textHomeConnectAdmin.setTextLimit(30);
        textHomeConnectAdmin.setText("Type something here!");
        textHomeConnectAdmin.setBounds(130,200,500,30); //Breite,Höhe,Länge,Feldhöhe
        
        Label labelHomeUserAdmin = new Label(shell, SWT.BORDER);
        labelHomeUserAdmin.setText("Connect to:");
        labelHomeUserAdmin.setSize(70,30);
        labelHomeUserAdmin.setBackground(new Color(display,200,100,50));
        labelHomeUserAdmin.setLocation(40, 300);
        
        Text textHomeUserAdmin = new Text(shell, SWT.BORDER);
        textHomeUserAdmin.setTextLimit(30);
        textHomeUserAdmin.setText("Type something here!");
        textHomeUserAdmin.setBounds(130,300,500,30);
        
        Label labelHomePasswordAdmin = new Label(shell, SWT.BORDER);
        labelHomePasswordAdmin.setText("Connect to:");
        labelHomePasswordAdmin.setSize(70,30);
        labelHomePasswordAdmin.setBackground(new Color(display,200,100,50));
        labelHomePasswordAdmin.setLocation(40, 400);
        
        Text textHomePasswordAdmin = new Text(shell, SWT.BORDER);
        textHomePasswordAdmin.setTextLimit(30);
        textHomePasswordAdmin.setText("Type something here!");
        textHomePasswordAdmin.setBounds(130,400,500,30);
        
                
        shell.setSize(700, 500);
        shell.open();
        
        while (!shell.isDisposed())
            if (!display.readAndDispatch()) 
                display.sleep();
    }

}
