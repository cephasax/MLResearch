package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.Arrays;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class SmoPrototype extends ClassifierPrototype {
	public SmoPrototype() throws InvalidParameterTypeException {
		ParameterPrototype sel = new ParameterPrototype("SEL", ParameterType.STRING);
		sel.setPossibilities(Arrays.asList("Default", "NormalizedPolyKernel","PolyKernel", "Puk", "RBFKernel"));
		parameters.put(sel.getName(), sel);
	}
}
