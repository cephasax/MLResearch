package br.ufrn.imd.pbilautoens.domain.bc.prototypes;

import java.util.Arrays;

import br.ufrn.imd.pbilautoens.ClassifierPrototype;
import br.ufrn.imd.pbilautoens.ParameterPrototype;
import br.ufrn.imd.pbilautoens.enums.ParameterType;
import br.ufrn.imd.pbilautoens.exception.InvalidParameterTypeException;

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
