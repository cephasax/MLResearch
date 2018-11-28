package br.ufrn.imd.pbil.domain;

import br.ufrn.imd.pbil.enums.BaseClassifierType;

public class BaseClassifier extends Classifier {

	private BaseClassifierType baseClassifierType;

	public BaseClassifier() {
		super();
	}
	
	public BaseClassifierType getBaseClassifierType() {
		return baseClassifierType;
	}

	public void setBaseClassifierType(BaseClassifierType baseClassifierType) {
		this.baseClassifierType = baseClassifierType;
	}

	
	@Override
	public String toString() {
		String param = " :";
		StringBuilder sb = new StringBuilder();
		sb.append(param);
		for (Parameter p : parameters) {
			sb.append(p.toString());
			sb.append("\n");
		}
		this.name + sb.toString();
		
		return "BaseClassifier [baseClassifierType=" + baseClassifierType + "]";
	}

	
	
}
