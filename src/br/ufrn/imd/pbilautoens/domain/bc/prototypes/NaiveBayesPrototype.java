package br.ufrn.imd.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.ParameterPrototype;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class NaiveBayesPrototype extends ClassifierPrototype {
	public NaiveBayesPrototype() throws InvalidParameterTypeException {
		ParameterPrototype d = new ParameterPrototype("D", ParameterType.BOOLEAN);
		d.setPossibilities(Arrays.asList(true,false));
		parameters.put(d.getName(), d);
		
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.BOOLEAN);
		k.setPossibilities(Arrays.asList(true,false));
		parameters.put(k.getName(), k);
		
	}
}
