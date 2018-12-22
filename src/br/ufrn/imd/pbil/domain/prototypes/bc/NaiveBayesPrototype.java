package br.ufrn.imd.pbil.domain.prototypes.bc;

import java.util.Arrays;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

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
