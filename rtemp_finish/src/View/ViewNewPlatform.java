package View;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import Model.Model;

public class ViewNewPlatform {
	private View view;
	private Model model;
	private Shell shell;
	public Button buttonOk, buttonCancel;
	public Text textHeight, textWidth;
	
	private List<Button> buttons = new ArrayList<Button>();
	
	public ViewNewPlatform(Model model, View view) {
		this.view = view;
		this.model = model;
		
		shell = new Shell();
		shell.setSize(400, 200);
		shell.setText("New Platform");
		shell.setLayout(null);
		shell.setVisible(false);
		
		Label lblpleaseInsertThe = new Label(shell, SWT.NONE);
		lblpleaseInsertThe.setBounds(40, 20, 300, 20);
		lblpleaseInsertThe.setText("\"Please insert the height and width of the Platform\"");
		
		Label labelWidth = new Label(shell, SWT.NONE);
		labelWidth.setBounds(140, 50, 60, 15);
		labelWidth.setText("Width:");
		
		textWidth = new Text(shell, SWT.BORDER);
		textWidth.setBounds(210, 50, 60, 20);
		
		Label labelHeight = new Label(shell, SWT.NONE);
		labelHeight.setBounds(140, 80, 60, 15);
		labelHeight.setText("Height:");
		
		textHeight = new Text(shell, SWT.BORDER);
		textHeight.setBounds(210, 80, 60, 20);
		
		buttonOk = new Button(shell, SWT.NONE);
		buttonOk.setBounds(120, 130, 80, 30);
		buttonOk.setText("OK");
		buttons.add(buttonOk);
		
		buttonCancel = new Button(shell, SWT.NONE);
		buttonCancel.setBounds(200, 130, 80, 30);
		buttonCancel.setText("Cancel");
		buttons.add(buttonCancel);
		
		shell.setDefaultButton(buttonOk);
		shell.open();
		
		//Center the in the middel of the screen
	    Rectangle bounds = shell.getDisplay().getPrimaryMonitor().getBounds();
	    Rectangle rect = shell.getBounds();
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    shell.setLocation(x, y);
	}
	
	public void setNewPlatformVisible(boolean visible) {
		shell.setVisible(visible);
		if(visible) {
			
			textHeight.setText("");
			textWidth.setText("");
			
		}
	}
	
	public List<Button> getButtons() {
		return buttons;
	}
	
}
