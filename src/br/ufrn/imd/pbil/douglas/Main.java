package br.ufrn.imd.pbil.douglas;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.Factory;
import br.ufrn.imd.pbil.domain.comm.wekabuilders.VoteWekaBuilder;
import br.ufrn.imd.pbil.pde.PossibilityKeySet;
import weka.classifiers.meta.Vote;

public class Main {
	public static void main(String[] args) throws Exception {
		Factory f = new Factory();
		
		Classifier c = f.buildSolutionFromRandom();
		PossibilityKeySet pks = new PossibilityKeySet(c);
		
		Vote rf =  VoteWekaBuilder.buildForWeka(pks);
		
		
		rf.buildClassifier(null);
	}
}
