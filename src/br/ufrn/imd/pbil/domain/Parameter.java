package br.ufrn.imd.pbil.domain;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class Parameter {

	protected ParameterType type;
	private String name;
	private String value;

	Parameter(String name, ParameterType parameterType) throws InvalidParameterTypeException {
		this.name = name;
		if (isCorrectParameterType(parameterType)) {
			this.type = parameterType;
		} else {
			throw new InvalidParameterTypeException();
		}

	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValues(String values) {
		this.value = values;
	}

	@Override
	public String toString() {
		return "Param: [type=" + type + ", name=" + name + ", value=" + value + "]";
	}

	public boolean isCorrectParameterType(ParameterType parameterType) {

		if (parameterType == ParameterType.BOOLEAN || parameterType == ParameterType.CHAR
				|| parameterType == ParameterType.DOUBLE || parameterType == ParameterType.INT
				|| parameterType == ParameterType.STRING) {
			return true;
		} else {
			return false;
		}
	}

}
