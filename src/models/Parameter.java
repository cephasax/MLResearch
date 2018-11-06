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
	private ConfigType type;
	private String name;
	private String values;
	public ConfigType getType() {
		return type;
	}
	public void setType(ConfigType type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValues() {
		return values;
	}
	public void setValues(String values) {
		this.values = values;
	}
	
}
