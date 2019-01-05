package br.ufrn.imd.pbil.domain.bc.prototypes;

import java.util.Arrays;

import br.ufrn.imd.pbil.domain.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.ParameterPrototype;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class SmoPrototype extends ClassifierPrototype {
	public SmoPrototype() throws InvalidParameterTypeException {
		ParameterPrototype sel = new ParameterPrototype("SEL", ParameterType.STRING);
		sel.setPossibilities(Arrays.asList(
				"Default", 
				"weka.classifiers.functions.supportVector.NormalizedPolyKernel -E 2.0 -C 250007", 
				"weka.classifiers.functions.supportVector.PolyKernel -E 1.0 -C 250007", 
				"weka.classifiers.functions.supportVector.Puk -O 1.0 -S 1.0 -C 250007", 
				"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01"));
		parameters.put(sel.getName(), sel);
	}
}
