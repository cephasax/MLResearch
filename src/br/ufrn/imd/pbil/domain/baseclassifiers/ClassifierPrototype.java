package br.ufrn.imd.pbil.domain.baseclassifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ClassifierPrototype {
	
	public HashMap<String, ParameterPrototype> parameters = new HashMap<String, ParameterPrototype>();

	public HashMap<String, ParameterPrototype> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, ParameterPrototype> parameters) {
		this.parameters = parameters;
	}
	
	public ParameterPrototype buildIntParamete(int begin, int end, int step, ParameterPrototype p){
		List<Integer> values = new ArrayList<Integer>();
		for(int i = begin; i<=end; i+=step) {
			values.add(i);
		}
		p.setPossibilities(values);
		return p;
	}
	
	protected ParameterPrototype buildDoubleParamete(int begin, int end, int step, ParameterPrototype p ){
		List<Double> values = new ArrayList<Double>();
		for(double i = begin; i<=end; i=i+step) {
			double valor  = i/100;
			values.add(valor);
		}
		p.setPossibilities(values);
		return p;
	}

}
