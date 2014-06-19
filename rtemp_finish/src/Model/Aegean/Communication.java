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

package Model.Aegean;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Communication
{
	@Attribute(required=false)
	public String comType;
	@Attribute(required=false)
	public String phits;
	@ElementList (inline=true, required=false)
	public List<Channel> channel1 = new ArrayList<Channel>();
	
	public String getComType() {
		return comType;
	}
	
	public void setComType(String comType) {
		this.comType = comType;
	}
	
	public String getPhits() {
		return phits;
	}
	
	public void setPhits(String phits) {
		this.phits = phits;
	}
	
	public List<Channel> getChannel1() {
		return channel1;
	}
	
	public void setChannel1(ArrayList<Channel> channel1) {
		this.channel1 = channel1;
	}
}
