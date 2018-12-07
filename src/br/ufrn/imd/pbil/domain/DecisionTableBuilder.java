package br.ufrn.imd.pbil.domain;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;

public class DecisionTableBuilder implements ClassifierBuilder {

	DecisionTable defautDT = new DecisionTable();
	
	public DecisionTable defautBuild() {
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		Parameter e = new Parameter();
		e.setName("e");
		e.setType(ParameterType.STRING);
		e.setValue("acc");
		parameters.add(e);
		
		Parameter i = new Parameter();
		i.setName("i");
		i.setType(ParameterType.BOOLEAN);
		i.setValue("False");
		parameters.add(i);
		
		Parameter s = new Parameter();
		s.setName("s");
		s.setType(ParameterType.STRING);
		s.setValue("GreedStepWise");
		parameters.add(s);
		
		Parameter x = new Parameter();
		x.setName("x");
		x.setType(ParameterType.INT);
		x.setValue("1");		
		parameters.add(x);
		
		defautDT.setParameters(parameters);
		return defautDT;
	}

	public Classifier randomBuild() {
		// TODO Auto-generated method stub
		return null;
	}

	public Classifier wheitedDrawBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
