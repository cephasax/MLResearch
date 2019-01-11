package br.ufrn.imd.pbilautoens.domain.comm.prototypes;

import br.ufrn.imd.pbilautoens.ParameterPrototype;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class RandomCommitteePrototype extends CommitteePrototype{
		
	public RandomCommitteePrototype () throws InvalidParameterTypeException{
		
		ParameterPrototype i = new ParameterPrototype("I", ParameterType.INT);
	 	i = buildIntParamete(2, 64, 1, i);
	 	parameters.put(i.getName(),i);
	 	
	 	ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
	 	s = buildIntParamete(1, 255, 1, s);
	 	parameters.put(s.getName(),s);
	 	
	 	this.setNumberOfBranchClassifiers(1);
	}
}
