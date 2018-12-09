package br.ufrn.imd.pbil.domain.committees;

import java.util.List;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.enums.CommitteeType;

public class Committee extends Classifier {

	private CommitteeType committeeType;
	private List<Classifier> classifiers;

	public int numberOfBaseClassifiers() {
		return this.classifiers.size();
	}

	public CommitteeType getCommitteeType() {
		return committeeType;
	}

	public void setCommitteeType(CommitteeType committeeType) {
		this.committeeType = committeeType;
	}

	public List<Classifier> getClassifiers() {
		return classifiers;
	}

	public void setClassifiers(List<Classifier> classifiers) {
		this.classifiers = classifiers;
	}

}
