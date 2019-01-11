package br.ufrn.imd.pbilautoens.domain.comm.prototypes;

import java.util.Arrays;

import br.ufrn.imd.pbilautoens.ParameterPrototype;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class BaggingPrototype extends CommitteePrototype{
	
	public BaggingPrototype() throws InvalidParameterTypeException {
		ParameterPrototype p =new ParameterPrototype("P", ParameterType.INT);
		p = buildIntParamete(10, 100, 1, p);
		parameters.put(p.getName(), p);
		
		ParameterPrototype i =new ParameterPrototype("I", ParameterType.INT);
		i = buildIntParamete(2, 128, 1, i);
		parameters.put(i.getName(), i);
		
		ParameterPrototype s =new ParameterPrototype("S", ParameterType.INT);
		s = buildIntParamete(1, 255, 1, s);
		parameters.put(s.getName(), s);
		
		ParameterPrototype o = new ParameterPrototype("O", ParameterType.BOOLEAN);
		o.setPossibilities(Arrays.asList(true,false));
		parameters.put(o.getName(), o);
		
		this.setNumberOfBranchClassifiers(1);
	}
}
