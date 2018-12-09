package br.ufrn.imd.pbil.domain.baseclassifiers;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

import java.util.Arrays;
import java.util.List;

public class RandomTreePrototype extends ClassifierPrototype{
	
	public RandomTreePrototype() throws InvalidParameterTypeException {
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.INT);
		m = parameterBuilder(m, 1, 64);
		parameters.put(m.getName(), m);
		
		ParameterPrototype k = new ParameterPrototype("K", ParameterType.INT);
		k = parameterBuilder(k, 0, 32);
		parameters.put(k.getName(), k);
		
		ParameterPrototype depth = new ParameterPrototype("depth", ParameterType.INT);
		depth = parameterBuilder(depth, 0, 20);
		parameters.put(depth.getName(), depth);
		
		ParameterPrototype n = new ParameterPrototype("N", ParameterType.INT);
		n = parameterBuilder(n, 0, 5);
		parameters.put(n.getName(), n);
		
		ParameterPrototype u = new ParameterPrototype("U", ParameterType.BOOLEAN);
		u.setPossibilities(Arrays.asList(true,false));
		parameters.put(u.getName(), u);
		
	}
	
	public ParameterPrototype parameterBuilder(ParameterPrototype parameter, int inicio, int end) {
		List<Integer> values = Arrays.asList();
		for(int i = inicio; i<=end;i++) {
			values.add(i);
		}
		parameter.setPossibilities(values);
		return parameter;
	}
}
