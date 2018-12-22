package br.ufrn.imd.pbil.domain.prototypes.bc;


import java.util.Arrays;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class KstarPrototype extends ClassifierPrototype{
	public KstarPrototype() throws InvalidParameterTypeException {
		// TODO Auto-generated constructor stub
		ParameterPrototype b = new ParameterPrototype("B",ParameterType.INT);
		b = buildIntParamete(1, 100,1, b);
		parameters.put(b.getName(), b);
		
		ParameterPrototype e =new ParameterPrototype("E", ParameterType.BOOLEAN);
		e.setPossibilities(Arrays.asList(true,false));
		parameters.put(e.getName(), e);
		
		ParameterPrototype x = new ParameterPrototype("X", ParameterType.STRING);
		x.setPossibilities(Arrays.asList("a","d","m","n"));
		parameters.put(x.getName(), x);
	}
}
