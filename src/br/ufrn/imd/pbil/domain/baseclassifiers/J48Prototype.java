package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.Arrays;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class J48Prototype extends ClassifierPrototype{
	public J48Prototype() throws InvalidParameterTypeException{
		ParameterPrototype o = new ParameterPrototype("O", ParameterType.BOOLEAN); 
		o.setPossibilities(Arrays.asList(true,false));
		parameters.put(o.getName(), o);
		
		ParameterPrototype u = new ParameterPrototype("U", ParameterType.BOOLEAN); 
		u.setPossibilities(Arrays.asList(true,false));
		parameters.put(u.getName(), u);
		
		ParameterPrototype b = new ParameterPrototype("B", ParameterType.BOOLEAN); 
		b.setPossibilities(Arrays.asList(true,false));
		parameters.put(b.getName(), b);
		
		ParameterPrototype j = new ParameterPrototype("J", ParameterType.BOOLEAN); 
		j.setPossibilities(Arrays.asList(true,false));
		parameters.put(j.getName(), j);
		
		ParameterPrototype a = new ParameterPrototype("A", ParameterType.BOOLEAN); 
		a.setPossibilities(Arrays.asList(true,false));
		parameters.put(a.getName(), a);
		
		ParameterPrototype s = new ParameterPrototype("S", ParameterType.BOOLEAN); 
		s.setPossibilities(Arrays.asList(true,false));
		parameters.put(s.getName(), s);
		
		ParameterPrototype m = new ParameterPrototype("M", ParameterType.INT); 
		m = buildIntParamete(1, 64, 1, m);
		parameters.put(m.getName(), m);
		
		ParameterPrototype c = new ParameterPrototype("C", ParameterType.DOUBLE); 
		c = buildDoubleParameter( 5, 500, 5, c);
		parameters.put(c.getName(), c);
	}
}
