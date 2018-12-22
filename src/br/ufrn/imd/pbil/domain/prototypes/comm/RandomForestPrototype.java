package br.ufrn.imd.pbil.domain.prototypes.comm;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class RandomForestPrototype extends ClassifierPrototype{
	public RandomForestPrototype() throws InvalidParameterTypeException {
		ParameterPrototype i = new ParameterPrototype("I", ParameterType.INT);
		i = buildIntParamete(2, 256, 1, i);
		parameters.put(i.getName(), i);
	
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.INT);
		k = buildIntParamete(1, 32, 1, k);
		parameters.put(k.getName(), k);
		
		ParameterPrototype w = new ParameterPrototype("W", ParameterType.INT);
		w = buildIntParamete(1, 20, 1, w);
		parameters.put(w.getName(), w);
		
	}
}
