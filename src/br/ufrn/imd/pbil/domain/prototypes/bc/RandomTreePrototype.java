package br.ufrn.imd.pbil.domain.prototypes.bc;

import java.util.Arrays;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class RandomTreePrototype extends ClassifierPrototype{
	
	public RandomTreePrototype() throws InvalidParameterTypeException {
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.INT);
		m = buildIntParamete( 1, 64,1,m);
		parameters.put(m.getName(), m);
		
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.INT);
		k = buildIntParamete(0, 32,1,k);
		parameters.put(k.getName(), k);
		
		ParameterPrototype depth = new ParameterPrototype("depth", ParameterType.INT);
		depth = buildIntParamete(0, 20,1,depth);
		parameters.put(depth.getName(), depth);
		
		ParameterPrototype n = new ParameterPrototype("N", ParameterType.INT);
		n = buildIntParamete(0, 5,1,n);
		parameters.put(n.getName(), n);
		
		ParameterPrototype u = new ParameterPrototype("U", ParameterType.BOOLEAN);
		u.setPossibilities(Arrays.asList(true,false));
		parameters.put(u.getName(), u);
		
	}
}
