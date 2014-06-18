package Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Model.Aegean.Link;

public abstract class PlatformObject implements Cloneable {

	public PlatformObject() {}
	
	public List<TreeObject> getAttributeList() {
		
		List<TreeObject> l = new ArrayList<TreeObject>();
		Object obj = this;
		
		try {
			List<Field> fields = listAllFields(obj);
			
			for(Field f : fields) {
				
				f.setAccessible(true);
				Object attributeValue = f.get(obj);
				
				// IF STRING
				if(f.getType().equals(String.class)) {
					String attributeName = f.getName();
					l.add(new TreeObject((PlatformObject) obj, attributeName, (String) attributeValue));

				} 
				// IF LIST
				else if (f.getType().equals(List.class)) {
					for (PlatformObject p : (List<PlatformObject>) attributeValue) {
						List<TreeObject> childList = p.getAttributeList();
						l.addAll(childList);
					}
				}
				// IF OBJECT
				else if(f.getType().getSuperclass() != null && attributeValue != null) {
					if(f.getType().getSuperclass().equals(PlatformObject.class)) {
						List<TreeObject> childList = ((PlatformObject) attributeValue).getAttributeList();
						l.addAll(childList);
					}
				}
				// OTHERWISE
				else {
					System.out.println("Error! Object class " + f.getType().getSimpleName() + " not recognized or was null.");
				}
			}
			
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		return l;
	}
	
	private static List<Field> listAllFields(Object obj) {
		List<Field> fieldList = new ArrayList<Field>();
		Class tmpClass = obj.getClass();
		while(tmpClass != null) {
			fieldList.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
			tmpClass = tmpClass.getSuperclass();
		}
		return fieldList;
	}
	
	public void changeField(String attributeName, String newSetting) {

		try {
			Field f = this.getClass().getDeclaredField(attributeName);
			f.setAccessible(true);
			f.set(this, newSetting);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected PlatformObject clone() throws CloneNotSupportedException {
		PlatformObject cloned = (PlatformObject)super.clone();
		
		List<Field> fieldList = listAllFields(cloned);
		
		// Recursive cloning of sub-objects
		for(Field f : fieldList) {
			f.setAccessible(true);

			try {

				Object oldValue = f.get(cloned);
				Object newValue = null;
				
				// If null
				if(oldValue == null) {
					newValue = null;
				}
				// If string
				else if(f.getType().equals(String.class)) {
					newValue = oldValue;
				}
				// If List
				else if(f.getType().equals(List.class)) {
					List<PlatformObject> newList = new ArrayList<PlatformObject>();
					
					for (PlatformObject p : (List<PlatformObject>) oldValue) {
						newList.add(p.clone());
						newValue = newList;
					}
				}
				// If field is a PlatformObject, the object should be cloned as well.
				else if(f.getType().getSuperclass().equals(PlatformObject.class)) {
					newValue = ((PlatformObject) oldValue).clone();
				}
				
				// Set cloned value
				f.set(cloned, newValue);
				
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	    
	    return cloned;
	}
	

}