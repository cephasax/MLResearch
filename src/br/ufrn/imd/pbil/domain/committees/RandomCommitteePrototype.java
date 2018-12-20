package br.ufrn.imd.pbil.domain.committees;



import br.ufrn.imd.pbil.domain.baseclassifiers.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.baseclassifiers.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class RandomCommitteePrototype extends ClassifierPrototype{
	RandomCommitteePrototype () throws InvalidParameterTypeException{
		ParameterPrototype i = new ParameterPrototype("I", ParameterType.INT);
	 	i = buildIntParamete(2, 64, 1, i);
	 	parameters.put(i.getName(),i);
	 	
	 	ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
	 	s = buildIntParamete(1, 255, 1, s);
	 	parameters.put(s.getName(),s);
	}
}
