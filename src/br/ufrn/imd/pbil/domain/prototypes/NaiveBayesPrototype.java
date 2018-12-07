package br.ufrn.imd.pbil.domain.prototypes;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class NaiveBayesPrototype extends ClassifierPrototype{
	public NaiveBayesPrototype() throws InvalidParameterTypeException {
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.BOOLEAN);
		List<Boolean> possibilities = new ArrayList<Boolean>();
		possibilities.add(true);
		possibilities.add(false);
		d.setPossibilities(possibilities);
		k.setPossibilities(possibilities);
		parameters.add(d);
		parameters.add(k);
	}
}
