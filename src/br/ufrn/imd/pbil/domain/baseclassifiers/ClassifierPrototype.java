package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.HashMap;

public abstract class ClassifierPrototype {
	
	protected HashMap<String, ParameterPrototype> parameters;

	public HashMap<String, ParameterPrototype> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, ParameterPrototype> parameters) {
		this.parameters = parameters;
	}

}
