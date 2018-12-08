package br.ufrn.imd.pbil.domain.prototypes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class IbkPrototype extends ClassifierPrototype {
	public IbkPrototype() throws InvalidParameterTypeException {
		ParameterPrototype e = new ParameterPrototype("E", ParameterType.BOOLEAN);
		e.setPossibilities(Arrays.asList(true,false));
		parameters.put(e.getName(), e);
		
		ParameterPrototype k  = new ParameterPrototype("K", ParameterType.INT);
		k = kPossibilities(k);
		parameters.put(k.getName(), k);
		
		ParameterPrototype i = new ParameterPrototype("I", ParameterType.BOOLEAN);
		i.setPossibilities(Arrays.asList(true,false));
		parameters.put(i.getName(), i);
		
		ParameterPrototype f = new ParameterPrototype("F", ParameterType.BOOLEAN);
		f.setPossibilities(Arrays.asList(true,false));
		parameters.put(f.getName(), f);
		
		ParameterPrototype x = new ParameterPrototype("X", ParameterType.BOOLEAN);
		x.setPossibilities(Arrays.asList(true,false));
		parameters.put(x.getName(), x);
	}

	private ParameterPrototype kPossibilities(ParameterPrototype k) {
		List<Integer> values = new ArrayList<Integer>();
		for(int i =1; i<=64;i++) {
			values.add(i);
		}
		k.setPossibilities(values);
		return k;
	}
}
