package br.ufrn.imd.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;
import java.util.List;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.ParameterPrototype;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class BayesNetPrototype extends ClassifierPrototype{
	public BayesNetPrototype() throws InvalidParameterTypeException {
		
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		d.setPossibilities(Arrays.asList(true,false));
		parameters.put(d.getName(), d);
		
		ParameterPrototype q = new ParameterPrototype("Q", ParameterType.STRING);
		List<String> possibilities = Arrays.asList(
				"weka.classifiers.bayes.net.search.local.K2",
				"weka.classifiers.bayes.net.search.local.HillClimber", 
				"weka.classifiers.bayes.net.search.local.LAGDHillClimber",
				"weka.classifiers.bayes.net.search.local.SimulatedAnnealing", 
				"weka.classifiers.bayes.net.search.local.TabuSearch", 
				"weka.classifiers.bayes.net.search.local.TAN");
		q.setPossibilities(possibilities);
		parameters.put(q.getName(), q);
		
	}
}
