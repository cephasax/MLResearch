package br.ufrn.imd.pbil.domain.bc.prototypes;

import java.util.Arrays;
import java.util.List;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BayesNetPrototype extends ClassifierPrototype{
	public BayesNetPrototype() throws InvalidParameterTypeException {
		ParameterPrototype q = new ParameterPrototype("Q", ParameterType.STRING);
		List<String> possibilities = Arrays.asList(
				"weka.classifiers.bayes.net.search.local.K2 -P1 -S BAYES",
				"weka.classifiers.bayes.net.search.local.HillClimber -P 1 -S BAYES", 
				"weka.classifiers.bayes.net.search.local.LAGDHillClimber -L 2 -G 5 -P 1 -S BAYES",
				"weka.classifiers.bayes.net.search.local.SimulatedAnnealing -A 10.0 U 10000 -D 0.999 -R 1 -S BAYES", 
				"weka.classifiers.bayes.net.search.local.TabuSearch -L 5 -U 10 -P 1 -S BAYES", 
				"weka.classifiers.bayes.net.search.local.TAN -S BAYES");
		q.setPossibilities(possibilities);
		parameters.put(q.getName(), q);
		
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		d.setPossibilities(Arrays.asList(true,false));
		parameters.put(d.getName(), d);
	}
}
