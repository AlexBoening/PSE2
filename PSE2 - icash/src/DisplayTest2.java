import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;

public class DisplayTest2 {
	public static void main(String[] args) {
		try {
			DisplayTest2 window = new DisplayTest2();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }
	private Shell shell;

	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected void createContents() {
		shell = new Shell();
		shell.setSize(750, 300);
		shell.setText("Bank Client 0.1");

		shell.setLayout(new FillLayout());

		Composite mainComposite = new Composite(shell, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(5, 5).applyTo(mainComposite);

		Label label = new Label(mainComposite, SWT.NONE);
		label.setText("Hello World");

		Button button = new Button(mainComposite, SWT.PUSH);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageDialog.openWarning(shell, "Bye", "Bye Bye Cruel World!");
			}
		});
	}
}
