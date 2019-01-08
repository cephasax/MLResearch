package br.ufrn.imd.pbil.pde;

import java.io.IOException;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.RandomCommitteeWekaBuilder;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;
import weka.classifiers.meta.RandomCommittee;

public class Teste {

	public static void main(String[] args) throws IOException, InvalidParameterTypeException {
		RandomCommittee rc = new RandomCommittee();

		Factory f = new Factory();

		System.out.println();
		Classifier c = f.getCommitteeFactory().buildClassifierRandomly("Bagging");
		PossibilityKeySet pks = new PossibilityKeySet(c);
		rc = RandomCommitteeWekaBuilder.buildForWeka(pks);
	}
	
}
