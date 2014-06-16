package Dialogs;




import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * 
 * @author Peter Gelsbo
 *
 */
public class TextInputDialog extends Dialog {
	private List<Object[]> inputs = new ArrayList<Object[]>();
	private List<Button> buttons = new ArrayList<Button>();
	private String message = "";
	private Object[] result;
	private Shell shell;
	private int width = 0;
	
	/**
	 * Constructs a new instance of this class given only its parent.
	 * @param parent
	 * @see Shell
	 */
	public TextInputDialog(Shell parent) {
		super(parent);
	}
	
	/**
	 * Constructs a new instance of this class given its parent and a style value describing its behavior and appearance.
	 * @param parent
	 * @param style
	 * @see Shell
	 */
	public TextInputDialog(Shell parent, int style) {
		super(parent, style);
	}
	
	/**
	 *  Sets the receiver's text, which is the string that the window manager will typically display as the receiver's title, to the argument, which must not be null.
	 */
	public void setText(String text) {
		super.setText(text);
	}
	
	/**
	 * Sets the dialog's message, which is a description of the purpose for which it was opened.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 
	 * @param options
	 */
	public void setOptions(int style, String... options) {
		for(String option : options) {
			this.inputs.add(new Object[] {option, new Text(getParent(),SWT.BORDER | style)});
		}
	}
	
	/**
	 * 
	 * @return Object[] array containing ...
	 */
	public Object[] open () {
		
		
		
		// Creating shell
		Shell parent = getParent();
		this.shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText(this.getText());
		
		GridLayout gl = new GridLayout();
		gl.marginHeight = 5;
		gl.marginWidth = 5;
		gl.numColumns = 1;
		shell.setLayout(gl);
		
		Color green = shell.getDisplay().getSystemColor(SWT.COLOR_GREEN);
		
		// Show message
		Label labelMessage = new Label(shell, SWT.WRAP);
		labelMessage.setText(this.message);
		GridData dMessage = new GridData(SWT.FILL, SWT.FILL , true, false);
		
			dMessage.widthHint = width;
		labelMessage.setLayoutData(dMessage);
		
		
		// Create and show input labels and text fields
		Composite cInputs = new Composite(shell,SWT.NONE);
		GridData dInputs = new GridData(SWT.FILL,SWT.FILL,false,false);
		cInputs.setLayoutData(dInputs);
		
		GridLayout lInputs = new GridLayout();
		lInputs.numColumns = 2;
		lInputs.marginWidth = 0;
		cInputs.setLayout(lInputs);
		
		
		for(Object[] input : inputs) {
			Composite cLabel = new Composite(cInputs,SWT.NONE);
			GridData dLabel = new GridData(SWT.FILL,0,false,false);
			cLabel.setLayoutData(dLabel);
			
			RowLayout lLabel = new RowLayout();
			lLabel.marginRight = 20;
			lLabel.marginLeft = 0;
			cLabel.setLayout(lLabel);
			Label l = new Label(cLabel,SWT.NONE);
			l.setText((String) input[0]);
			
			Text t = (Text) input[1];
			t.setParent(cInputs);
			GridData dText = new GridData(SWT.FILL,SWT.FILL,true,false);
			t.setLayoutData(dText);
		}
		
		
		// Create and show buttons
		Composite cButtons = new Composite(shell,SWT.NONE);
		GridData dButtons = new GridData(0,0,true,false);
		dButtons.horizontalAlignment = SWT.RIGHT;
		cButtons.setLayoutData(dButtons);
		RowLayout lButtons = new RowLayout(SWT.HORIZONTAL);
		cButtons.setLayout(lButtons);
		
		if((getStyle() & SWT.OK) != 0) {
			buttons.add(createButton(cButtons,SWT.OK,"OK"));
		}
		if((getStyle() & SWT.CANCEL) != 0) {
			buttons.add(createButton(cButtons,SWT.CANCEL,"Cancel"));
		}
		
		// Set default button
		shell.setDefaultButton(buttons.get(0));

		shell.pack();
		shell.open();

		//Center the in the middle of the screen
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
		shell.dispose();
		return result == null ? new Object[]{SWT.CANCEL, "", ""} : result;
	}
	
	private Button createButton(Composite parent, final int style, String text) {
		Button b = new Button(parent, SWT.PUSH);
		b.setText(text);
		
		// Set widt of buttons
		RowData rd = new RowData();
		rd.width = 100;
		b.setLayoutData(rd);
		
		
		
		b.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = generateResult(style);
				shell.close();
			}

		});
		return b;
	}

	private Object[] generateResult(int type) {
		String[] output = new String[inputs.size()];
		for(int i = 0; i < output.length; i++) {
			output[i] = ((Text) (inputs.get(i))[1]).getText();
		}
		return new Object[] {type,output};
	}

	public void setWidth(int i) {
		this.width = i;
	}
}
