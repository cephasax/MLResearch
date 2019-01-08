package br.ufrn.imd.pbil.douglas;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.SmoWekaBuilder;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.functions.SMO;

public class Main {
	public static void main(String[] args) throws Exception {
		Factory f = new Factory();
		
		Classifier c = f.buildSolutionFromRandom();
		PossibilityKeySet pks = new PossibilityKeySet(c);
		
		SMO rt =  SmoWekaBuilder.buildWekaSMO(pks);
		rt.buildClassifier(null);
	}
}
