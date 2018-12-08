package br.ufrn.imd.pbil.domain.prototypes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class KStarPrototype extends ClassifierPrototype{
	public KStarPrototype() throws InvalidParameterTypeException {
		// TODO Auto-generated constructor stub
		ParameterPrototype b = new ParameterPrototype("B",ParameterType.INT);
		b = bPossibiliteis(b);
		parameters.put(b.getName(), b);
		
		ParameterPrototype e =new ParameterPrototype("E", ParameterType.BOOLEAN);
		e.setPossibilities(Arrays.asList(true,false));
		parameters.put(e.getName(), e);
		
		ParameterPrototype x = new ParameterPrototype("C", ParameterType.STRING);
		x.setPossibilities(Arrays.asList("a","d","m","n"));
		parameters.put(x.getName(), x);
	}

	private ParameterPrototype bPossibiliteis(ParameterPrototype b) {
		List<Integer> bValues = new ArrayList<Integer>();
		for(int i =1; i<=100;i++) {
			bValues.add(i);
		}
		b.setPossibilities(bValues);
		return b;
	}
}
