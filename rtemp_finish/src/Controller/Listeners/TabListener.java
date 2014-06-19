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

package Controller.Listeners;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import Model.Model;
import View.View;

public class TabListener implements SelectionListener {
	private View view;
	private Model model;
	
	public TabListener(Model model, View view) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if(!view.viewCpu.adding)
			view.update();
	}

}
