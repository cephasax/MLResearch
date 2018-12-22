package br.ufrn.imd.pbil.pde;

import java.util.Map;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Parameter;

public class PossibilityKeysSet {

	private String key;
	private Map<String, String> keyValuesPairs;

	public PossibilityKeysSet(Classifier classifier) {
		this.key = classifier.getName();
		for(Parameter param: classifier.getParameters()) {
			keyValuesPairs.put(param.getName(), param.getValue());
		}
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Map<String, String> getKeyValuesPairs() {
		return keyValuesPairs;
	}

	public void setKeyValuesPairs(Map<String, String> keyValuesPairs) {
		this.keyValuesPairs = keyValuesPairs;
	}

}
