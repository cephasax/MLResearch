package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.sql.ParameterMetaData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class BayesNetPrototype extends ClassifierPrototype{
	public BayesNetPrototype() throws InvalidParameterTypeException {
		ParameterPrototype q = new ParameterPrototype("Q", ParameterType.STRING);
		List<String> possibilities = Arrays.asList("K2", "HillClimber", "LAGDHillClimber",
				"SimulatedAnnealing", "TabuSearch", "TAN");
		q.setPossibilities(possibilities);
		parameters.put(q.getName(), q);
		
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		d.setPossibilities(Arrays.asList(true,false));
		parameters.put(d.getName(), d);
	}
}
