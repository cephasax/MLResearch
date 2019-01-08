package br.ufrn.imd.pbil.pde;

import java.io.IOException;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.DecisionTableWekaBuilder;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import weka.classifiers.rules.DecisionTable;

public class Teste {

	public static void main(String[] args) throws IOException, InvalidParameterTypeException {
		DecisionTable dt = new DecisionTable();

		Factory f = new Factory();
		for(int i = 0; i < 100; i++) {
			System.out.println();
			Classifier c = f.getBaseclassifierFactory().buildClassifierRandomly("weka.classifiers.rules.DecisionTable");
			PossibilityKeySet pks = new PossibilityKeySet(c);
			dt = DecisionTableWekaBuilder.buildWekaDecisionTable(pks);
		}
	}
	
}
