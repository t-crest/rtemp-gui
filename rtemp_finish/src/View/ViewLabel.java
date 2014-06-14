package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class ViewLabel {
	private View view;
	
	//Used to create labels
	public ViewLabel(View view, String text, int alignment) {
		this.view = view;
		Label details = new Label(view.getShell(),SWT.NONE);
		details.setText(text);
		details.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));
		details.setAlignment(alignment);
	}
}
