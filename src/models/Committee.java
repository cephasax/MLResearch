package models;

import java.util.List;

public class Committee extends Classifier{
	public CommitteType type;
	public List<Classifier> classifiers;
	public int numberOfBaseClassifiers() {
		return this.classifiers.size();
	}
	
}
