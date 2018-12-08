package br.ufrn.imd.pbil.domain.builders;

import java.util.ArrayList;
import java.util.List;
import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.DecisionTable;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.DecisionTablePrototype;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class DecisionTableBuilder extends ClassifierBuilder {
	
	public Classifier defautBuild() {
		
		classifier = new DecisionTable();
		List<Parameter> parameters = new ArrayList<Parameter>();
		Parameter e = new Parameter("E", ParameterType.STRING);
		e.setValue("acc");
		parameters.add(e);
		
		Parameter i = new Parameter("I",ParameterType.BOOLEAN);
		i.setValue("False");
		parameters.add(i);
		
		Parameter s = new Parameter("S", ParameterType.STRING);
		s.setValue("GreedStepWise");
		parameters.add(s);
		
		Parameter x = new Parameter("X", ParameterType.INT);
		x.setValue("1");
		parameters.add(x);

		classifier.setParameters(parameters);
		return classifier;
		
	}
	
	public Classifier randomBuild() {
		
		classifier = new DecisionTable();
		

		int randomizedInt = 0;
		
		try {
			prototype = new DecisionTablePrototype();
			
			List<Parameter> parameters = new ArrayList<Parameter>();
			Parameter e = new Parameter("E", ParameterType.STRING);
			e.setValue(randomValueForParameter(e));
			parameters.add(e);
			
			Parameter i = new Parameter("I",ParameterType.BOOLEAN);
			
			i.setValue(randomValueForParameter(i));
			parameters.add(i);
			
			Parameter s = new Parameter("S",ParameterType.STRING);
			s.setType(ParameterType.STRING);
			s.setName("S");
			
			
			s.setValue(randomValueForParameter(s));
			parameters.add(s);
			
			Parameter x = new Parameter("X",ParameterType.STRING);			
			x.setValue(randomValueForParameter(x));
			parameters.add(x);
			
			classifier.setClassifierType(ClassifierType.BASE_CLASSIFIER);
			classifier.setName("Decision Table");
			classifier.setParameters(parameters);
			
		} catch (InvalidParameterTypeException e1) {
			e1.printStackTrace();
		}
		return classifier;
	}
	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
