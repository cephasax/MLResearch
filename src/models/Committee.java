package models;

import java.util.List;

public class Committee extends Classifier{
	private CommitteType type;
	private List<Classifier> classifiers;
	public int numberOfBaseClassifiers() {
		return this.classifiers.size();
	}
	public CommitteType getType() {
		return type;
	}
	public void setType(CommitteType type) {
		this.type = type;
	}
	public List<Classifier> getClassifiers() {
		return classifiers;
	}
	public void setClassifiers(List<Classifier> classifiers) {
		this.classifiers = classifiers;
	}
	
}
