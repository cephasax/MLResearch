package br.ufrn.imd.pbil.domain.prototypes;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class DecisionTablePrototype extends ClassifierPrototype{
	
	public DecisionTablePrototype() throws InvalidParameterTypeException {
		ParameterPrototype e = new ParameterPrototype("e", ParameterType.STRING);
		List<String> eValaues = new ArrayList<String>();
		eValaues.add("acc");
		eValaues.add("rmse");
		eValaues.add("mae");
		eValaues.add("auc");
		e.setPossibilities(eValaues);
		parameters.add(e);
		
		ParameterPrototype i = new ParameterPrototype("i", ParameterType.BOOLEAN);
		List<String> iValaues = new ArrayList<String>();
		iValaues.add("true");
		iValaues.add("false");
		i.setPossibilities(iValaues);
		parameters.add(i);
		
		ParameterPrototype s = new ParameterPrototype("s", ParameterType.STRING);
		List<String> sValaues = new ArrayList<String>();
		sValaues.add("GreedyStepWise");
		sValaues.add("BestFirst");
		s.setPossibilities(sValaues);
		parameters.add(s);
		
		ParameterPrototype x = new ParameterPrototype("e", ParameterType.INT);
		List<String> xValaues = new ArrayList<String>();
		xValaues.add("1");
		xValaues.add("2");
		xValaues.add("3");
		xValaues.add("4");
		x.setPossibilities(xValaues);
		parameters.add(x);
	}
}
