package br.ufrn.imd.pbil.domain.committees;

import br.ufrn.imd.pbil.domain.Parameter;
import br.ufrn.imd.pbil.enums.ParameterType;
import br.ufrn.imd.pbil.exception.InvalidParameterTypeException;

public class RandomForestBuilder extends CommitteeBuilder{

	public RandomForestBuilder(CommitteePrototype committeePrototype) {
		super(committeePrototype);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Committee defaultBuild() {
		committee = new RandomForest();
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue("10");
		committee.addParameter(i);
		
		Parameter k = new Parameter("K", ParameterType.INT);
		k.setValue("2");
		committee.addParameter(k);
		
		Parameter w = new Parameter("W", ParameterType.INT);
		w.setValue("2");
		committee.addParameter(w);
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		
		return committee;
	}

	@Override
	public Committee randomBuild() {
		committee = new RandomForest();
		
		Parameter i = new Parameter("I", ParameterType.INT);
		i.setValue(randomValueForParameter(i));
		committee.addParameter(i);
		
		Parameter k = new Parameter("K", ParameterType.INT);
		k.setValue(randomValueForParameter(k));
		committee.addParameter(k);
		
		Parameter w = new Parameter("W", ParameterType.INT);
		w.setValue(randomValueForParameter(w));
		committee.addParameter(w);
		
		try {
			committee.setClassifiers(buildClassifiers(1));
		} catch (InvalidParameterTypeException e) {
			e.printStackTrace();
		}
		
		return committee;
	}

	@Override
	public Committee weightedBuild() {
		// TODO Auto-generated method stub
		return null;
	}

}
