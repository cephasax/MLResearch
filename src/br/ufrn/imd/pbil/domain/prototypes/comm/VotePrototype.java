package br.ufrn.imd.pbil.domain.prototypes.comm;

import java.util.Arrays;

import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class VotePrototype extends CommitteePrototype{
	
	public VotePrototype() throws InvalidParameterTypeException {
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.INT);
		s = buildIntParamete(1, 255, 1, s);
		parameters.put(s.getName(), s);
		
		ParameterPrototype r = new ParameterPrototype("R", ParameterType.STRING);
		r.setPossibilities(Arrays.asList("AVG", "PROD", "MAJ", "MIN", "MAX", "MED"));
		parameters.put(r.getName(), r);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.INT);
		b = buildIntParamete(1, 9, 1, b);
		parameters.put(b.getName(), b);
		
		ParameterPrototype num = new ParameterPrototype("num", ParameterType.INT);
		num = buildIntParamete(1, 10, 1, num);
		parameters.put(num.getName(), num);
		
		this.setNumberOfBranchClassifiers(2);
	}
}
