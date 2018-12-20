package br.ufrn.imd.pbil.domain.committees;

import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class StackingPrototype extends ClassifierPrototype{
	public StackingPrototype() throws InvalidParameterTypeException {
		ParameterPrototype x = new ParameterPrototype("X", ParameterType.INT);
		x = buildIntParamete(1, 10, 1, x);
		parameters.put(x.getName(),x);
		
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
		s = buildIntParamete(1, 255, 1, s);
		parameters.put(s.getName(),s);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.INT);
		b = buildIntParamete(1, 9, 1, b);
		parameters.put(b.getName(),b);
		
		ParameterPrototype num = new ParameterPrototype("num", ParameterType.INT);
		num = buildIntParamete(1, 10, 1, num);
		parameters.put(num.getName(),num);

	}
}
