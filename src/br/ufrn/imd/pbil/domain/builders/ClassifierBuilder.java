package br.ufrn.imd.pbil.domain.builders;

import br.ufrn.imd.pbil.domain.Classifier;

public interface ClassifierBuilder {
	
	public Classifier defautBuild();
	
	public Classifier randomBuild();
	
	public Classifier wheitedDrawBuild();
}
