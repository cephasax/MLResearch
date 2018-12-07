package br.ufrn.imd.pbil.domain.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.ufrn.imd.pbil.domain.Classifier;
import br.ufrn.imd.pbil.domain.DecisionTable;
import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.domain.prototypes.ClassifierPrototype;
import br.ufrn.imd.pbil.domain.prototypes.DecisionTablePrototype;
import br.ufrn.imd.pbil.enums.ClassifierType;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class DecisionTableBuilder implements ClassifierBuilder {
	
	private Classifier classifier;
	private ClassifierPrototype prototype;
	private StringBuilder stringBuilder;
	
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
		
		try {
			prototype = new DecisionTablePrototype();
			stringBuilder = new StringBuilder();
			
			Random m = new Random();
			
			List<Parameter> parameters = new ArrayList<Parameter>();
			Parameter e = new Parameter("E", ParameterType.STRING);
			
			stringBuilder.append(prototype.getParameters().get(e.getValue());
			//get(0).getPossibilities().get(m.nextInt(4)));
			
			e.setValue(value);
			parameters.add(e);
			
			Parameter i = new Parameter("I",ParameterType.BOOLEAN);
			
			value =  ""+parametersValues.getParameters().get(1)
			.getPossibilities().get(m.nextInt(1));
			
			i.setValue(value);
			parameters.add(i);
			
			Parameter s = new Parameter("S",ParameterType.STRING);
			s.setType(ParameterType.STRING);
			s.setName("S");
			
			value =  ""+parametersValues.getParameters().get(2)
			.getPossibilities().get(m.nextInt(1));
			
			s.setValue(value);
			parameters.add(s);
			
			Parameter x = new Parameter("X",ParameterType.STRING);
			
			value =  ""+parametersValues.getParameters().get(3)
			.getPossibilities().get(m.nextInt(3));
			
			x.setValue(value);
			parameters.add(x);
			randomTable.setClassifierType(ClassifierType.BASE_CLASSIFIER);
			randomTable.setName("Decision Table");
			randomTable.setParameters(parameters);
			
		} catch (InvalidParameterTypeException e1) {
			e1.printStackTrace();
		}
		return randomTable;
	}
	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}
}
