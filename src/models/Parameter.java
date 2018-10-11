package models;

import java.lang.Exception;

public class Parameter {
	public Parameter(ConfigType type, String name ) {
		if(type ==ConfigType.BOLLEAN || type ==ConfigType.STRING ||
		type ==ConfigType.DOUBLE || type ==ConfigType.INT || type ==ConfigType.CHAR) {
			this.type = type;
			this.name = name;
		}else {
			try {
				throw new Exception("No type detected or wrong type");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	public ConfigType type;
	public String name;
	public String values;
	
}
