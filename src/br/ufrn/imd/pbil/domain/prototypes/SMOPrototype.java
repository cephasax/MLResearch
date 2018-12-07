package br.ufrn.imd.pbil.domain.prototypes;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class SMOPrototype extends ClassifierPrototype{
	 public SMOPrototype() throws InvalidParameterTypeException{
		List<String> possibilities = new ArrayList<String>();
		ParameterPrototype u = new ParameterPrototype("u",ParameterType.STRING);
		possibilities.add("Defaut");
		possibilities.add("NormalizedPolyKernel");
		possibilities.add("PolyKernel");
		possibilities.add("Puk");
		possibilities.add("RBFKernel");
		u.setPossibilities(possibilities);
		parameters.add(u);
	}
	
}
