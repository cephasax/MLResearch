package br.ufrn.imd.pbil.domain;

import br.ufrn.imd.pbil.enums.ClassifierType;

public class ClassifierFactory {

	public Classifier getClassifier(ClassifierType classifierType) {
		switch(classifierType) {
			case BASE_CLASSIFIER:
				return new BaseClassifier();
			case COMMITTEE:
				return new Committee();
			default:
				return new BaseClassifier();
		}
	}
}
