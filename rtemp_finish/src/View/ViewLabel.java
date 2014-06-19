/*
    Copyright 2014 Peter Gelsbo and Andreas Nordmand Andersen

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	    http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */

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
