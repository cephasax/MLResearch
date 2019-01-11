package br.ufrn.imd.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.ParameterPrototype;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

public class RandomTreePrototype extends ClassifierPrototype{
	
	public RandomTreePrototype() throws InvalidParameterTypeException {
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.INT);
		m = buildIntParamete( 1, 64,1,m);
		parameters.put(m.getName(), m);
		
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.INT);
		k = buildIntParamete(0, 32,1,k);
		parameters.put(k.getName(), k);
		
		ParameterPrototype depth = new ParameterPrototype("depth", ParameterType.INT);
		depth = buildIntParamete(2, 20,1,depth);
		parameters.put(depth.getName(), depth);
		
		ParameterPrototype n = new ParameterPrototype("N", ParameterType.INT);
		n = buildIntParamete(0, 5,1,n);
		parameters.put(n.getName(), n);
		
		ParameterPrototype u = new ParameterPrototype("U", ParameterType.BOOLEAN);
		u.setPossibilities(Arrays.asList(true,false));
		parameters.put(u.getName(), u);
		
	}
}
