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

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import Model.PlatformObject;


@Root
public class Patmos  extends PlatformObject {
	
	@Element
	String description = ""; 
	@Element
	Frequency frequency;
	@Element
	Pipeline pipeline;
	@Element
	Bus bus;
	@Element
	Bootrom bootrom;
	@Element(name="MCache")
	MCache mCache;
	@Element(name="DCache")
	DCache dCache;
	@Element(name="SCache")
	SCache sCache;
	@Element(name="ISPM")
	ISPM ispm;
	@Element(name="DSPM")
	DSPM dspm;
	@Element(name="BootSPM")
	BootSPM bootSPM;
	@Element(name="ExtMem")
	ExtMem extMem;
	@ElementList(name="IOs")
	ArrayList<IO> ios = new ArrayList<IO>();
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Frequency getFrequency() {
		return frequency;
	}
	
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	
	public Pipeline getPipeline() {
		return pipeline;
	}
	
	public void setPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
	}
	
	public Bus getBus() {
		return bus;
	}
	
	public void setBus(Bus bus) {
		this.bus = bus;
	}
	
	public Bootrom getBootrom() {
		return bootrom;
	}
	
	public void setBootrom(Bootrom bootrom) {
		this.bootrom = bootrom;
	}
	
	public MCache getmCache() {
		return mCache;
	}
	
	public void setmCache(MCache mCache) {
		this.mCache = mCache;
	}
	
	public DCache getdCache() {
		return dCache;
	}
	
	public void setdCache(DCache dCache) {
		this.dCache = dCache;
	}
	
	public SCache getsCache() {
		return sCache;
	}
	
	public void setsCache(SCache sCache) {
		this.sCache = sCache;
	}
	
	public ISPM getIspm() {
		return ispm;
	}
	
	public void setIspm(ISPM ispm) {
		this.ispm = ispm;
	}
	
	public DSPM getDspm() {
		return dspm;
	}
	
	public void setDspm(DSPM dspm) {
		this.dspm = dspm;
	}
	
	public BootSPM getBootSPM() {
		return bootSPM;
	}
	
	public void setBootSPM(BootSPM bootSPM) {
		this.bootSPM = bootSPM;
	}
	
	public ExtMem getExtMem() {
		return extMem;
	}
	
	public void setExtMem(ExtMem extMem) {
		this.extMem = extMem;
	}
	
	public ArrayList<IO> getIos() {
		return ios;
	}
	
	public void setIos(ArrayList<IO> ios) {
		this.ios = ios;
	}
}
