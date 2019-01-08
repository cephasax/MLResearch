package br.ufrn.imd.pbil.domain.comm.wekabuilders;

import br.ufrn.imd.pbil.douglas.ClassifierBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Stacking;

public class StackingWekaBuilder {
	
	public static Stacking buildForWeka(PossibilityKeySet pks) throws NumberFormatException, Exception {
		Stacking st = new Stacking();
		Classifier [] classifiers = new Classifier[pks.getBranchClassifiers().size()];
		
		for(int i = 0; i<classifiers.length;i++) {
			classifiers[i] = ClassifierBuilder.buildClassifier(pks.getBranchClassifiers().get(i));
		}
		
		st.setClassifiers(classifiers);
		
		st.setNumFolds(Integer.parseInt(pks.getKeyValuesPairs().get("X")));
		st.setSeed(Integer.parseInt(pks.getKeyValuesPairs().get("S")));
		
		return st;
	}
	
}
