package Dialogs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DoubleInputDialog extends Dialog {
	private Shell shell;
	private Text textField1, textField2;
	
	private String text = "";
	private String text1 = "";
	private String text2 = "";
	private String message = "";
	
	private Object[] result;

	public DoubleInputDialog(Shell shell) {
		super(shell);
		this.shell = shell;
	}

	public DoubleInputDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setOptions(String text1, String text2) {
		this.text1 = text1;
		this.text2 = text2;
	}

	public Object[] open () {
		Shell parent = getParent();
		final Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		// Your code goes here (widget creation, set result, etc).
		shell.setSize(400, 200);
		shell.setLayout(null);
		shell.setVisible(false);
		shell.setText(this.text);

		Label labelMessage = new Label(shell, SWT.NONE);
		labelMessage.setBounds(40, 20, 300, 20);
		labelMessage.setText(this.message);

		Label labelText1 = new Label(shell, SWT.NONE);
		labelText1.setBounds(140, 50, 60, 15);
		labelText1.setText(this.text1);

		textField2 = new Text(shell, SWT.BORDER);
		textField2.setBounds(210, 50, 60, 20);

		Label labelText2 = new Label(shell, SWT.NONE);
		labelText2.setBounds(140, 80, 60, 15);
		labelText2.setText(this.text2);

		textField1 = new Text(shell, SWT.BORDER);
		textField1.setBounds(210, 80, 60, 20);

		Button buttonOk = new Button(shell, SWT.NONE);
		buttonOk.setBounds(120, 130, 80, 30);
		buttonOk.setText("OK");
		shell.setDefaultButton(buttonOk);
		buttonOk.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = generateResult(SWT.OK);
				shell.close();
			}

		});

		Button buttonCancel = new Button(shell, SWT.NONE);
		buttonCancel.setBounds(200, 130, 80, 30);
		buttonCancel.setText("Cancel");
		buttonCancel.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = generateResult(SWT.CANCEL);
				shell.close();
			}

		});

		//Center the in the middel of the screen
		Rectangle bounds = shell.getDisplay().getPrimaryMonitor().getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);

		shell.open();
		Display display = parent.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}

		return result == null ? new Object[]{SWT.CANCEL, "", ""} : result;
	}
	
	private Object[] generateResult(int type) {
		return new Object[] {type,textField2.getText(),textField1.getText()};
	}
}
