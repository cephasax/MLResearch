package br.ufrn.imd.pbil.pde;

import java.io.IOException;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.DecisionTableWekaBuilder;
import br.ufrn.imd.pbil.domain.bc.wekabuilders.IbkWekaBuilder;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import weka.classifiers.lazy.IBk;

public class Teste {

	public static void main(String[] args) throws IOException, InvalidParameterTypeException {
		IBk ibk = new IBk();

		Factory f = new Factory();
		for(int i = 0; i < 100; i++) {
			System.out.println();
			Classifier c = f.getBaseclassifierFactory().buildClassifierRandomly("weka.classifiers.lazy.IBk");
			PossibilityKeySet pks = new PossibilityKeySet(c);
			ibk = IbkWekaBuilder.buildWekaDecisionTable(pks);
		}
	}
	
}
