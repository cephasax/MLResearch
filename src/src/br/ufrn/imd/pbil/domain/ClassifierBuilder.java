package br.ufrn.imd.pbil.domain;

public interface ClassifierBuilder {

	public Classifier defautBuild();

	public Classifier randomBuild();

	public Classifier wheitedDrawBuild();
}