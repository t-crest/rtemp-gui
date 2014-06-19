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

package Model.IPCores;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import Model.PlatformObject;

@Root
public class IO extends PlatformObject {

	@Attribute(name="DevTypeRef")
	String devTypeRef;
	@Attribute
	String offset;
	@Attribute(required=false)
	String intrs;
	
	public String getDevTypeRef() {
		return devTypeRef;
	}

	public void setDevTypeRef(String devTypeRef) {
		this.devTypeRef = devTypeRef;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}
}
